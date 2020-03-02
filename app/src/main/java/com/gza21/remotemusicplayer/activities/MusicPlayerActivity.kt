package com.gza21.remotemusicplayer.activities

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.SeekBar
import com.gza21.remotemusicplayer.R
import com.gza21.remotemusicplayer.managers.LibVlcManager
import com.gza21.remotemusicplayer.managers.PlayerListManager
import com.gza21.remotemusicplayer.entities.MusicMod
import com.gza21.remotemusicplayer.utils.Helper
import kotlinx.android.synthetic.main.activity_music_player.*
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer
import java.io.FileInputStream
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
        btn_play?.setOnClickListener {
            if (mPlayer?.isPlaying == true) {
                mPlayer?.pause()
                btn_play?.setImageDrawable(getDrawable(R.drawable.ic_play))
            } else {
                mPlayer?.play()
                btn_play?.setImageDrawable(getDrawable(R.drawable.ic_pause))
            }
        }
        seekbar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                val duration = mPlayer?.media?.duration
                val progress = seekBar?.progress
                if (mPlayer?.isSeekable == true && duration != null && progress != null) {
                    val time = duration * progress / 100
                    mPlayer?.time = time
                }

            }
        })
    }

    override fun getLayoutView(): Int {
        return R.layout.activity_music_player
    }

    override fun onStart() {
        super.onStart()
        mMusic = PlayerListManager.instance.mMusic
        prepareMusic()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    val playListener = object : MediaPlayer.EventListener {
        override fun onEvent(event: MediaPlayer.Event?) {
            val media = mPlayer?.media
            if (media == null || event == null) {
                return
            }
            runOnUiThread {
                val time = Helper.convertMilisec2Min(event.timeChanged)
                val percentage = event.timeChanged * 100 / media.duration
                if (time_cur?.text?.toString() != time &&
                    ((mPlayer?.isPlaying == true && percentage in 1 .. 99)
                            || (mPlayer?.isPlaying == false && mPlayer?.media?.state == Media.State.Ended))
                ) {
                    time_cur?.text = time
                    seekbar?.progress = percentage.toInt()
                }
                if (mPlayer?.media?.state == Media.State.Ended) {
                    mPlayer?.time = 0L
                    mPlayer?.stop()
                    btn_play?.setImageDrawable(getDrawable(R.drawable.ic_play))
                }
//                val artPath = mPlayer?.media?.getMeta(Media.Meta.ArtworkURL)?.let {
//                    URLDecoder.decode(it, "UTF-8")
//                }
//                if (artPath != null && artPath != mMusic?.mArtPath) {
//                    mMusic?.mArtPath = artPath
//                    if (artPath.startsWith("file")) {
//                        try {
//                            val stream = FileInputStream(artPath.substring(7))
//                            val bitmap = BitmapFactory.decodeStream(stream)
//                            img_thumbnail?.setImageBitmap(bitmap)
//                        } catch (e: Exception) {
//                            e.printStackTrace()
//                        }
//                    }
//                }

            }
        }
    }

    val cacheListener = object : MediaPlayer.EventListener {
        override fun onEvent(event: MediaPlayer.Event?) {

        }
    }

    private fun prepareMusic() {

        mMusic?.mUriPath?.let {
            music_title?.text = mMusic?.mTitle ?: ""
            music_artist?.text = mMusic?.mArtistNames?.get(0) ?: ""
            music_album?.text = mMusic?.mAlbumName ?: ""


            if (mMusic?.mArtPath?.startsWith("file:") == true) {
                try {
                    val stream = FileInputStream(mMusic?.mArtPath!!.substring(7))
                    val bitmap = BitmapFactory.decodeStream(stream)
                    img_thumbnail?.setImageBitmap(bitmap)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            try {
                mPlayer = MediaPlayer(mLibMgr.mLibVlc)
                val media = Media(mLibMgr.mLibVlc, it)
                mPlayer?.media = media
                mPlayer?.setEventListener(playListener)
            } catch (e: Exception) {}

        }

    }
}