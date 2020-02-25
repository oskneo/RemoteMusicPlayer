package com.gza21.remotemusicplayer.dao

import androidx.room.*
import com.gza21.remotemusicplayer.entities.AlbumArtist

@Dao
interface AlbumArtistDao {
    @Query("SELECT * FROM album_artists")
    fun getAll(): List<AlbumArtist>

    @Query("SELECT * FROM album_artists WHERE mId IN (:albumArtistIds)")
    fun loadAllByIds(albumArtistIds: IntArray): List<AlbumArtist>

//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): MusicMod

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg albumArtists: AlbumArtist)

    @Delete
    fun delete(albumArtist: AlbumArtist)
}