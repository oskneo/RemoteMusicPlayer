package com.gza21.remotemusicplayer.entities

import android.os.Parcel
import android.os.Parcelable

data class TestMod(
    var data: HashMap<String, MusicMod>
) : Parcelable {
    constructor(source: Parcel) : this(
        hashMapOf<String, MusicMod>().also {
            source.createTypedArrayList(MusicMod.CREATOR)?.let { list ->
                for (item in list) {
//                    it.put(item.mUri?.toString()!!, item)
                }
            }
        }
    )

    fun test() {
        for (item in data) {
            
        }
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        val list = data.values.toList()
        writeTypedList(list)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<TestMod> = object : Parcelable.Creator<TestMod> {
            override fun createFromParcel(source: Parcel): TestMod = TestMod(source)
            override fun newArray(size: Int): Array<TestMod?> = arrayOfNulls(size)
        }
    }
}