package com.gza21.remotemusicplayer.managers

import com.gza21.remotemusicplayer.entities.MusicMod
import com.gza21.remotemusicplayer.entities.PlaylistMod
import org.videolan.libvlc.Media

class PlayerListManager {
    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { PlayerListManager() }
    }

    var mPlayList: PlaylistMod = PlaylistMod()
    var mMusic: MusicMod = MusicMod()
    var mMedia: Media? = null
}