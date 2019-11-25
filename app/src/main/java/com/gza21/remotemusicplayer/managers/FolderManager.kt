package com.gza21.remotemusicplayer.managers

import com.gza21.remotemusicplayer.utils.AppConstants

class FolderManager {

    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { FolderManager() }
    }

    private val mFolders: ArrayList<String> = arrayListOf()

    fun getFolders(): ArrayList<String> {
        return mFolders
    }

    fun setFolders(folders: ArrayList<String>) {
        mFolders.clear()
        mFolders.add(AppConstants.PREVIOUS_DIR)
        mFolders.addAll(folders)
    }

    fun addFolder(folder: String) {
        mFolders.add(folder)
    }

    fun removeFolder(folder: String) {
        mFolders.remove(folder)
    }

    fun clearFolders() {
        mFolders.clear()
    }
}