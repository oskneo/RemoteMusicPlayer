package com.gza21.remotemusicplayer.managers

import android.util.Log
import androidx.fragment.app.DialogFragment
import com.gza21.remotemusicplayer.BaseApp
import org.videolan.libvlc.Dialog
import org.videolan.libvlc.Dialog.*
import org.videolan.libvlc.LibVLC
import java.util.*

class LibVlcManager {
    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { LibVlcManager() }
    }

    val mLibVlc: LibVLC

    var mDialogCallbacks: Callbacks =
        object : Callbacks {
            override fun onDisplay(dialog: ErrorMessage) {
            }

            override fun onDisplay(dialog: LoginDialog) {
                dialog.postLogin("osk666neo", "666666", true)
            }

            override fun onDisplay(dialog: QuestionDialog) {
            }

            override fun onDisplay(dialog: ProgressDialog) {
            }

            override fun onCanceled(dialog: Dialog) {
            }

            override fun onProgressUpdate(dialog: ProgressDialog) {
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
}