package com.gza21.remotemusicplayer.mods

import android.os.Parcel
import android.os.Parcelable

data class MusicMod(
    var mFileName: String?,
    var mPath: String? = null,
    var mSize: Long? = null,
    var mTitle: String? = null,
    var mCodec: String? = null,
    var mType: String? = null,
    var mIndexInCache: Int = -1,
    var mAlbumName: String? = null,
    var mAlbumIndex: Int = -1,
    var mArtistNames: ArrayList<String> = arrayListOf(),
    var mArtistInduces: Array<Int> = arrayOf(),
    var mPlaylists: Array<Int> = arrayOf(),
    var mGenres: Array<Int> = arrayOf(),
    var mIndexInModList: Int = -1

) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readValue(Long::class.java.classLoader) as Long?,
        source.readString(),
        source.readString(),
        source.readString(),
        source.readInt(),
        source.readString(),
        source.readInt(),
        source.createStringArrayList(),
        source.createIntArray().toTypedArray(),
        source.createIntArray().toTypedArray(),
        source.createIntArray().toTypedArray(),
        source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(mFileName)
        writeString(mPath)
        writeValue(mSize)
        writeString(mTitle)
        writeString(mCodec)
        writeString(mType)
        writeInt(mIndexInCache)
        writeString(mAlbumName)
        writeInt(mAlbumIndex)
        writeStringList(mArtistNames)
        writeIntArray(mArtistInduces.toIntArray())
        writeIntArray(mPlaylists.toIntArray())
        writeIntArray(mGenres.toIntArray())
        writeInt(mIndexInModList)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<MusicMod> = object : Parcelable.Creator<MusicMod> {
            override fun createFromParcel(source: Parcel): MusicMod = MusicMod(source)
            override fun newArray(size: Int): Array<MusicMod?> = arrayOfNulls(size)
        }
    }
}