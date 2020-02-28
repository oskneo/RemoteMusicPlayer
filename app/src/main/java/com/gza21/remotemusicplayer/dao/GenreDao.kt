package com.gza21.remotemusicplayer.dao

import androidx.room.*
import com.gza21.remotemusicplayer.entities.GenreMod

@Dao
interface GenreDao {
    @Query("SELECT * FROM genres")
    fun getAll(): List<GenreMod>

    @Query("SELECT * FROM genres WHERE mId IN (:genreIds)")
    fun loadAllByIds(genreIds: IntArray): List<GenreMod>

    @Query("SELECT * FROM genres WHERE name = :name")
    fun loadByName(name: String): List<GenreMod>

//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): MusicMod

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(genre: GenreMod): Long

    @Delete
    fun delete(genre: GenreMod)
}