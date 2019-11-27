package com.gza21.remotemusicplayer.mods

import android.os.Parcel
import android.os.Parcelable
import ealvatag.audio.AudioFile
import ealvatag.audio.AudioFileIO
import ealvatag.tag.FieldKey
import ealvatag.tag.NullTag
import java.io.File

data class MusicMod(
    var mFileName: String?,
    var mPath: String = "",
    var mSize: Long? = null,
    var mTitle: String? = null,
    var mCodec: String? = null,
    var mType: String? = null,
    var mIndexInCache: Int = -1,
    var mAlbumName: String? = null,
    var mAlbumIndex: Int = -1,
    var mArtistNames: ArrayList<String> = arrayListOf(),
    var mArtistInduces: ArrayList<Int> = arrayListOf(),
    var mPlaylists: ArrayList<Int> = arrayListOf(),
    var mGenre: String? = null,
    var mGenreInduce: ArrayList<Int> = arrayListOf(),
    var mIndexInModList: Int = -1,
    var mBitrate: Int = 0,
    var mChannelNumber: Int = 0,
    var mYear: String? = null,
    var mTrack: String? = null,
    var mDiskNo: String? = null

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
        ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) },
        ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) },
        source.readString(),
        ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) },
        source.readInt(),
        source.readInt(),
        source.readInt(),
        source.readString(),
        source.readString(),
        source.readString()
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
        writeList(mArtistInduces)
        writeList(mPlaylists)
        writeString(mGenre)
        writeList(mGenreInduce)
        writeInt(mIndexInModList)
        writeInt(mBitrate)
        writeInt(mChannelNumber)
        writeString(mYear)
        writeString(mTrack)
        writeString(mDiskNo)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<MusicMod> = object : Parcelable.Creator<MusicMod> {
            override fun createFromParcel(source: Parcel): MusicMod = MusicMod(source)
            override fun newArray(size: Int): Array<MusicMod?> = arrayOfNulls(size)
        }
    }

    fun loadMusicFile(file: File, path: String) {
        val audioFile = AudioFileIO.read(file)
        val header = audioFile.audioHeader

        mChannelNumber = header.channelCount
        mBitrate = header.bitRate
        mCodec = header.encodingType
        mSize = file.length()
        mPath = path

        val tag = audioFile.tag.or(NullTag.INSTANCE)
        mGenre =  tag.getValue(FieldKey.GENRE).or("")
        mTitle = tag.getValue(FieldKey.TITLE).or("")
        mAlbumName = tag.getValue(FieldKey.ALBUM).or("")
        mArtistNames.add(tag.getValue(FieldKey.ARTIST).or(""))
        mYear = tag.getValue(FieldKey.YEAR).or("")
        mTrack = tag.getValue(FieldKey.TRACK).or("")
        mDiskNo = tag.getValue(FieldKey.DISC_NO).or("")
    }
}