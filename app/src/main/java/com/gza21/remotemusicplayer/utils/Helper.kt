package com.gza21.remotemusicplayer.utils

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson
import com.gza21.remotemusicplayer.BaseApp
import org.videolan.BuildConfig
import java.io.File
import java.util.*

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

        fun convertMilisec2Min(miliSec: Long): String {

            val sec = miliSec / 1000
            val min = sec / 60
            val r = sec % 60
            return "$min:${if (r < 10) "0$r" else "$r"}"
        }

        fun isCorrectExt(uri: Uri?): Boolean {
            val address = uri?.toString() ?: ""
            val ext = address.substring(address.lastIndexOf('.')).toLowerCase(Locale.US)
            val allowedExt = listOf(".m4a", ".mp4", ".flac", ".wav", ".aac", ".mp3")
            return allowedExt.contains(ext)
        }

        fun setSharedPrefForObject(key: String, obj: Any) {
            val pref = BaseApp.instance.applicationContext.getSharedPreferences(AppConstants.PREF_GLOBAL, Context.MODE_PRIVATE).edit()
            val jstr = Gson().toJson(obj)
            pref.putString(key, jstr)
            pref.apply()
        }

        fun <T> getSharedPrefForObject(key: String, clazz: Class<T>) : T? {
            val pref = BaseApp.instance.applicationContext.getSharedPreferences(AppConstants.PREF_GLOBAL, Context.MODE_PRIVATE)
            val jstr = pref.getString(key, null) ?: ""
            return Gson().fromJson(jstr, clazz)
        }


    }
}