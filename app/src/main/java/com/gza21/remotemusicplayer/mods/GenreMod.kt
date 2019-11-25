package com.gza21.remotemusicplayer.mods

import android.os.Parcel
import android.os.Parcelable

data class GenreMod(
    var mName: String?,
    var mMusics: Array<Int>?,
    var mAlbums: ArrayList<AlbumMod>?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.createIntArray()?.toTypedArray(),
        source.createTypedArrayList(AlbumMod.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(mName)
        writeIntArray(mMusics?.toIntArray())
        writeTypedList(mAlbums)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<GenreMod> = object : Parcelable.Creator<GenreMod> {
            override fun createFromParcel(source: Parcel): GenreMod = GenreMod(source)
            override fun newArray(size: Int): Array<GenreMod?> = arrayOfNulls(size)
        }
    }
}