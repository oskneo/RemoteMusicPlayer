package com.gza21.remotemusicplayer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.gza21.remotemusicplayer.R
import com.gza21.remotemusicplayer.managers.ServerManager
import com.gza21.remotemusicplayer.mods.ServerMod

class ServerAdapter(val context: Context, val listener: ServerListener) : BaseAdapter() {

    private val mSvrMgr = ServerManager.instance
    var mServers: ArrayList<ServerMod> = arrayListOf()

    init {
        mServers.clear()
    }

    fun updateServers() {
        mServers.clear()
        mServers.addAll(mSvrMgr.mServers)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return mServers.size
    }

    override fun getItem(position: Int): ServerMod {
        return mServers.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.view_server_item, null)
        val server = getItem(position)
        view.findViewById<TextView>(R.id.server_name)?.text = server.mName
        view.findViewById<TextView>(R.id.server_address)?.text = server.mAddress
        view.findViewById<TextView>(R.id.server_status)?.text = "Not connected"

        view.setOnClickListener {
            listener.onConnect(server)
        }
        return view
    }

    interface ServerListener {
        fun onConnect(server: ServerMod)
    }
}