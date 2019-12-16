package com.gza21.remotemusicplayer.activities


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Process
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
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
import com.gza21.remotemusicplayer.managers.PlayerListManager
import com.gza21.remotemusicplayer.managers.ServerManager
import com.gza21.remotemusicplayer.mods.MusicMod
import com.gza21.remotemusicplayer.mods.ServerMod
import com.gza21.remotemusicplayer.utils.Helper
import kotlinx.coroutines.sync.Mutex
import org.videolan.libvlc.Media
import org.videolan.libvlc.util.MediaBrowser
import java.net.URLDecoder
import java.util.*


class NetworkActivity : BaseActivity(), MediaBrowser.EventListener {

    var mAdapter: ServerAdapter? = null
    val mSvMgr = ServerManager.instance
    val mutex = Mutex()
    val mLibMgr = LibVlcManager.instance
    var mBrowser: MediaBrowser? = null
    var mIsEnd = true
    val mThread = Thread()
    val mStack: Stack<ServerMod> = Stack()

    override fun onBrowseEnd() {
        synchronized(this@NetworkActivity) {
            mIsEnd = true
        }
        runOnUiThread {
            mAdapter?.updateServers()
        }
    }

    override fun onMediaAdded(index: Int, media: Media?) {
        media?.let {
            val url = URLDecoder.decode(it.uri.toString(), "UTF-8")
            Log.e("FilePath", "Path:${url ?: ""}")
            runOnUiThread {

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
                mSvMgr.add(server)

                if (mStack.empty()) {
                    mAdapter?.updateServers()
                }

                media.release()
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



    override fun onResume() {
        super.onResume()
        mBrowser = MediaBrowser(mLibMgr.mLibVlc, this, browserHandler)
        mThread.run {
            mLibMgr.setDialogCallback()

        }
        mBrowser?.discoverNetworkShares()
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
                openPath(server)
            }
        })
        recyclerView?.adapter = mAdapter
    }
    fun openPath(server: ServerMod) {
        if (server.mType == Media.Type.Directory) {
            mBrowser?.browse(server.mUri, MediaBrowser.Flag.Interact)

        } else if (server.mType == Media.Type.File && Helper.isCorrectExt(server.mUri)) {
            val music = MusicMod.loadUri(server.mUri, true)
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
        menu?.findItem(R.id.action_add)?.setTitle("Add")
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId ?: 0 == R.id.action_add) {
            add()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getEditedContent(view: View?, @IdRes id: Int) : String {
        return view?.findViewById<LinearLayout>(id)?.
            findViewById<EditText>(R.id.edit_text)?.text?.toString() ?: ""
    }

    private fun setEditedContent(view: View?, @IdRes id: Int, text: String? = null, @StringRes title: Int? = null) {
        text?.let {
            view?.findViewById<LinearLayout>(id)?.
                findViewById<EditText>(R.id.edit_text)?.setText(it)
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
    fun getServerDialog(server: ServerMod?) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_server, null)
        setEditedContent(dialogView, R.id.server_name, server?.mName, R.string.server_name)
        setEditedContent(dialogView, R.id.server_address, server?.mAddress, R.string.server_address)
        setEditedContent(dialogView, R.id.server_username, server?.mUserName, R.string.username)
        setEditedContent(dialogView, R.id.server_pass, server?.mPassword, R.string.password)
        mAlertDialog = Helper.getAlertDialog(this, mAlertDialog, dialogView, R.string.ok, {
            val mServer = ServerMod(
                getEditedContent(dialogView, R.id.server_name),
                getEditedContent(dialogView, R.id.server_address),
                getEditedContent(dialogView, R.id.server_username),
                getEditedContent(dialogView, R.id.server_pass)
            )
            server?.let {
                mSvMgr.edit(server, mServer)
            } ?: run {
                mSvMgr.add(mServer)
            }
            updateList()
        })
    }

    override fun onBackPressed() {
        if (mStack.empty()) {
            super.onBackPressed()
        } else {
            mStack.pop()
            if (mStack.isEmpty()) {
                mBrowser?.discoverNetworkShares()
            } else {
                openPath(mStack.peek())
            }

        }
    }

    private fun add() {
        getServerDialog(null)
    }
}