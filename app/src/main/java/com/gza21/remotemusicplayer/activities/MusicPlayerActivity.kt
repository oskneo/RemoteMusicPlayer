package com.gza21.remotemusicplayer.activities

import android.os.Bundle
import com.gza21.remotemusicplayer.R
import com.gza21.remotemusicplayer.managers.LibVlcManager
import com.gza21.remotemusicplayer.mods.MusicMod
import com.gza21.remotemusicplayer.utils.Helper
import kotlinx.android.synthetic.main.activity_music_player.*
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer
import java.lang.Exception

class MusicPlayerActivity : BaseActivity() {

    var mMusic: MusicMod? = null
    val mLibMgr = LibVlcManager.instance
    var mPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout()
    }

    private fun initLayout() {
        btn_play?.setOnClickListener {  }
    }

    override fun getLayoutView(): Int {
        return R.layout.activity_music_player
    }

    private fun playMusic() {
        mMusic?.mUri.let {
            try {
                mPlayer = MediaPlayer(mLibMgr.mLibVlc)
                val media = Media(mLibMgr.mLibVlc, it)
                mPlayer?.media = media
                mPlayer?.setEventListener {
                    runOnUiThread {
                        val time = Helper.convertMilisec2Min(it.timeChanged)
                        if (time_cur?.text?.toString() != time) {
                            time_cur?.text = time
                        }

                    }

                }
                mPlayer?.play()
            } catch (e: Exception) {}

        }

    }
}