package com.gza21.remotemusicplayer.managers

import com.gza21.remotemusicplayer.mods.ArrayListServerMod
import com.gza21.remotemusicplayer.mods.ServerMod
import com.gza21.remotemusicplayer.utils.AppConstants
import com.gza21.remotemusicplayer.utils.Helper
import org.videolan.libvlc.Dialog

class ServerManager {

    var mServers: ArrayList<ServerMod> = arrayListOf()

    var mSavedServers: ArrayList<ServerMod> = arrayListOf()
    var mTargetServer: ServerMod? = null
    var mLoginDialog: Dialog.LoginDialog? = null
    var mListener: LoginListener? = null

    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { ServerManager() }
    }

    @Synchronized
    fun clear() {
        mServers.clear()
    }

    @Synchronized
    fun setTargetServer(server: ServerMod?) {
        mTargetServer = server
    }

    fun showLoginDialog(dialog: Dialog.LoginDialog) {

        mTargetServer?.let { target ->
            mSavedServers.find {
                it.mAddress == target.mAddress && target.mIsStored
            }?.let {
                dialog.postLogin(it.mUserName, it.mPassword, it.mIsStored)
            } ?: run {
                mLoginDialog = dialog
                mListener?.showLoginDialog(target)
            }
        }

    }

    fun login(server: ServerMod) {
        mLoginDialog?.postLogin(server.mUserName, server.mPassword, server.mIsStored)
        addSavedServer(server)
    }

    init {
        mSavedServers.clear()
        val temp = Helper.getSharedPrefForObject(AppConstants.PREF_SERVERS, ArrayListServerMod::class.java)
        temp?.let {
            mSavedServers.addAll(it.mData)
            mServers.addAll(it.mData)
        }
    }

    fun addSavedServer(server: ServerMod) {
        mSavedServers.find { compareServers(it, server) }?.let {
            mSavedServers.remove(it)
        }
        mSavedServers.add(server)
        Helper.setSharedPrefForObject(AppConstants.PREF_SERVERS, ArrayListServerMod(mSavedServers))
        add(server)
    }

    fun removeSavedServer(server: ServerMod?) {
        server?.let {
            mSavedServers.remove(it)
        }
        remove(server)
    }

    fun clearSavedServers() {
        mSavedServers.clear()
    }

    private fun compareServers(server1: ServerMod?, server2: ServerMod?): Boolean {
        if (server1?.mUri == null || server2?.mUri == null) {
            return false
        }
        val uri1 = server1.mUri.toString()
        val uri2 = server2.mUri.toString()
        if (uri1.length == 0 || uri2.length == 0) {
            return false
        }
        return uri1 == uri2
    }

    @Synchronized
    fun add(server: ServerMod) {
        mServers.find { compareServers(it, server) }?.let {
            mServers.remove(it)
        }
        if (server.mIsRoot) {
            mSavedServers.find { compareServers(it, server) }?.let {
                mServers.add(it)
            } ?: run {
                mServers.add(server)
            }
        } else {
            mServers.add(server)
        }
    }

    @Synchronized
    fun addDrectly(server: ServerMod) {
        mServers.add(server)
    }



    @Synchronized
    fun remove(server: ServerMod?) {
        server?.let {
            mServers.remove(it)
        }
    }

    @Synchronized
    fun edit(oldServer: ServerMod, newServer: ServerMod) {
        mServers.find {
            it == oldServer
        }?.let {
            it.mName = newServer.mName
            it.mAddress = newServer.mAddress
            it.mUserName = newServer.mUserName
            it.mPassword = newServer.mPassword
        }
    }

    fun editSavedServer(oldServer: ServerMod, newServer: ServerMod) {
        mSavedServers.find {
            it == oldServer
        }?.let {
            it.mName = newServer.mName
            it.mAddress = newServer.mAddress
            it.mUserName = newServer.mUserName
            it.mPassword = newServer.mPassword
        }
        edit(oldServer, newServer)
    }

    fun resetDisConnected() {
        for (server in mServers) {
            if (server.mIsStored == true)
                server.mIsStored = false
        }
    }

    fun setServerConnected(server: ServerMod?) {
        mServers.find {
            it == server
        }?.let {
            resetDisConnected()
            it.mIsStored = true
        }
    }

    interface LoginListener {
        fun showLoginDialog(server: ServerMod)
    }
}