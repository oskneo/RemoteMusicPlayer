package com.gza21.remotemusicplayer.dao

import androidx.room.*
import com.gza21.remotemusicplayer.entities.PlaylistMod

@Dao
interface PlaylistDao {
    @Query("SELECT * FROM playlists")
    fun getAll(): List<PlaylistMod>

    @Query("SELECT * FROM playlists WHERE playlistId IN (:playlistIds)")
    fun loadAllByIds(playlistIds: IntArray): List<PlaylistMod>

//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): MusicMod

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg playlists: PlaylistMod)

    @Delete
    fun delete(playlist: PlaylistMod)
}