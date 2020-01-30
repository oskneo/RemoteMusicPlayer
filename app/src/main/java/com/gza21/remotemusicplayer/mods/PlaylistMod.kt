package com.gza21.remotemusicplayer.mods

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.gza21.remotemusicplayer.managers.MusicDBManager
import com.gza21.remotemusicplayer.utils.IndexInterface

@Entity(tableName = "playlists")
data class PlaylistMod(
    @ColumnInfo(name = "name")
    var mName: String = "",
    @Ignore
    var mPhoto: Bitmap? = null,
    @Ignore
    var mMusics: ArrayList<Int> = arrayListOf(),
    @Ignore
    var mAlbums: ArrayList<Int> = arrayListOf(),
    @Ignore
    override var mIndex: Int = -1,
    @PrimaryKey(autoGenerate = true)
    var mId: Int = 0
) : Parcelable, IndexInterface<PlaylistMod> {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readParcelable<Bitmap>(Bitmap::class.java.classLoader),
        ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) },
        ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) },
        source.readInt()
    )

    override fun compareTo(other: PlaylistMod): Int {
        return MusicDBManager.instance.compare(this.mName, other.mName)
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(mName)
        writeParcelable(mPhoto, 0)
        writeList(mMusics)
        writeList(mAlbums)
        writeInt(mIndex)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<PlaylistMod> = object : Parcelable.Creator<PlaylistMod> {
            override fun createFromParcel(source: Parcel): PlaylistMod = PlaylistMod(source)
            override fun newArray(size: Int): Array<PlaylistMod?> = arrayOfNulls(size)
        }
    }
}