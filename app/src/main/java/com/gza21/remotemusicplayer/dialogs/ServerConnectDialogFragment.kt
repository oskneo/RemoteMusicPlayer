package com.gza21.remotemusicplayer.dialogs

import android.os.Bundle
import android.os.Parcel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.gza21.remotemusicplayer.R
import com.gza21.remotemusicplayer.activities.NetworkActivity
import com.gza21.remotemusicplayer.managers.NetworkManager
import com.gza21.remotemusicplayer.managers.ServerManager
import com.gza21.remotemusicplayer.mods.ServerMod
import com.hierynomus.msdtyp.AccessMask
import com.hierynomus.mssmb2.SMB2CreateDisposition
import com.hierynomus.mssmb2.SMB2ShareAccess
import com.hierynomus.smbj.SMBClient
import com.hierynomus.smbj.SmbConfig
import com.hierynomus.smbj.auth.AuthenticationContext
import com.hierynomus.smbj.share.DiskShare
import java.io.BufferedOutputStream
import java.io.FileOutputStream
import java.io.Serializable
import java.util.*
import java.util.concurrent.TimeUnit

class ServerConnectDialogFragment : DialogFragment() {

    val LOG_TAG = ServerConnectDialogFragment::class.java.simpleName

    var mContentView: View? = null
    var mServer: ServerMod? = null
    val SHARE_USER = "SHARE_USER"
    val SHARE_PASSWORD = "SHARE_PASSWORD"
    val SHARE_DOMAIN = "SHARE_DOMAIN"
    val SHARE_SRC_DIR = "SHARE_SRC_DIR"
    val SHARE_DST_DIR = "SHARE_DST_DIR"
    val mNwMgr = NetworkManager.instance

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContentView = inflater.inflate(R.layout.dialog_server_connect, container)
        return mContentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContentView?.let {
            it.findViewById<Button>(R.id.btn_connect)?.setOnClickListener {
                connect()
            }
            it.findViewById<Button>(R.id.btn_disconnect)?.setOnClickListener {
                disconnect()
            }
            it.findViewById<Button>(R.id.btn_connect)?.setOnClickListener {
                delete()
            }
            it.findViewById<Button>(R.id.btn_edit)?.setOnClickListener {
                edit()
            }
        }
    }

    private fun connect() {

        mServer?.let {
            mNwMgr.setServer(it)
            mNwMgr.connect()
            (activity as NetworkActivity).updateList()
        }

        val config = SmbConfig.builder().withTimeout(120, TimeUnit.SECONDS)
            .withTimeout(120, TimeUnit.SECONDS) // 超时设置读，写和Transact超时（默认为60秒）
            .withSoTimeout(180, TimeUnit.SECONDS) // Socket超时（默认为0秒）
            .build()
        val client = SMBClient()

        try {
            val connection = client.connect("IP或域名")	// 如:123.123.123.123
            val ac = AuthenticationContext(SHARE_USER, SHARE_PASSWORD.toCharArray(), SHARE_DOMAIN)
            val session = connection.authenticate(ac)

            // 连接共享文件夹
            val share = session.connectShare(SHARE_SRC_DIR) as DiskShare

            val folder: String = SHARE_SRC_DIR + SHARE_DST_DIR
            val dstRoot: String = "要保存的本地文件夹路径"	// 如: D:/smd2/

            for ( f in share.list(SHARE_DST_DIR, "*.mp4")) {
                val filePath = folder + f.fileName
                val dstPath = dstRoot + f.getFileName()

                val fos = FileOutputStream(dstPath)
                val bos = BufferedOutputStream(fos)


                if (share.fileExists(filePath)) {
                    System.out.println("正在下载文件:" + f.getFileName())

                    val smbFileRead = share.openFile(filePath, EnumSet.of(AccessMask.GENERIC_READ),
                        null, SMB2ShareAccess.ALL, SMB2CreateDisposition.FILE_OPEN, null)
                    val input = smbFileRead.getInputStream()
                    val buffer = ByteArray(4096)
                    var len = 0

                    while (len != -1) {
                        len = input.read(buffer, 0, buffer.size)
                        bos.write(buffer, 0, len)
                    }

                    bos.flush()
                    bos.close()

                    System.out.println("文件下载成功");
                    System.out.println("==========================");
                } else {
                    System.out.println("文件不存在");
                }
            }
        } catch (e: Exception) {
            e.printStackTrace();
        } finally {
            client.close()
        }
        dismiss()
    }

    private fun disconnect() {
        mNwMgr.disconnect()
        dismiss()
    }

    private fun delete() {
        disconnect()
        ServerManager.instance.remove(mServer)
        if (activity is NetworkActivity) {
            (activity as NetworkActivity?)?.updateList()
        }
    }

    private fun edit() {
        if (activity is NetworkActivity) {
            (activity as NetworkActivity?)?.getServerDialog(mServer)
        }
        dismiss()
    }
}