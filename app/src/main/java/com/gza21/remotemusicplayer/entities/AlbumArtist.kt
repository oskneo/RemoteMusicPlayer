package com.gza21.remotemusicplayer.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "album_artists", primaryKeys = ["album_id", "artist_id"])
data class AlbumArtist(
//    @PrimaryKey(autoGenerate = true)
//    var mId: Int = 0,
    @ForeignKey(entity = AlbumMod::class, parentColumns = ["id"], childColumns = ["album_id"])
    @ColumnInfo(name = "album_id")
    var mAlbumId: Int = 0,
    @ForeignKey(entity = ArtistMod::class, parentColumns = ["id"], childColumns = ["artist_id"])
    @ColumnInfo(name = "artist_id")
    var mArtistId: Int = 0
)