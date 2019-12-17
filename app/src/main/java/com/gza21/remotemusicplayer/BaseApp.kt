package com.gza21.remotemusicplayer

import android.app.Application
import com.gza21.remotemusicplayer.managers.LibVlcManager

class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        LibVlcManager.instance.prepareCacheFolder(applicationContext)
    }

    companion object {
        lateinit var instance: BaseApp
            private set
    }
}