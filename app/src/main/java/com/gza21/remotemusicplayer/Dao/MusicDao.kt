package com.gza21.remotemusicplayer.Dao

import androidx.room.*
import com.gza21.remotemusicplayer.mods.MusicMod

@Dao
interface MusicDao {
    @Query("SELECT * FROM musics")
    fun getAll(): List<MusicMod>

    @Query("SELECT * FROM musics WHERE mId IN (:musicIds)")
    fun loadAllByIds(musicIds: IntArray): List<MusicMod>

//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): MusicMod

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg musics: MusicMod)

    @Delete
    fun delete(music: MusicMod)
}