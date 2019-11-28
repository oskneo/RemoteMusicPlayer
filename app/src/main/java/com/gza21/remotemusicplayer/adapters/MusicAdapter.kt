package com.gza21.remotemusicplayer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.gza21.remotemusicplayer.R
import com.gza21.remotemusicplayer.managers.FolderManager
import com.gza21.remotemusicplayer.managers.MusicDBManager
import com.gza21.remotemusicplayer.managers.ServerManager
import com.gza21.remotemusicplayer.mods.MusicMod
import com.gza21.remotemusicplayer.mods.ServerMod

class MusicAdapter(val context: Context, val listener: MusicListener) : BaseAdapter() {

    private val mMsMgr = MusicDBManager.instance
    var mMusics: ArrayList<MusicMod> = arrayListOf()

    init {
        mMusics.clear()
    }

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

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.view_folder_item, null)
        val folderName = getItem(position)
        view.findViewById<TextView>(R.id.folder_item)?.text = folderName
        view.setOnClickListener {
            listener.onSelect(folderName)
        }
        return view
    }

    interface MusicListener {
        fun onPlay(music: MusicMod)
        fun onMenu(music: MusicMod)
        fun onDetail(music: MusicMod)
    }
}