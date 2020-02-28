package com.gza21.remotemusicplayer.activities


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Process
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.common.collect.Iterables
import com.gza21.remotemusicplayer.R
import com.gza21.remotemusicplayer.adapters.ServerAdapter
import com.gza21.remotemusicplayer.dialogs.ServerConnectDialogFragment
import com.gza21.remotemusicplayer.managers.LibVlcManager
import com.gza21.remotemusicplayer.managers.MusicDBManager
import com.gza21.remotemusicplayer.managers.PlayerListManager
import com.gza21.remotemusicplayer.managers.ServerManager
import com.gza21.remotemusicplayer.entities.DataBaseMod
import com.gza21.remotemusicplayer.entities.MusicMod
import com.gza21.remotemusicplayer.entities.ServerMod
import com.gza21.remotemusicplayer.utils.Helper
import kotlinx.android.synthetic.main.activity_add_remote_server.*
import kotlinx.coroutines.sync.Mutex
import org.videolan.libvlc.Media
import org.videolan.libvlc.util.MediaBrowser
import java.net.URLDecoder
import java.util.*
import java.util.concurrent.Semaphore
import kotlin.collections.ArrayList


class NetworkActivity : BaseActivity(), MediaBrowser.EventListener {

    var mAdapter: ServerAdapter? = null
    val mSvMgr = ServerManager.instance
    val mutex = Mutex()
    val mLibMgr = LibVlcManager.instance
    var mBrowser: MediaBrowser? = null
    var mIsEnd = true
    val mThread = Thread()
    val mStack: Stack<ServerMod> = Stack()
    var mAddButton: MenuItem? = null
    var mIsScan = false
    var mIsLock = false
    val semaphore = Semaphore(1)

    private fun scanToBuildLibrary() {


        mIsScan = true
        updateLoading(true)
        val db = DataBaseMod(mStack.firstElement())
        scanPaths(db)
        MusicDBManager.instance.mDataBase = db
        updateLoading(false)
        openPath(mStack.peek())
    }

    @Synchronized
    private fun scanPaths(db: DataBaseMod) {
        val serverList: ArrayList<ServerMod> = arrayListOf()
        serverList.addAll(mSvMgr.mServers)
        mSvMgr.clear()
        for (server in serverList) {
            scanPath(server, db)
        }
//        val tList = arrayListOf<Thread>()
//        for (server in serverList) {
//            val t = Thread {
//                scanPath(server, db)
//            }
//            t.start()
//            tList.add(t)
//        }
//        for (t in tList) {
//            t.join()
//        }
    }

    private fun scanPath(server: ServerMod, db: DataBaseMod) {
        if (server.mType == Media.Type.Directory) {
            mBrowser?.browse(server.mUri, MediaBrowser.Flag.Interact)
            mIsLock = true
            while (mIsLock) {
                Thread.sleep(5)
            }
            scanPaths(db)
        } else if (server.mType == Media.Type.File && Helper.isCorrectExt(server.mUri)) {
            Log.e("Music", "Scanmusci")
//            parseMusic(server, db)
            
        }
    }

    private fun parseMusic(server: ServerMod, db: DataBaseMod) {
        MusicMod.loadUri(server.mUri, false, server.mId)?.let {
            synchronized(this) {
                db.addMusic(it)
                Log.e("Music", "Added")
            }
        }
    }

    @Synchronized
    override fun onBrowseEnd() {


//        val time = measureTimeMillis {
//            val one = waitSome()
//        }
        Log.e("FilePath", "BrowseEnd:$mIsScan")
        synchronized(this@NetworkActivity) {
            mIsEnd = true
        }
        runOnUiThread {
//            semaphore.acquire()
//            Log.e("FilePath", "semaphore:${semaphore.}")
            if (!mIsScan) {
                mAdapter?.updateServers()
                updateLoading(false)
            } else {
                mIsLock = false
            }
//            semaphore.release()
        }

    }

//    suspend fun waitSome(): Int {
//        semaphore.acquire()
//        return 1
//    }

    @Synchronized
    override fun onMediaAdded(index: Int, media: Media?) {

        media?.let {
            val url = URLDecoder.decode(it.uri.toString(), "UTF-8")

            runOnUiThread {

//                semaphore.acquire()
                if (mIsEnd) {
                    mSvMgr.clear()
                }
                synchronized(this@NetworkActivity) {
                    mIsEnd = false
                }

                val server =
                    ServerMod(
                        Iterables.getLast(url.split('/')),
                        url,
                        mUri = it.uri,
                        mIsRoot = if (mStack.empty()) true else false,
                        mType = media.type
                    )
                if (mIsScan) {
                    mSvMgr.addDrectly(server)
                } else {
                    mSvMgr.add(server)
                }


                if (mStack.empty()) {
                    mAdapter?.updateServers()
                    updateLoading(false)
                }
                Log.e("FilePath", "Path:${url ?: ""}")
                media.release()
//                semaphore.release()
            }
        }
    }

    override fun onMediaRemoved(index: Int, media: Media?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getList()

    }

    private val browserHandler by lazy {
        val handlerThread = HandlerThread("vlc-mProvider", Process.THREAD_PRIORITY_DEFAULT + Process.THREAD_PRIORITY_LESS_FAVORABLE)
        handlerThread.start()
        Handler(handlerThread.looper)
    }

    override fun onStart() {
        super.onStart()
        mSvMgr.mListener = object : ServerManager.LoginListener {
            override fun showLoginDialog(server: ServerMod) {
                getServerDialog(server, true)
            }
        }
        mAdapter?.updateServers()

    }


    override fun onResume() {
        super.onResume()
        mBrowser = MediaBrowser(mLibMgr.mLibVlc, this, browserHandler)
        mLibMgr.setDialogCallback()

    }

    private fun openServerDialog(server: ServerMod) {
        val dialog = ServerConnectDialogFragment()
        dialog.mServer = server
        dialog.show(supportFragmentManager, dialog.LOG_TAG)
    }

    private fun getList() {
        val recyclerView = findViewById<RecyclerView>(R.id.server_list)
        val layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        mAdapter = ServerAdapter(this, object : ServerAdapter.ServerListener {
            override fun onConnect(server: ServerMod) {
//                openServerDialog(server)
                synchronized(this@NetworkActivity) {
                    mIsEnd = true
                    mStack.push(server)
                }
                mSvMgr.setTargetServer(server)
                openPath(server)
            }
        })
        recyclerView?.adapter = mAdapter
    }
    fun openPath(server: ServerMod) {
        if (server.mType == Media.Type.Directory) {
            mBrowser?.browse(server.mUri, MediaBrowser.Flag.Interact)
            mAddButton?.setVisible(false)

        } else if (server.mType == Media.Type.File && Helper.isCorrectExt(server.mUri)) {
            val music = MusicMod.loadUri(server.mUri, true, server.mId)
            music?.let {
                PlayerListManager.instance.mMusic = it
                Intent(this, MusicPlayerActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }

    fun updateList() {
        mAdapter?.updateServers()
    }

    override fun getLayoutView(): Int {
        return R.layout.activity_add_remote_server
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_network, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        mAddButton = menu?.findItem(R.id.action_add)
        mAddButton?.setTitle("Add")
        menu?.findItem(R.id.action_scan)?.setTitle("Scan")
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId ?: 0 == R.id.action_add) {
            add()
        } else if (item?.itemId ?: 0 == R.id.action_scan) {
            scan()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun scan() {
        updateLoading(true)
        if (mStack.empty()) {
            mBrowser?.discoverNetworkShares()
        } else {
            scanToBuildLibrary()
        }
    }



    private fun updateLoading(isLoading: Boolean) {
        runOnUiThread {
            loading_bar?.visibility = if (isLoading) View.VISIBLE else View.GONE
            loading_bar?.isIndeterminate = if (isLoading) true else false
        }
    }

    private fun getEditedContent(view: View?, @IdRes id: Int) : String {
        return view?.findViewById<LinearLayout>(id)?.
            findViewById<EditText>(R.id.edit_text)?.text?.toString() ?: ""
    }

    private fun setEditedContent(view: View?, @IdRes id: Int, text: String? = null, @StringRes title: Int? = null, readOnly: Boolean = false) {
        text?.let {
            val text = view?.findViewById<LinearLayout>(id)?.
                findViewById<EditText>(R.id.edit_text)
            text?.setText(it)
            if (readOnly) text?.isEnabled = false
        }
        title?.let {
            view?.findViewById<LinearLayout>(id)?.
                findViewById<TextView>(R.id.edit_title)?.setText(it)
        }

    }

    /**
     * If server is not null, it is edit dialog.
     * If server is null, it is add dialog.
     */
    fun getServerDialog(server: ServerMod?, isLogin: Boolean = false) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_server, null)
        setEditedContent(dialogView, R.id.server_name, server?.mName, R.string.server_name, isLogin)
        setEditedContent(dialogView, R.id.server_address, server?.mAddress, R.string.server_address, isLogin)
        setEditedContent(dialogView, R.id.server_username, server?.mUserName, R.string.username)
        setEditedContent(dialogView, R.id.server_pass, if (isLogin && server?.mIsStored != true) "" else server?.mPassword, R.string.password)
        mAlertDialog = Helper.getAlertDialog(this, mAlertDialog, dialogView, R.string.ok, {

            var address = getEditedContent(dialogView, R.id.server_address)
            if (!address.startsWith("smb")) {
                address = "smb://" + address
            }
            val mServer = ServerMod(
                getEditedContent(dialogView, R.id.server_name),
                address,
                getEditedContent(dialogView, R.id.server_username),
                getEditedContent(dialogView, R.id.server_pass),
                mUri = Uri.parse(address),
                mIsStored = dialogView?.findViewById<CheckBox>(R.id.store_check)?.isChecked ?: false,
                mType = Media.Type.Directory
            )
            if (isLogin) {
                mSvMgr.login(mServer)
            } else {
                server?.let {
                    mSvMgr.editSavedServer(server, mServer)
                } ?: run {
                    mSvMgr.addSavedServer(mServer)
                }
                updateList()
            }

        })
    }

    override fun onBackPressed() {
        if (mStack.empty()) {
            super.onBackPressed()
        } else {
            synchronized(this) {
                mStack.pop()
            }
            mSvMgr.setTargetServer(null)
            if (mStack.isEmpty()) {
                mSvMgr.clear()
                mAdapter?.updateServers()
                mAddButton?.setVisible(true)
            } else {
                openPath(mStack.peek())
            }

        }
    }

    private fun add() {
        getServerDialog(null)
    }
}