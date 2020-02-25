package com.gza21.remotemusicplayer.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class PlaylistWithMusics(
    @Embedded
    val playlist: PlaylistMod,
    @Relation(
        parentColumn = "playlistId",
        entity = MusicMod::class,
        entityColumn = "musicId",
        associateBy = Junction(
            value = MusicPlaylist::class,
            parentColumn = "playlistId",
            entityColumn = "musicId"
        )
    )
    val musics: List<MusicMod>
)