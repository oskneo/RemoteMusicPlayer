package com.gza21.remotemusicplayer.mods

import android.os.Parcel
import android.os.Parcelable

data class ServerMod(
    var mName: String?,
    var mAddress: String?,
    var mUserName: String?,
    var mPassword: String?,
    var mDomain: String = "",
    var mIsConnected: Boolean = false
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        1 == source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(mName)
        writeString(mAddress)
        writeString(mUserName)
        writeString(mPassword)
        writeString(mDomain)
        writeInt((if (mIsConnected) 1 else 0))
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ServerMod> = object : Parcelable.Creator<ServerMod> {
            override fun createFromParcel(source: Parcel): ServerMod = ServerMod(source)
            override fun newArray(size: Int): Array<ServerMod?> = arrayOfNulls(size)
        }
    }
}