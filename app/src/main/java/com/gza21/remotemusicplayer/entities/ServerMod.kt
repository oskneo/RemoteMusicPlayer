package com.gza21.remotemusicplayer.entities

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "servers")
data class ServerMod(
    @ColumnInfo(name = "name")
    var mName: String? = null,
    @ColumnInfo(name = "address")
    var mAddress: String = "",
    @ColumnInfo(name = "username")
    var mUserName: String? = null,
    @ColumnInfo(name = "password")
    var mPassword: String? = null,
    @Ignore
    var mDomain: String = "",
    @Ignore
    var mIsStored: Boolean = false,
    @Ignore
    var mDbPath: String? = null,
    @Ignore
    var mUri: Uri? = null,
    @Ignore
    var mIsRoot: Boolean = false, // Not to parcel
    @Ignore
    var mType: Int? = null, // Not to parcel
    @PrimaryKey(autoGenerate = true)
    var mId: Int = 0
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        1 == source.readInt(),
        source.readString(),
        source.readParcelable<Uri>(Uri::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(mName)
        writeString(mAddress)
        writeString(mUserName)
        writeString(mPassword)
        writeString(mDomain)
        writeInt((if (mIsStored) 1 else 0))
        writeString(mDbPath)
        writeParcelable(mUri, 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ServerMod> = object : Parcelable.Creator<ServerMod> {
            override fun createFromParcel(source: Parcel): ServerMod = ServerMod(source)
            override fun newArray(size: Int): Array<ServerMod?> = arrayOfNulls(size)
        }
    }
}