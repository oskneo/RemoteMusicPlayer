package com.gza21.remotemusicplayer.managers

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.fragment.app.DialogFragment
import com.gza21.remotemusicplayer.BaseApp
import org.videolan.BuildConfig
import org.videolan.libvlc.Dialog
import org.videolan.libvlc.Dialog.*
import org.videolan.libvlc.LibVLC
import java.io.File
import java.util.*
import java.util.concurrent.atomic.AtomicReference

class LibVlcManager {
    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { LibVlcManager() }
    }

    val mLibVlc: LibVLC
    var CACHE_DIR = ""
    var ART_DIR = AtomicReference<String>()
    var COVER_DIR = AtomicReference<String>()

    var mDialogCallbacks: Callbacks =
        object : Callbacks {
            override fun onDisplay(dialog: ErrorMessage) {
                Log.e("Callbacks", "error")
            }

            override fun onDisplay(dialog: LoginDialog) {
                Log.e("Callbacks", "login")
                ServerManager.instance.showLoginDialog(dialog)
//                dialog.postLogin("osk666neo", "666666", true)
            }

            override fun onDisplay(dialog: QuestionDialog) {
                Log.e("Callbacks", "question")
            }

            override fun onDisplay(dialog: ProgressDialog) {
                Log.e("Callbacks", "progress")
            }

            override fun onCanceled(dialog: Dialog) {
                Log.e("Callbacks", "dialog")
            }

            override fun onProgressUpdate(dialog: ProgressDialog) {
                Log.e("Callbacks", "update")
            }
        }

    init {
        val args = ArrayList<String>()
        args.add("-vvv")
        mLibVlc = LibVLC(BaseApp.instance.applicationContext, args)
    }

    fun setDialogCallback() {
        Dialog.setCallbacks(
            mLibVlc,
            mDialogCallbacks
        )
    }

    fun prepareCacheFolder(context: Context?) {
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && context?.externalCacheDir != null)
                CACHE_DIR = context.externalCacheDir?.path ?: ""
            else CACHE_DIR =
                Environment.getExternalStorageDirectory().path.toString() + "/Android/data/" + BuildConfig.APPLICATION_ID + "/cache"
        } catch (e: Exception) { // catch NPE thrown by getExternalCacheDir()
            CACHE_DIR =
                Environment.getExternalStorageDirectory().path.toString() + "/Android/data/" + BuildConfig.APPLICATION_ID + "/cache"
        } catch (e: ExceptionInInitializerError) {
            CACHE_DIR =
                Environment.getExternalStorageDirectory().path.toString() + "/Android/data/" + BuildConfig.APPLICATION_ID + "/cache"
        }
        ART_DIR.set(CACHE_DIR + "/art/")
        COVER_DIR.set(CACHE_DIR + "/covers/")
        for (path in Arrays.asList<String>(
            ART_DIR.get(),
            COVER_DIR.get()
        )) {
            val file = File(path)
            if (!file.exists()) file.mkdirs()
        }
    }
}