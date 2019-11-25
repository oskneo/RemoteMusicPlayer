package com.gza21.remotemusicplayer.mods

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable

data class ArtistMod(
    var mName: String?,
    var mAlbums: ArrayList<AlbumMod>?,
    var mPhoto: Bitmap?,
    var mMusics: MutableList<Int>?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.createTypedArrayList(AlbumMod.CREATOR),
        source.readParcelable<Bitmap>(Bitmap::class.java.classLoader),
        ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) }
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(mName)
        writeTypedList(mAlbums)
        writeParcelable(mPhoto, 0)
        writeList(mMusics)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ArtistMod> = object : Parcelable.Creator<ArtistMod> {
            override fun createFromParcel(source: Parcel): ArtistMod = ArtistMod(source)
            override fun newArray(size: Int): Array<ArtistMod?> = arrayOfNulls(size)
        }
    }
}