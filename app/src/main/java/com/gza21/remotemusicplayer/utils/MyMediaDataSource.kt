package com.gza21.remotemusicplayer.utils

import android.media.MediaDataSource
import com.gza21.remotemusicplayer.managers.NetworkManager
import java.io.IOException
import java.lang.Exception

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
                var skipRemaining = position
                while (true) {
                    try {
                        val skipped = input.skip(position)
                        if (skipped <= 0 && skipRemaining > 0) {
                            return -1
                        }
                        skipRemaining -= skipped
                        if (skipRemaining == 0L) {
                            break
                        } else if (skipRemaining < 0L) {
                            return -1
                        }
                    } catch (e: Exception) {
                        throw IOException("")
                    }
                }

                var currentOffset = offset
                var currentSize = size
                while (true) {
                    try {
                        val haveRead = input.read(it, currentOffset, currentSize)
                        if (haveRead <= 0 && currentSize > 0) {
                            return size - currentSize
                        }
                        currentOffset += haveRead
                        currentSize -= haveRead
                        if (currentSize == 0) {
                            return size
                        } else if (currentSize < 0) {
                            return -1
                        }
                    } catch (e: Exception) {
                        throw IOException("")
                    }
                }
            }
        }
        return -1
    }

    override fun close() {
        mNwMgr.disconnect()
    }
}