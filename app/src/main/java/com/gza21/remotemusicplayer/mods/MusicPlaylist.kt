package com.gza21.remotemusicplayer.mods


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(/*tableName = "music_playlists", */primaryKeys = ["playlistId", "musicId"])
data class MusicPlaylist(
//    @PrimaryKey(autoGenerate = true)
//    var mId: Int = 0,
//    @ForeignKey(entity = MusicMod::class, parentColumns = ["id"], childColumns = ["music_id"])
//    @ColumnInfo(name = "music_id")
    var musicId: Int = 0,
//    @ForeignKey(entity = PlaylistMod::class, parentColumns = ["id"], childColumns = ["playlist_id"])
//    @ColumnInfo(name = "playlist_id")
    var playlistId: Int = 0
)