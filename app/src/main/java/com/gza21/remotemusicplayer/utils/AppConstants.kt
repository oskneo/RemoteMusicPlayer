package com.gza21.remotemusicplayer.utils

class AppConstants {

    enum class ScanMode {
        PathScan,
        FullScan
    }

    companion object {
        val PREVIOUS_DIR = "..\\"
        val PREF_GLOBAL = "pref_global"
        val PREF_SERVERS = "pref_servers"
    }
}