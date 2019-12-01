package com.gza21.remotemusicplayer.managers

import android.os.Parcel
import com.gza21.remotemusicplayer.mods.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.OutputStream

class MusicDBManager {
    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { MusicDBManager() }
    }

    var mDataBase: DataBaseMod? = null

    fun getMusicList(): ArrayList<MusicMod> {
        return mDataBase?.mMusics ?: arrayListOf()
    }

    fun getAlbumList(): ArrayList<AlbumMod> {
        return mDataBase?.mAlbums ?: arrayListOf()
    }

    fun getArtistList(): ArrayList<ArtistMod> {
        return mDataBase?.mArtists ?: arrayListOf()
    }

    fun setDb(db: DataBaseMod) {
        mDataBase = db
    }
    fun loadDb(serverMod: ServerMod) {

        val path = serverMod.mDbPath
        val file = File(path)
        val bytes = file.readBytes()
        val p = Parcel.obtain()
        p.unmarshall(bytes, 0, bytes.size)
        mDataBase = DataBaseMod(p)
    }

    fun saveDb(serverMod: ServerMod) {

        val p = Parcel.obtain()
        mDataBase?.writeToParcel(p, 0)
        val bytes = p.marshall()
        val path = "db"
        val file = FileOutputStream(path)
        file.write(bytes)
        file.close()
        serverMod.mDbPath = path
    }
}