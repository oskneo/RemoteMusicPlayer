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

    fun remove(server: ServerMod) {
        mServers.remove(server)
    }
}