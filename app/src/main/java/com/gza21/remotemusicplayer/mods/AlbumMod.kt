package com.gza21.remotemusicplayer.mods

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import com.gza21.remotemusicplayer.utils.IndexInterface
import java.lang.ref.WeakReference
import java.text.Collator
import java.util.*
import kotlin.collections.ArrayList

data class AlbumMod(
    var mName: String = "",
    var mArtist: ArrayList<Int> = arrayListOf(),
    var mMusics: ArrayList<Int> = arrayListOf(),
    override var mIndex: Int = -1,
    var mPhotoPath: String? = null
) : Parcelable, IndexInterface<AlbumMod> {

    var mPhotoIndexInCache: WeakReference<Bitmap>? = null

    constructor(source: Parcel) : this(
        source.readString(),
        ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) },
        ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) },
        source.readInt(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(mName)
        writeList(mArtist)
        writeList(mMusics)
        writeInt(mIndex)
        writeString(mPhotoPath)
    }

    override fun compareTo(other: AlbumMod): Int {
        val cl = Collator.getInstance(Locale.JAPANESE)
        return cl.compare(this.mName, other.mName)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<AlbumMod> = object : Parcelable.Creator<AlbumMod> {
            override fun createFromParcel(source: Parcel): AlbumMod = AlbumMod(source)
            override fun newArray(size: Int): Array<AlbumMod?> = arrayOfNulls(size)
        }
    }
}