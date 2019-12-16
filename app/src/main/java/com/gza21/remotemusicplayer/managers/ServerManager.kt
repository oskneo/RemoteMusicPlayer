package com.gza21.remotemusicplayer.managers

import com.gza21.remotemusicplayer.mods.ServerMod

class ServerManager {

    var mServers: ArrayList<ServerMod> = arrayListOf()

    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { ServerManager() }
    }

    fun clear() {
        synchronized(this@ServerManager) {
            mServers.clear()
        }
    }

    fun add(server: ServerMod) {
        synchronized(this@ServerManager) {
            mServers.find { it == server }?.let {
                mServers.remove(it)
            }
            mServers.add(server)
        }
    }

    fun remove(server: ServerMod?) {
        synchronized(this@ServerManager) {
            server?.let {
                mServers.remove(it)
            }
        }
    }

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

    fun resetDisConnected() {
        for (server in mServers) {
            if (server.mIsConnected == true)
                server.mIsConnected = false
        }
    }

    fun setServerConnected(server: ServerMod?) {
        mServers.find {
            it == server
        }?.let {
            resetDisConnected()
            it.mIsConnected = true
        }
    }
}