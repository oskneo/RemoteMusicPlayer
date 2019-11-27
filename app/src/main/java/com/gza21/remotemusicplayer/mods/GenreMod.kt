package com.gza21.remotemusicplayer.mods

import android.os.Parcel
import android.os.Parcelable

data class GenreMod(
    var mName: String?,
    var mMusics: ArrayList<Int> = arrayListOf(),
    var mAlbums: ArrayList<Int> = arrayListOf(),
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
        writeList(mMusics)
        writeList(mAlbums)
        writeInt(mIndex)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<GenreMod> = object : Parcelable.Creator<GenreMod> {
            override fun createFromParcel(source: Parcel): GenreMod = GenreMod(source)
            override fun newArray(size: Int): Array<GenreMod?> = arrayOfNulls(size)
        }
    }
}