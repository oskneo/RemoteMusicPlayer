package com.gza21.remotemusicplayer.mods

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import com.gza21.remotemusicplayer.managers.MusicDBManager
import com.gza21.remotemusicplayer.utils.IndexInterface
import com.hierynomus.smbj.share.File
import org.videolan.libvlc.Media
//import ealvatag.audio.AudioFileIO
//import ealvatag.tag.FieldKey
//import ealvatag.tag.NullTag

data class MusicMod(
    var mFileName: String? = null,
    var mArtPath: String = "",
    var mSize: Long? = null,
    var mTitle: String = "",
    var mCodec: String? = null,
    var mType: String? = null,
    var mIndexInCache: Int = -1,
    var mAlbumName: String = "",
    var mAlbumIndex: Int = -1,
    var mArtistNames: ArrayList<String> = arrayListOf(),
    var mArtistInduces: ArrayList<Int> = arrayListOf(),
    var mPlaylists: ArrayList<Int> = arrayListOf(),
    var mGenre: String = "",
    var mGenreInduce: ArrayList<Int> = arrayListOf(),
    override var mIndex: Int = -1,
    var mBitrate: Int = 0,
    var mChannelNumber: Int = 0,
    var mYear: String? = null,
    var mTrack: String? = null,
    var mDiskNo: String? = null,
    var mUri: Uri? = null,
    var mDuration: Long = 0L

) : Parcelable, IndexInterface<MusicMod> {
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
        source.readString(),
        source.readParcelable(Uri::class.java.classLoader),
        source.readLong()
    )

    constructor(media: Media) : this() {
        loadMusicMedia(media)
    }

    override fun compareTo(other: MusicMod): Int {
        return MusicDBManager.instance.compare(this.mTitle, other.mTitle)
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(mFileName)
        writeString(mArtPath)
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
        writeInt(mIndex)
        writeInt(mBitrate)
        writeInt(mChannelNumber)
        writeString(mYear)
        writeString(mTrack)
        writeString(mDiskNo)
        writeParcelable(mUri, 0)
        writeLong(mDuration)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<MusicMod> = object : Parcelable.Creator<MusicMod> {
            override fun createFromParcel(source: Parcel): MusicMod = MusicMod(source)
            override fun newArray(size: Int): Array<MusicMod?> = arrayOfNulls(size)
        }
    }

    fun loadMusicFile(file: File, path: String, size: Int) {

//        val f = java.io.File(file.fileId.toString())
//        val buffer = ByteArray(size)
//        file.read(buffer, 0)
//        val os = FileOutputStream(f)
//        os.write(buffer, 0, size)
//        val audioFile = AudioFileIO.read(f)
//        val header = audioFile.audioHeader
//
//
////        Channels.newChannel( file.inputStream )
////        Channels.newInputStream(raf.getChannel())
//
//        mChannelNumber = header.channelCount
//        mBitrate = header.bitRate
//        mCodec = header.encodingType
//        mSize = size.toLong()
//        mPath = path
//
//        val tag = audioFile.tag.or(NullTag.INSTANCE)
//        mGenre =  tag.getValue(FieldKey.GENRE).or("")
//        mTitle = tag.getValue(FieldKey.TITLE).or("")
//        mAlbumName = tag.getValue(FieldKey.ALBUM).or("")
//        mArtistNames.add(tag.getValue(FieldKey.ARTIST).or(""))
//        mYear = tag.getValue(FieldKey.YEAR).or("")
//        mTrack = tag.getValue(FieldKey.TRACK).or("")
//        mDiskNo = tag.getValue(FieldKey.DISC_NO).or("")
    }

    fun loadMusicMedia(music: Media) {


        mGenre =  music.getMeta(Media.Meta.Genre)
        mTitle = music.getMeta(Media.Meta.Title)
        mAlbumName = music.getMeta(Media.Meta.Album)
        mArtistNames.add(music.getMeta(Media.Meta.Artist))
        mYear = music.getMeta(Media.Meta.Date)
        mTrack = music.getMeta(Media.Meta.TrackNumber)
        mDiskNo = music.getMeta(Media.Meta.DiscNumber)
        mUri = music.uri
        mArtPath = music.getMeta(Media.Meta.ArtworkURL)
        mSize = music.stats?.readBytes?.toLong() ?: 0
//        mCodec = music.stats?.
        mBitrate = music.stats?.inputBitrate?.toInt() ?: 0
        mDuration = music.duration


    }
}