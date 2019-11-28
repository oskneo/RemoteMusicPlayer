package com.gza21.remotemusicplayer.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import com.gza21.remotemusicplayer.R
import com.gza21.remotemusicplayer.adapters.MusicAdapter
import com.gza21.remotemusicplayer.adapters.ServerAdapter
import com.gza21.remotemusicplayer.mods.MusicMod
import com.gza21.remotemusicplayer.mods.ServerMod

class RemoteMusicActivity : BaseActivity() {

    var mAdapter: MusicAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getList()

    }

    private fun getList() {

        val listView = findViewById<ListView>(R.id.music_list)
        mAdapter = MusicAdapter(this, object : MusicAdapter.MusicListener {
            override fun onDetail(music: MusicMod) {

            }

            override fun onMenu(music: MusicMod) {

            }

            override fun onPlay(music: MusicMod) {

            }
        })
        listView?.adapter = mAdapter
    }

    fun updateList() {
        mAdapter?.updateMusics()
    }

    override fun getLayoutView(): Int {
        return R.layout.activity_remote_musics
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_network, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.findItem(R.id.action_search)?.setTitle("Search")
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId ?: 0 == R.id.action_add) {
            search()
        }
        return super.onOptionsItemSelected(item)
    }

    fun getEditedContent(view: View?, id: Int) : String {
        return view?.findViewById<LinearLayout>(id)?.
            findViewById<EditText>(R.id.edit_text)?.text?.toString() ?: ""
    }

    fun search() {

    }

}