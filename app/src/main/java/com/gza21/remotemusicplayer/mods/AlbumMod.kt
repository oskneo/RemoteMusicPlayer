package com.gza21.remotemusicplayer.mods

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable

data class AlbumMod(
    var mName: String?,
    var mArtist: ArrayList<ArtistMod>?,
    var mMusics: ArrayList<Int>,
    var mPhoto: Bitmap?
) : Parcelable {
    constructor(mName: String?) : this(
        mName,
        arrayListOf(),
        arrayListOf(),
        null
    )
    constructor(source: Parcel) : this(
        source.readString(),
        source.createTypedArrayList(ArtistMod.CREATOR),
        ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) },
        source.readParcelable<Bitmap>(Bitmap::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(mName)
        writeTypedList(mArtist)
        writeList(mMusics)
        writeParcelable(mPhoto, 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<AlbumMod> = object : Parcelable.Creator<AlbumMod> {
            override fun createFromParcel(source: Parcel): AlbumMod = AlbumMod(source)
            override fun newArray(size: Int): Array<AlbumMod?> = arrayOfNulls(size)
        }
    }
}