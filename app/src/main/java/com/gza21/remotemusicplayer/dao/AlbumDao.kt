package com.gza21.remotemusicplayer.dao


import androidx.room.*
import com.gza21.remotemusicplayer.entities.AlbumMod

@Dao
interface AlbumDao {
    @Query("SELECT * FROM albums")
    fun getAll(): List<AlbumMod>

    @Query("SELECT * FROM albums WHERE mId IN (:albumIds)")
    fun loadAllByIds(albumIds: IntArray): List<AlbumMod>

    @Query("SELECT * FROM albums WHERE name = :name")
    fun loadByName(name: String): List<AlbumMod>

//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): MusicMod

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(album: AlbumMod): Long

    @Delete
    fun delete(album: AlbumMod)
}