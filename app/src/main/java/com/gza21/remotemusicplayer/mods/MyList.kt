package com.gza21.remotemusicplayer.mods

import com.gza21.remotemusicplayer.utils.IndexInterface

class MyList<E: IndexInterface<E>>() : ArrayList<E>() {
    var list: ArrayList<E> = arrayListOf()
    var induces: ArrayList<Int> = arrayListOf()
    constructor(newList: ArrayList<E>, newInduces: ArrayList<Int> = arrayListOf()) : this() {
        list = newList
        induces = newInduces
        if (induces.isEmpty()) {
            induces.ensureCapacity(list.size)
        }
    }
    override fun get(index: Int): E {
        return list.get(induces.get(index))
    }

    override fun set(index: Int, element: E): E {
        return list.set(induces.get(index), element)
    }

    fun getInducesList(): ArrayList<Int> {
        return induces
    }

//    fun test() {
//        val ls = MyList<AlbumMod>(arrayListOf<AlbumMod>(), arrayListOf())
//        ls[1] = AlbumMod("")
//
//    }

    fun setMIndex() {
        var i = 0
        for (item in list) {
            item.mIndex = i++
        }
    }

    fun quickSort() {
        val sortedList = list.quickSort()
        var i = 0
        for (item in sortedList) {
            item.mIndex
            induces.set(i, item.mIndex)
            i++
        }
    }


    fun <T : Comparable<T>> List<T>.quickSort(): List<T> =
        if(size < 2) this
        else {
            val pivot = first()
            val (smaller, greater) = drop(1).partition { it <= pivot }
            smaller.quickSort() + pivot + greater.quickSort()
        }


}