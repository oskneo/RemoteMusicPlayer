package com.gza21.remotemusicplayer.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.*
import com.gza21.remotemusicplayer.managers.MusicDBManager
import com.gza21.remotemusicplayer.utils.IndexInterface

@Entity(tableName = "artists", indices = arrayOf(Index(value = ["name"], unique = true)))
data class ArtistMod(
    @ColumnInfo(name = "name")
    var mName: String = "",
    @Ignore
    var mAlbums: ArrayList<Int> = arrayListOf(),
    @Ignore
    var mMusics: ArrayList<Int> = arrayListOf(),
    @Ignore
    override var mIndex: Int = -1,
    @PrimaryKey(autoGenerate = true)
    var mId: Int = 0,
    @ForeignKey(entity = ServerMod::class, parentColumns = ["id"], childColumns = ["server_id"])
    @ColumnInfo(name = "server_id")
    var mServerId: Int = 0
) : Parcelable, IndexInterface<ArtistMod> {
    constructor(source: Parcel) : this(
        source.readString(),
        ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) },
        ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) },
        source.readInt()
    )

    constructor(serverId: Int, name: String?) : this() {
        mServerId = serverId
        mName = name ?: ""
    }

    override fun compareTo(other: ArtistMod): Int {
        return MusicDBManager.instance.compare(this.mName, other.mName)
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(mName)
        writeList(mAlbums)
        writeList(mMusics)
        writeInt(mIndex)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ArtistMod> = object : Parcelable.Creator<ArtistMod> {
            override fun createFromParcel(source: Parcel): ArtistMod = ArtistMod(source)
            override fun newArray(size: Int): Array<ArtistMod?> = arrayOfNulls(size)
        }
    }
}