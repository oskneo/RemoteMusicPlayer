package com.gza21.remotemusicplayer.utils

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gza21.remotemusicplayer.BaseApp
import com.gza21.remotemusicplayer.dao.AlbumDao
import com.gza21.remotemusicplayer.dao.MusicDao
import com.gza21.remotemusicplayer.entities.*

@Database(
    entities = arrayOf(
        MusicMod::class,
        AlbumMod::class,
        ArtistMod::class,
        GenreMod::class,
        PlaylistMod::class,
        ServerMod::class,
        AlbumArtist::class,
        MusicPlaylist::class
    ),
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun musicDao(): MusicDao
    abstract fun albumDao(): AlbumDao

    companion object {
        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke()= instance ?: synchronized(LOCK){
            instance ?: buildDatabase().also { instance = it}
        }

        private fun buildDatabase() = Room.databaseBuilder(
            BaseApp.instance.applicationContext,
            AppDatabase::class.java, "remotemusic.db")
            .build()
    }
}