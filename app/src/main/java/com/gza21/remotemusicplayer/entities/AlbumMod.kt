package com.gza21.remotemusicplayer.entities

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import androidx.room.*
import com.gza21.remotemusicplayer.managers.DatabaseManager
import com.gza21.remotemusicplayer.managers.MusicDBManager
import com.gza21.remotemusicplayer.utils.IndexInterface
import java.lang.ref.WeakReference
import kotlin.collections.ArrayList

@Entity(tableName = "albums", indices = arrayOf(Index(value = ["name"], unique = true)))
data class AlbumMod(


    @ColumnInfo(name = "name")
    var mName: String = "",
    @Ignore
    var mArtist: ArrayList<Int> = arrayListOf(),
    @Ignore
    var mMusics: ArrayList<Int> = arrayListOf(),
    @Ignore
    override var mIndex: Int = -1,
    @ColumnInfo(name = "photo_path")
    var mPhotoPath: String? = null,
    @PrimaryKey(autoGenerate = true)
    var mId: Int = 0,
    @ForeignKey(entity = ServerMod::class, parentColumns = ["id"], childColumns = ["server_id"])
    @ColumnInfo(name = "server_id")
    var mServerId: Int = 0,
    @Ignore
    var mMusicList: List<MusicMod> = listOf()

) : Parcelable, IndexInterface<AlbumMod> {

    var mPhotoIndexInCache: WeakReference<Bitmap>? = null

    constructor(source: Parcel) : this(
        source.readString(),
        ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) },
        ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) },
        source.readInt(),
        source.readString()
    )

    constructor(serverId: Int, name: String?) : this() {
        mServerId = serverId
        mName = name ?: ""
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(mName)
        writeList(mArtist)
        writeList(mMusics)
        writeInt(mIndex)
        writeString(mPhotoPath)
    }

    override fun compareTo(other: AlbumMod): Int {
        return MusicDBManager.instance.compare(this.mName, other.mName)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<AlbumMod> = object : Parcelable.Creator<AlbumMod> {
            override fun createFromParcel(source: Parcel): AlbumMod = AlbumMod(source)
            override fun newArray(size: Int): Array<AlbumMod?> = arrayOfNulls(size)
        }
    }

    fun getMusicList(callback: () -> Unit) {
        DatabaseManager.instance.getMusicByAlbum(mId, {
            mMusicList = it
            callback()
        })
    }
}