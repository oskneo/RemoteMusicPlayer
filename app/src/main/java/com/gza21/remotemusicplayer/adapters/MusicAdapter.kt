package com.gza21.remotemusicplayer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.gza21.remotemusicplayer.R
import com.gza21.remotemusicplayer.managers.MusicDBManager
import com.gza21.remotemusicplayer.entities.MusicMod
import java.lang.StringBuilder

class MusicAdapter(val context: Context, val listener: MusicListener) : BaseAdapter() {

    private val mMsMgr = MusicDBManager.instance
    var mMusics: ArrayList<MusicMod> = arrayListOf()

    fun updateMusics() {
        mMusics.clear()
        mMusics.addAll(mMsMgr.getMusicList())
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return mMusics.size
    }

    override fun getItem(position: Int): MusicMod {
        return mMusics.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (view == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.view_music_item, null)
        }
        val music = getItem(position)
        view?.findViewById<ImageView>(R.id.thumb_nail)
        view?.findViewById<TextView>(R.id.music_title)?.text = music.mTitle
        var artists = StringBuilder()
        artists.append("")
        for (artist in music.mArtistNames) {
            if (artists.length > 0) {
                artists.append(", ")
            }
            artists.append(artist)
        }
        view?.findViewById<TextView>(R.id.music_artist)?.text = artists
        view?.findViewById<ImageView>(R.id.music_menu)?.setOnClickListener {
            listener.onMenu(music)
        }
        view?.setOnClickListener {
            listener.onPlay(music)
        }
        return view!!
    }

    interface MusicListener {
        fun onPlay(music: MusicMod)
        fun onMenu(music: MusicMod)
        fun onDetail(music: MusicMod)
    }
}