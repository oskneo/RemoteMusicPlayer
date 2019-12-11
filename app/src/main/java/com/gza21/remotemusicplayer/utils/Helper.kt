package com.gza21.remotemusicplayer.utils

import android.content.ComponentCallbacks
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.gza21.remotemusicplayer.R
import com.gza21.remotemusicplayer.managers.ServerManager
import com.gza21.remotemusicplayer.mods.ServerMod

class Helper {
    companion object {

        fun getAlertDialog(context: Context, alertDialog: AlertDialog?, view: View,
                           @StringRes okId: Int, callbacks: () -> Unit): AlertDialog {
            if (alertDialog?.isShowing == true) {
                alertDialog.dismiss()
            }
            val mDialog = AlertDialog.Builder(context)
                .setView(view)
                .setPositiveButton(okId, { dialog, _ ->
                    callbacks()
                    dialog.dismiss()
                })
                .setNegativeButton("Cancel", { dialog, which ->
                    dialog?.dismiss()
                }).create()
            mDialog.show()
            return  mDialog
        }
    }
}