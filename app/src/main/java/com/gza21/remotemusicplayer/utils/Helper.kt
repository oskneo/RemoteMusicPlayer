package com.gza21.remotemusicplayer.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson
import com.gza21.remotemusicplayer.BaseApp
import com.gza21.remotemusicplayer.mods.ArrayListServerMod
import kotlinx.android.synthetic.main.activity_music_player.*
import org.videolan.BuildConfig
import java.io.File
import java.io.FileInputStream
import java.lang.Exception
import java.lang.ref.WeakReference
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
            if (obj is ArrayListServerMod) {
                for (item in obj.mData) {
                    item.mUri = null
                }
            }
            val jstr = Gson().toJson(obj)
            pref.putString(key, jstr)
            pref.apply()
        }

        fun <T> getSharedPrefForObject(key: String, clazz: Class<T>) : T? {
            val pref = BaseApp.instance.applicationContext.getSharedPreferences(AppConstants.PREF_GLOBAL, Context.MODE_PRIVATE)
            val jstr = pref.getString(key, null) ?: ""
            val mod = Gson().fromJson(jstr, clazz)
            if (mod is ArrayListServerMod) {
                for (item in mod.mData) {
                    item.mUri = Uri.parse(item.mAddress)
                }
            }
            return mod
        }

        fun getBitmapFromPath(path: String?): WeakReference<Bitmap>? {
            var bitmap: Bitmap? = null
            if (path?.startsWith("file:") == true) {
                try {
                    val stream = FileInputStream(path.substring(7))
                    bitmap = BitmapFactory.decodeStream(stream)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            return if (bitmap != null) {
                WeakReference(bitmap)
            } else null
        }

        /**
         * This function can save more memory compared to the above one.
         */
        fun decodeSampledBitmapFromResource(inputUri: Uri, context: Context?, reqWidth: Int, reqHeight: Int): Bitmap? {
            return context?.contentResolver?.openInputStream(inputUri)?.let {

                val options = BitmapFactory.Options()
                options.inJustDecodeBounds = true
                BitmapFactory.decodeStream(it, null, options)
                options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
                options.inJustDecodeBounds = false
                context.contentResolver?.openInputStream(inputUri)?.let {
                    BitmapFactory.decodeStream(it, null, options)
                }
            }
        }

        fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
            val width = options.outWidth
            val height = options.outHeight
            var inSampleSize = 1

            if (height > reqHeight || width > reqWidth) {
                while (height / inSampleSize >= reqHeight && width / inSampleSize >= reqWidth) {
                    inSampleSize *= 2
                }
            }
            return inSampleSize
        }


    }
}