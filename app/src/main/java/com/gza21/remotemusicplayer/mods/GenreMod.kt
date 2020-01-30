package com.gza21.remotemusicplayer.mods

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.gza21.remotemusicplayer.managers.MusicDBManager
import com.gza21.remotemusicplayer.utils.IndexInterface

@Entity(tableName = "genres")
data class GenreMod(
    @ColumnInfo(name = "name")
    var mName: String = "",
    @Ignore
    var mMusics: ArrayList<Int> = arrayListOf(),
    @Ignore
    var mAlbums: ArrayList<Int> = arrayListOf(),
    @Ignore
    override var mIndex: Int = -1,
    @PrimaryKey(autoGenerate = true)
    var mId: Int = 1
) : Parcelable, IndexInterface<GenreMod> {
    constructor(source: Parcel) : this(
        source.readString(),
        ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) },
        ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) },
        source.readInt()
    )

    override fun compareTo(other: GenreMod): Int {
        return MusicDBManager.instance.compare(this.mName, other.mName)
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(mName)
        writeList(mMusics)
        writeList(mAlbums)
        writeInt(mIndex)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<GenreMod> = object : Parcelable.Creator<GenreMod> {
            override fun createFromParcel(source: Parcel): GenreMod = GenreMod(source)
            override fun newArray(size: Int): Array<GenreMod?> = arrayOfNulls(size)
        }
    }
}