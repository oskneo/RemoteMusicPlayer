package com.gza21.remotemusicplayer.activities

import android.content.Intent
import android.media.MediaDataSource
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.gza21.remotemusicplayer.R
import com.gza21.remotemusicplayer.managers.TempDataManager
import kotlinx.android.synthetic.main.app_bar_main.*
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer
import org.videolan.libvlc.util.VLCVideoLayout
import java.io.IOException
import java.lang.Exception
import java.util.*

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    var mLibVLC: LibVLC? = null
    var mMediaPlayer: MediaPlayer? = null
    private var mVideoLayout: VLCVideoLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
        playSmbSampleDemo()
    }

    override fun getLayoutView(): Int {
        return super.getLayoutView()
    }

    private fun playDemo() {
        try {
            val args = ArrayList<String>()
            args.add("-vvv")
            mLibVLC = LibVLC(this, args)
            mMediaPlayer = MediaPlayer(mLibVLC)

            mVideoLayout = findViewById<VLCVideoLayout>(R.id.video_layout)

            mMediaPlayer!!.attachViews(
                mVideoLayout!!,
                null,
                false,
                false
            )

            val fd = resources.openRawResourceFd(0)//R.raw.bbb
            try {
                val media = Media(
                    mLibVLC,
                    fd
                )
                mMediaPlayer!!.media = media
                media.release()
                media.getMeta(Media.Meta.ArtworkURL)
            } catch (e: IOException) {
                throw RuntimeException("Invalid asset folder")
            }
            mMediaPlayer!!.play()
        } catch (e: Exception) {

        }

    }

    private fun playSmbSampleDemo() {
//        val test = MediaMetadataRetriever()
//        test.setDataSource("")
//        val source = MediaDataSource()
//        test.embeddedPicture
//        val uri = Uri.parse("smb://osk666neo:666666@192.168.11.65/J/Music/STH/Duca - ISI.m4a")
//        val player = android.media.MediaPlayer.create(this, uri)
        val player = android.media.MediaPlayer()
        player.setDataSource()
        player.start()
    }

    override fun onStart() {
        super.onStart()
        val view = findViewById<View>(R.id.drawer_layout)
        view.postDelayed({
            TempDataManager.instance.height = view.height
            TempDataManager.instance.width = view.width
        }, 100)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle the camera action
            }
            R.id.nav_local_network -> {
                Intent(this, NetworkActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_tools -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
