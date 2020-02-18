package com.gza21.remotemusicplayer.managers

import com.gza21.remotemusicplayer.mods.DataBaseMod
import com.gza21.remotemusicplayer.mods.MusicMod
import com.gza21.remotemusicplayer.mods.ServerMod
import com.hierynomus.msdtyp.AccessMask
import com.hierynomus.msfscc.FileAttributes
import com.hierynomus.msfscc.fileinformation.FileIdBothDirectoryInformation
import com.hierynomus.mssmb2.SMB2ShareAccess
import com.hierynomus.protocol.commons.EnumWithValue
import com.hierynomus.smbj.SMBClient
import com.hierynomus.smbj.SmbConfig
import com.hierynomus.smbj.auth.AuthenticationContext
import com.hierynomus.smbj.connection.Connection
import com.hierynomus.smbj.session.Session
import com.hierynomus.smbj.share.DiskShare
import java.lang.Exception
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

class NetworkManager {

    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { NetworkManager() }
    }
    private var mSrcDir = ""
    private var mDstDir = ""

    private val TransactTimeOut = 120L
    private val SocketTimeOut = 180L
    private val mFdMgr = FolderManager.instance


    private var mConfig: SmbConfig? = null
    private var mShare: DiskShare? = null
    private var mClient: SMBClient? = null
    private var mConnection: Connection? = null
    private var mAc: AuthenticationContext? = null
    private var mSession: Session? = null

    private var mCurrentDir = ""
    private var mServer: ServerMod? = null
    private var mIsConnected = false
    private var mThread: Thread? = null

    private val mDbMgr = MusicDBManager.instance




    fun setServer(server: ServerMod) {
        mServer = server
    }

    private fun setConfig() {
        mConfig = SmbConfig.builder().withTimeout(TransactTimeOut, TimeUnit.SECONDS)
            .withTimeout(TransactTimeOut, TimeUnit.SECONDS) // 超时设置读，写和Transact超时（默认为60秒）
            .withSoTimeout(SocketTimeOut, TimeUnit.SECONDS) // Socket超时（默认为0秒）
            .build()
    }

    fun login(username: String, password: String) {
        mServer?.mUserName = username
        mServer?.mPassword = password
    }

    fun connect() {
        if (mServer == null) {
            return
        }
        try {
            if (mThread == null) {
                mThread = Thread()
            }

            mThread?.run {
                setConfig()
                mClient = SMBClient()
                mConnection = mClient?.connect(mServer?.mAddress)	// 如:123.123.123.123
                mAc = AuthenticationContext(mServer?.mUserName, mServer?.mPassword?.toCharArray(), mServer?.mDomain)

                mSession = mConnection?.authenticate(mAc)

                // 连接共享文件夹
                mShare = mSession?.connectShare(mSrcDir) as DiskShare
                synchronized(this@NetworkManager) {
                    mIsConnected = true
                    ServerManager.instance.setServerConnected(mServer)
                }
            }




        } catch (e: Exception) {
            e.printStackTrace()
            disconnect()
        }
    }

    fun disconnect() {
        mThread?.run {
            try {
                mSession?.close()
            } catch (e: Exception) {}
        }
        synchronized(this@NetworkManager) {
            mIsConnected = false
        }
    }

    fun openFolder(folder: String) {
        mCurrentDir = folder
        mDstDir +=  folder + "\\"
        mFdMgr.setFolders(getCurrentFolderList())
    }

    fun backToPrevDir() {
        if (mDstDir.length >= mCurrentDir.length && !mDstDir.isEmpty()) {
            val list = mDstDir.split('\\', ignoreCase = false, limit = Int.MAX_VALUE)
            mDstDir = mDstDir.take(mDstDir.length - mCurrentDir.length - 1)
            if (mDstDir.isEmpty()) {
                mCurrentDir = ""
            } else {
                mCurrentDir = list.get(list.size - 2)
            }
            mFdMgr.setFolders(getCurrentFolderList())
        }
    }

    fun getCurrentFolderList(destDir: String = mDstDir,
                             items: ArrayList<FileIdBothDirectoryInformation>? = getCurrentItems(destDir)
    ): ArrayList<String> {
        val folders = arrayListOf<String>()
        items?.let {
            for (f in it) {
                if (EnumWithValue.EnumUtils.isSet(f.getFileAttributes(), FileAttributes.FILE_ATTRIBUTE_DIRECTORY)) {
                    folders.add(f.fileName)
                }
            }
        }
        return folders
    }

    fun getCurrentFileList(destDir: String = mDstDir,
                           items: ArrayList<FileIdBothDirectoryInformation>? = getCurrentItems(destDir)
    ): ArrayList<FileIdBothDirectoryInformation> {
        val files = arrayListOf<FileIdBothDirectoryInformation>()
        items?.let {
            for (f in it) {
                if (!EnumWithValue.EnumUtils.isSet(f.getFileAttributes(), FileAttributes.FILE_ATTRIBUTE_DIRECTORY)) {
                    files.add(f)
                }
            }
        }
        return files
    }

    fun getCurrentItems(destDir: String = mDstDir): ArrayList<FileIdBothDirectoryInformation>? {
        val folders = arrayListOf<FileIdBothDirectoryInformation>()
        mShare?.list(destDir)?.let {
            folders.addAll(it)
            return folders
        } ?: run { return null }
    }

    fun resetParams() {
        mDstDir = ""
    }

    fun selectFolder() {
        val db = DataBaseMod(mServer)
        scanFolder(db, mDstDir)
        mDbMgr.setDb(db)
    }

    fun scanFolder(database: DataBaseMod, path: String) {

        val items = getCurrentItems(path)
        for (folder in getCurrentFolderList(path, items)) {
            scanFolder(database, path + folder + "\\")
        }

        for (file in getCurrentFileList(path, items)) {

            val s = HashSet<SMB2ShareAccess>()
            s.add(SMB2ShareAccess.ALL.iterator().next())
            val f = mShare?.openFile(path + file.fileName, EnumSet.of(AccessMask.GENERIC_READ), null, s, null, null)
            val size = file.allocationSize
            val music = MusicMod()
            music.mFileName = file.fileName
            f?.let {
                music.loadMusicFile(it, path + file.fileName, size.toInt())
                database.addMusic(music)
            }
        }
    }

}