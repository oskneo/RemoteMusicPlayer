package com.gza21.remotemusicplayer.utils

import android.media.MediaDataSource
import com.gza21.remotemusicplayer.managers.NetworkManager

class MyMediaDataSource(path: String) : MediaDataSource() {

    private val mPath: String
    private val mFileName: String
    private val mNwMgr = NetworkManager.instance
    init {
        mPath = path.replaceAfterLast('\\', "")
        mFileName = path.substringAfterLast('\\')
        mNwMgr
    }

    override fun getSize(): Long {
        mNwMgr.openFolder(mPath)
        mNwMgr.connect()
        return mNwMgr.getCurrentItems()?.find {
            it.fileName == mFileName
        }?.allocationSize ?: 0L
    }

    override fun readAt(position: Long, buffer: ByteArray?, offset: Int, size: Int): Int {

        buffer?.let {
            mNwMgr.openFolder(mPath)
            mNwMgr.connect()
            mNwMgr.openFile(mPath + mFileName)?.let { file ->
                val input = file.inputStream
                var remaining = size
                input.skip(position)
                while (true) {
                    val haveRead = input.read(it, offset, size)
                }

            }
        }
    }

    override fun close() {
        mNwMgr.disconnect()
    }
}