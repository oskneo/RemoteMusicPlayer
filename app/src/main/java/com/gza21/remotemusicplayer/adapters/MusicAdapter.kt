package com.gza21.remotemusicplayer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.gza21.remotemusicplayer.R
import com.gza21.remotemusicplayer.managers.FolderManager
import com.gza21.remotemusicplayer.managers.ServerManager
import com.gza21.remotemusicplayer.mods.ServerMod

class MusicAdapter(val context: Context, val listener: MusicListener) : BaseAdapter() {

    private val mFdMgr = FolderManager.instance
    var mFolders: ArrayList<String> = arrayListOf()

    init {
        mFolders.clear()
    }

    fun updateFolders() {
        mFolders.clear()
        mFolders.addAll(mFdMgr.getFolders())
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return mFolders.size
    }

    override fun getItem(position: Int): String {
        return mFolders.get(position)
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
        fun onSelect(folder: String)
    }
}