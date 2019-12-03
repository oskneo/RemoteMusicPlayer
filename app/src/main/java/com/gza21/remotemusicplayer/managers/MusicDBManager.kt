package com.gza21.remotemusicplayer.managers

import android.os.Parcel
import com.gza21.remotemusicplayer.mods.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.Collator
import java.util.*
import kotlin.collections.ArrayList

class MusicDBManager {
    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { MusicDBManager() }
    }

    var mDataBase: DataBaseMod? = null
    var mLocale: Locale = Locale.CHINA
    var mCollator = Collator.getInstance(mLocale)

    fun setLocale(locale: Locale) {
        mLocale = locale
        mCollator = Collator.getInstance(mLocale)
    }

    fun getCollator(): Collator {
        return mCollator
    }

    fun compare(source: String, target: String): Int {
        return mCollator.compare(source, target)
    }

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