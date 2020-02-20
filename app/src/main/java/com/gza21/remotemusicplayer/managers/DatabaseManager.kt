package com.gza21.remotemusicplayer.managers

import com.gza21.remotemusicplayer.Dao.AlbumDao
import com.gza21.remotemusicplayer.Dao.MusicDao
import com.gza21.remotemusicplayer.mods.DataBaseMod
import com.gza21.remotemusicplayer.mods.MusicMod
import com.gza21.remotemusicplayer.utils.AppDatabase

class DatabaseManager {
    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { DatabaseManager() }
    }
    private var mMusicDao: MusicDao? = null
    private var mAlbumDao: AlbumDao? = null

    private fun initDao() {
        if (mMusicDao == null) {
            mMusicDao = AppDatabase.invoke().musicDao()
        }
        if (mAlbumDao == null) {
            mAlbumDao = AppDatabase.invoke().albumDao()
        }
    }

    fun addMusicToDb(music: MusicMod) {
        initDao()
        mMusicDao?.insertAll(music)
    }
}