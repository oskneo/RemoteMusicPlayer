package com.gza21.remotemusicplayer.managers

import com.gza21.remotemusicplayer.dao.AlbumDao
import com.gza21.remotemusicplayer.dao.MusicDao
import com.gza21.remotemusicplayer.entities.AlbumMod
import com.gza21.remotemusicplayer.entities.MusicMod
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

    fun getMusicByAlbum(albumId: Int, callback: (List<MusicMod>) -> Unit) {
        Thread {
            callback(mMusicDao?.loadAllByAlbumId(albumId) ?: listOf())
        }.start()

    }
    fun getAlbum(albumId: Int, callback: (AlbumMod?) -> Unit) {
        Thread {
            val list = mAlbumDao?.loadAllByIds(IntArray(1).also { it[0] = albumId })
            if (list != null && list.isNotEmpty()) {
                callback(list[0])
            }
            callback(null)
        }.start()
    }


}