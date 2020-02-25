package com.gza21.remotemusicplayer.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gza21.remotemusicplayer.R
import com.gza21.remotemusicplayer.managers.ServerManager
import com.gza21.remotemusicplayer.entities.ServerMod


class ServerAdapter(val context: Context, val listener: ServerListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mSvrMgr = ServerManager.instance
    var mSources: ArrayList<Any> = arrayListOf()
    var mType = 0

    class ServerViewHolder(var layoutView: View) : RecyclerView.ViewHolder(layoutView) {
        val mName: TextView
        val mAddress: TextView
        val mStatus: TextView

        init {
            mName = layoutView.findViewById<TextView>(R.id.server_name)
            mAddress = layoutView.findViewById<TextView>(R.id.server_address)
            mStatus = layoutView.findViewById<TextView>(R.id.server_status)
        }
    }

    init {
        mSources.clear()
    }

    fun updateServers() {
        mSources.clear()
        val list = mSvrMgr.getServers()
        Log.e("FilePath", "number of file: ${list.size}")
        mSources.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mSources.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val server = getItem(position)
        if (holder is ServerViewHolder && server is ServerMod) {
            holder.layoutView.setOnClickListener {
                listener.onConnect(server)
            }
            holder.mAddress.text = server.mAddress
            holder.mName.text = server.mName
            holder.mStatus.setText(if (server.mIsStored) R.string.connected else R.string.not_connected)
        } else {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_server_item, parent, false)
        val viewHolder = ServerViewHolder(view)
        view.tag = viewHolder
        return viewHolder
    }

    fun getItem(position: Int): Any {
        return mSources.get(position)
    }

    interface ServerListener {
        fun onConnect(server: ServerMod)
    }
}