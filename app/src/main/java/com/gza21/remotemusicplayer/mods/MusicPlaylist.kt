package com.gza21.remotemusicplayer.mods


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "music_playlist")
data class MusicPlaylist(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ForeignKey(entity = MusicMod::class, parentColumns = ["id"], childColumns = ["music_id"])
    @ColumnInfo(name = "music_id")
    var mMusicId: Int = 0,
    @ForeignKey(entity = PlaylistMod::class, parentColumns = ["id"], childColumns = ["playlist_id"])
    @ColumnInfo(name = "playlist_id")
    var mPlaylistId: Int = 0
)