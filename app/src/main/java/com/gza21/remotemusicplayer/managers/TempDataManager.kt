package com.gza21.remotemusicplayer.managers

class TempDataManager {
    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { TempDataManager() }
    }

    var height = 0
    var width = 0
}