package com.gza21.remotemusicplayer.mods

class MyList<E>() : ArrayList<E>() {
    var list: ArrayList<E> = arrayListOf()
    var induces: ArrayList<Int> = arrayListOf()
    constructor(newList: ArrayList<E>, newInduces: ArrayList<Int>) : this() {
        list = newList
        induces = newInduces
    }
    override fun get(index: Int): E {
        return list.get(induces.get(index))
    }

    override fun set(index: Int, element: E): E {
        return list.set(induces.get(index), element)
    }

    fun test() {
        val ls = MyList(arrayListOf<MusicMod>(), arrayListOf())
        ls[1] = MusicMod("")

    }

    fun <T : Comparable<T>> List<T>.quickSort(): List<T> =
        if(size < 2) this
        else {
            val pivot = first()
            val (smaller, greater) = drop(1).partition { it <= pivot }
            smaller.quickSort() + pivot + greater.quickSort()
        }


}