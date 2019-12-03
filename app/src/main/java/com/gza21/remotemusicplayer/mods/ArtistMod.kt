package com.gza21.remotemusicplayer.mods

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import com.gza21.remotemusicplayer.managers.MusicDBManager
import com.gza21.remotemusicplayer.utils.IndexInterface

data class ArtistMod(
    var mName: String = "",
    var mAlbums: ArrayList<Int> = arrayListOf(),
    var mMusics: ArrayList<Int> = arrayListOf(),
    override var mIndex: Int = -1
) : Parcelable, IndexInterface<ArtistMod> {
    constructor(source: Parcel) : this(
        source.readString(),
        ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) },
        ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) },
        source.readInt()
    )

    override fun compareTo(other: ArtistMod): Int {
        return MusicDBManager.instance.compare(this.mName, other.mName)
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(mName)
        writeList(mAlbums)
        writeList(mMusics)
        writeInt(mIndex)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ArtistMod> = object : Parcelable.Creator<ArtistMod> {
            override fun createFromParcel(source: Parcel): ArtistMod = ArtistMod(source)
            override fun newArray(size: Int): Array<ArtistMod?> = arrayOfNulls(size)
        }
    }
}