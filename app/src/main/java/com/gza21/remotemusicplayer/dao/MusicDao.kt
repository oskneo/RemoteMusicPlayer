package com.gza21.remotemusicplayer.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gza21.remotemusicplayer.entities.MusicMod


@Dao
interface MusicDao {
    @Query("SELECT * FROM musics")
    fun getAll(): List<MusicMod>

    @Query("SELECT * FROM musics WHERE musicId IN (:musicIds)")
    fun loadAllByIds(musicIds: IntArray): List<MusicMod>

    @Query("SELECT * FROM musics WHERE album_id = (:albumId)")
    fun loadAllByAlbumId(albumId: Int): List<MusicMod>

    @Query("SELECT COUNT(musicId) FROM musics")
    fun getRowCount(): Int

//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): MusicMod

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg musics: MusicMod)

    @Delete
    fun delete(music: MusicMod)
}