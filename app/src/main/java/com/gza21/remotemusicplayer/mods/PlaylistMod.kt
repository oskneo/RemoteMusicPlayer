package com.gza21.remotemusicplayer.mods

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable

data class PlaylistMod(
    var mName: String?,
    var mPhoto: Bitmap?,
    var mMusics: Array<Int>?,
    var mAlbums: ArrayList<AlbumMod>?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readParcelable<Bitmap>(Bitmap::class.java.classLoader),
        source.createIntArray()?.toTypedArray(),
        source.createTypedArrayList(AlbumMod.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(mName)
        writeParcelable(mPhoto, 0)
        writeIntArray(mMusics?.toIntArray())
        writeTypedList(mAlbums)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<PlaylistMod> = object : Parcelable.Creator<PlaylistMod> {
            override fun createFromParcel(source: Parcel): PlaylistMod = PlaylistMod(source)
            override fun newArray(size: Int): Array<PlaylistMod?> = arrayOfNulls(size)
        }
    }
}