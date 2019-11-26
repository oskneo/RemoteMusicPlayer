package com.gza21.remotemusicplayer.managers

import com.gza21.remotemusicplayer.mods.ServerMod

class ServerManager {

    var mServers: ArrayList<ServerMod> = arrayListOf()

    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { ServerManager() }
    }

    fun add(server: ServerMod) {
        mServers.find { it.mAddress == server.mAddress }?.let {
            mServers.remove(it)
        }
        mServers.add(server)
    }

    fun remove(server: ServerMod?) {
        server?.let {
            mServers.remove(it)
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