package com.gza21.remotemusicplayer.activities


import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Process
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
import com.gza21.remotemusicplayer.managers.ServerManager
import com.gza21.remotemusicplayer.mods.ServerMod
import com.gza21.remotemusicplayer.utils.Helper
import kotlinx.coroutines.sync.Mutex
import org.videolan.libvlc.Media
import org.videolan.libvlc.util.MediaBrowser
import java.net.URLDecoder


class NetworkActivity : BaseActivity(), MediaBrowser.EventListener {

    var mAdapter: ServerAdapter? = null
    val mSvMgr = ServerManager.instance
    val mutex = Mutex()
    val mLibMgr = LibVlcManager.instance
    var mBrowser: MediaBrowser? = null
    var mIsEnd = true
    val mThread = Thread()

    override fun onBrowseEnd() {
        synchronized(this@NetworkActivity) {
            mIsEnd = true
        }
    }

    override fun onMediaAdded(index: Int, media: Media?) {
        media?.let {
            runOnUiThread {
                synchronized(this@NetworkActivity) {
                    if (mIsEnd) {
                        mSvMgr.mServers.clear()
                    }

                    val server = ServerMod(Iterables.getLast(URLDecoder.decode(it.uri.toString(), "UTF-8").split('/')), it.uri.toString(), mUri = it.uri)
                    mSvMgr.add(server)
                    mAdapter?.updateServers()
                    mIsEnd = false
                }
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
//        val uri = Uri.Builder().appendPath("smb://OSK666-PC").build()
//        mBrowser?.browse(uri, MediaBrowser.Flag.Interact)
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
                }
                openPath(server)
            }
        })
        recyclerView?.adapter = mAdapter
    }
    fun openPath(server: ServerMod) {
        mBrowser?.browse(server.mUri, MediaBrowser.Flag.Interact)


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
     * If server is not null, it it edit dialog.
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

    private fun add() {
        getServerDialog(null)
    }
}