package com.gza21.remotemusicplayer.dao

import androidx.room.*
import com.gza21.remotemusicplayer.entities.ServerMod

@Dao
interface ServerDao {
    @Query("SELECT * FROM servers")
    fun getAll(): List<ServerMod>

    @Query("SELECT * FROM servers WHERE mId IN (:serverIds)")
    fun loadAllByIds(serverIds: IntArray): List<ServerMod>

//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): MusicMod

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg servers: ServerMod)

    @Delete
    fun delete(server: ServerMod)
}