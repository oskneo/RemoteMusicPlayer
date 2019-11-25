package com.gza21.remotemusicplayer.mods

import android.os.Parcel
import android.os.Parcelable

data class DataBaseMod(
    var mMusics: ArrayList<MusicMod>?,
    var mAlbums: ArrayList<AlbumMod>?,
    var mArtists: ArrayList<ArtistMod>?,
    var mPlaylists: ArrayList<PlaylistMod>?,
    var mGenres: ArrayList<GenreMod>?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.createTypedArrayList(MusicMod.CREATOR),
        source.createTypedArrayList(AlbumMod.CREATOR),
        source.createTypedArrayList(ArtistMod.CREATOR),
        source.createTypedArrayList(PlaylistMod.CREATOR),
        source.createTypedArrayList(GenreMod.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeTypedList(mMusics)
        writeTypedList(mAlbums)
        writeTypedList(mArtists)
        writeTypedList(mPlaylists)
        writeTypedList(mGenres)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<DataBaseMod> = object : Parcelable.Creator<DataBaseMod> {
            override fun createFromParcel(source: Parcel): DataBaseMod = DataBaseMod(source)
            override fun newArray(size: Int): Array<DataBaseMod?> = arrayOfNulls(size)
        }
    }
}