package com.gza21.remotemusicplayer.mods

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable

data class ArtistMod(
    var mName: String?,
    var mAlbums: ArrayList<Int> = arrayListOf(),
    var mMusics: ArrayList<Int> = arrayListOf(),
    var mIndex: Int = -1
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) },
        ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) },
        source.readInt()
    )

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