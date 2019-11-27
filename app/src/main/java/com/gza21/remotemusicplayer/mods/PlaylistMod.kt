package com.gza21.remotemusicplayer.mods

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable

data class PlaylistMod(
    var mName: String?,
    var mPhoto: Bitmap? = null,
    var mMusics: ArrayList<Int>? = arrayListOf(),
    var mAlbums: ArrayList<Int>? = arrayListOf(),
    var mIndex: Int = -1
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readParcelable<Bitmap>(Bitmap::class.java.classLoader),
        ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) },
        ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) },
        source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(mName)
        writeParcelable(mPhoto, 0)
        writeList(mMusics)
        writeList(mAlbums)
        writeInt(mIndex)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<PlaylistMod> = object : Parcelable.Creator<PlaylistMod> {
            override fun createFromParcel(source: Parcel): PlaylistMod = PlaylistMod(source)
            override fun newArray(size: Int): Array<PlaylistMod?> = arrayOfNulls(size)
        }
    }
}