package com.gza21.remotemusicplayer.utils

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gza21.remotemusicplayer.mods.MusicMod

@Database(entities = arrayOf(MusicMod::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun musicDao(): MusicDao
}