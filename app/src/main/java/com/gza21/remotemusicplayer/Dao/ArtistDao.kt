package com.gza21.remotemusicplayer.Dao

import androidx.room.*
import com.gza21.remotemusicplayer.mods.ArtistMod

@Dao
interface ArtistDao {
    @Query("SELECT * FROM albums")
    fun getAll(): List<ArtistMod>

    @Query("SELECT * FROM artists WHERE mId IN (:artistIds)")
    fun loadAllByIds(artistIds: IntArray): List<ArtistMod>

//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): MusicMod

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg artists: ArtistMod)

    @Delete
    fun delete(artist: ArtistMod)
}