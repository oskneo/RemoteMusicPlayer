package com.gza21.remotemusicplayer.activities

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.gza21.remotemusicplayer.R

open class BaseActivity : AppCompatActivity() {

    var mAlertDialog: AlertDialog? = null
    var mRootView: ViewGroup? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLayout()
    }

    private fun loadLayout() {
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        val layoutViewId = getLayoutView()
        if (layoutViewId != 0 && mRootView == null) {
            val root = findViewById<RelativeLayout>(R.id.root_view)
            mRootView = layoutInflater.inflate(layoutViewId, root) as ViewGroup?
            root?.addView(mRootView)
        }

    }

    open fun getLayoutView() : Int {
        return 0
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}