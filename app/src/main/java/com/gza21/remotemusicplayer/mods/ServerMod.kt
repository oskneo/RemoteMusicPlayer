package com.gza21.remotemusicplayer.mods

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import org.videolan.libvlc.Media

data class ServerMod(
    var mName: String? = null,
    var mAddress: String = "",
    var mUserName: String? = null,
    var mPassword: String? = null,
    var mDomain: String = "",
    var mIsConnected: Boolean = false,
    var mDbPath: String? = null,
    var mUri: Uri? = null,
    var mIsRoot: Boolean = false, // Not to parcel
    var mType: Int? = null // Not to parcel
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
        writeInt((if (mIsConnected) 1 else 0))
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