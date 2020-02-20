package com.gza21.remotemusicplayer.Dao


import androidx.room.*
import com.gza21.remotemusicplayer.mods.AlbumMod
import com.gza21.remotemusicplayer.mods.MusicMod

@Dao
interface AlbumDao {
    @Query("SELECT * FROM albums")
    fun getAll(): List<AlbumDao>

    @Query("SELECT * FROM albums WHERE mId IN (:albumIds)")
    fun loadAllByIds(albumIds: IntArray): List<AlbumMod>

//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): MusicMod

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg albums: AlbumMod)

    @Delete
    fun delete(album: AlbumMod)
}