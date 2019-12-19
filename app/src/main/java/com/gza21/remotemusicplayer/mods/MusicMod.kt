package com.gza21.remotemusicplayer.mods

import android.graphics.Bitmap
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.gza21.remotemusicplayer.managers.LibVlcManager
import com.gza21.remotemusicplayer.managers.MusicDBManager
import com.gza21.remotemusicplayer.managers.TempDataManager
import com.gza21.remotemusicplayer.utils.Helper
import com.gza21.remotemusicplayer.utils.IndexInterface
import com.hierynomus.smbj.share.File
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer
import java.io.FileInputStream
import java.io.FileOutputStream
import java.net.URLDecoder
import java.util.concurrent.Semaphore

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

        private val semaphore = Semaphore(0)
        private var mPlayer: MediaPlayer? = null
        private var mUrl: String? = null



        fun loadUri(uri: Uri?, cacheArt: Boolean = false): MusicMod? {
            mUrl = uri?.toString()
            if (Helper.isCorrectExt(uri)) {
                val music = MusicMod()
                val media = Media(LibVlcManager.instance.mLibVlc, uri)
                var path = ""
                if (cacheArt) {
                    Log.e("Music", "Scan2")
                    val t = Thread()
                    t.run {
                        mPlayer = org.videolan.libvlc.MediaPlayer(media)
                        mPlayer?.volume = 0
                        mPlayer?.play()
                    }
                    while (true) {
                        Thread.sleep(10)
                        if (media.isParsed == true) {
                            break
                        }
                    }
//                    Helper.getBitmapFromPath(media.getMeta(Media.Meta.ArtworkURL))?.get()?.let {
//                        if (it.width > TempDataManager.instance.width) {
//                            val ratio = it.width.toFloat() / it.height
//                            val height = TempDataManager.instance.width / ratio
//                            Bitmap.createScaledBitmap(it, TempDataManager.instance.width, height.toInt(), false)
//                        }
//                    }
                    Log.e("Music", "Scan3")
                    mPlayer?.media?.getMeta(Media.Meta.ArtworkURL)?.let {
                        URLDecoder.decode(it, "UTF-8")
                    }?.let {
                        val file = java.io.File(it.substring(7))
                        val hash = file.hashCode()
                        path = LibVlcManager.instance.ART_DIR.get() + "$hash" + it.takeLast(4)
                        file.copyTo(java.io.File(path), true)
                        file.delete()
                        path = "file://" + path
                    }

                    Log.e("Music", "Scan4")

                    t.run {

                        mPlayer?.stop()
                        mPlayer?.release()
                    }

                } else {
                    Log.e("Music", "Scan02")
                    media.parse(Media.Parse.ParseNetwork)
                    Log.e("Music", "Scan03")
                }
                Log.e("Music", "Scan05")
                music.loadMusicMedia(media, path)
                Log.e("Music", "Scan06")
                media.release()
                return music
            } else {
                return null
            }
        }
        val CachePlayerListener = object : org.videolan.libvlc.MediaPlayer.EventListener {
            override fun onEvent(event: org.videolan.libvlc.MediaPlayer.Event?) {
                Log.e("Sema", "Sema")
                if (mPlayer?.media == null || mUrl == null) {
                    return
                }
                if (mPlayer?.media?.getMeta(Media.Meta.Title)?.endsWith(mUrl!!.takeLast(3)) == false) {
                    semaphore.release()
                    synchronized(this@Companion) {
//                        locked = false
                    }

                }
            }
        }
        val CacheMediaListener = object : org.videolan.libvlc.Media.EventListener {
            override fun onEvent(event: Media.Event?) {
                Log.e("Sema", "Sema")
                semaphore.release()
            }
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

    fun loadMusicMedia(music: Media, artPath: String = "") {


        Log.e("Music", "Scan07")
        mGenre =  music.getMeta(Media.Meta.Genre) ?: ""
        mTitle = music.getMeta(Media.Meta.Title) ?: ""
        mAlbumName = music.getMeta(Media.Meta.Album) ?: ""
        music.getMeta(Media.Meta.Artist)?.let {
            mArtistNames.add(it)
        }
        mYear = music.getMeta(Media.Meta.Date) ?: ""
        mTrack = music.getMeta(Media.Meta.TrackNumber) ?: ""
        mDiskNo = music.getMeta(Media.Meta.DiscNumber) ?: ""
        mUri = music.uri
        mArtPath = artPath
        mSize = music.stats?.readBytes?.toLong() ?: 0
        Log.e("Music", "Scan08")

        mDuration = music.duration ?: 0
        val track = music.getTrack(0) as? Media.AudioTrack
        track?.let {
            mBitrate = track.bitrate
            mCodec = track.codec
            mChannelNumber = track.channels
        }
        Log.e("Music", "Scan09")



    }
}