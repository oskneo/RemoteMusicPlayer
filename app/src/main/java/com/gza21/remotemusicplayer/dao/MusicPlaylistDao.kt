package com.gza21.remotemusicplayer.dao

import androidx.room.*
import com.gza21.remotemusicplayer.entities.MusicPlaylist

@Dao
interface MusicPlaylistDao {
    @Query("SELECT * FROM music_playlists")
    fun getAll(): List<MusicPlaylist>

//    @Query("SELECT * FROM music_playlists WHERE mId IN (:musicPlaylistIds)")
//    fun loadAllByIds(musicPlaylistIds: IntArray): List<MusicPlaylist>

//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): MusicMod

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg musicPlaylists: MusicPlaylist)

    @Delete
    fun delete(musicPlaylist: MusicPlaylist)
}