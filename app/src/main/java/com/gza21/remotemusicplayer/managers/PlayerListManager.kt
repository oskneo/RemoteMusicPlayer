package com.gza21.remotemusicplayer.managers

import android.provider.MediaStore
import com.gza21.remotemusicplayer.mods.MusicMod
import com.gza21.remotemusicplayer.mods.PlaylistMod
import org.videolan.libvlc.Media

class PlayerListManager {
    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { PlayerListManager() }
    }

    var mPlayList: PlaylistMod = PlaylistMod()
    var mMusic: MusicMod = MusicMod()
    var mMedia: Media? = null
}