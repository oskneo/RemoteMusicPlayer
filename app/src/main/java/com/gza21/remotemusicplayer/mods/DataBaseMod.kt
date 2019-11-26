package com.gza21.remotemusicplayer.mods

import android.os.Parcel
import android.os.Parcelable

data class DataBaseMod(
    var mServer: ServerMod?,
    var mMusics: ArrayList<MusicMod>,
    var mAlbums: ArrayList<AlbumMod>,
    var mArtists: ArrayList<ArtistMod>,
    var mPlaylists: ArrayList<PlaylistMod>,
    var mGenres: ArrayList<GenreMod>
) : Parcelable {

    constructor(source: Parcel) : this(
        source.readParcelable<ServerMod>(ServerMod::class.java.classLoader),
        source.createTypedArrayList(MusicMod.CREATOR),
        source.createTypedArrayList(AlbumMod.CREATOR),
        source.createTypedArrayList(ArtistMod.CREATOR),
        source.createTypedArrayList(PlaylistMod.CREATOR),
        source.createTypedArrayList(GenreMod.CREATOR)
    )

    constructor(mServer: ServerMod?) : this(
        mServer,
        arrayListOf(),
        arrayListOf(),
        arrayListOf(),
        arrayListOf(),
        arrayListOf()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeParcelable(mServer, 0)
        writeTypedList(mMusics)
        writeTypedList(mAlbums)
        writeTypedList(mArtists)
        writeTypedList(mPlaylists)
        writeTypedList(mGenres)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<DataBaseMod> = object : Parcelable.Creator<DataBaseMod> {
            override fun createFromParcel(source: Parcel): DataBaseMod = DataBaseMod(source)
            override fun newArray(size: Int): Array<DataBaseMod?> = arrayOfNulls(size)
        }
    }

    fun addMusic(music: MusicMod) {

        val albumName = music.mAlbumName
        val album = mAlbums.find {
            it.mName == albumName
        } ?: run {
            val ab = AlbumMod(albumName)
            mAlbums.add(ab)
            ab
        }
        album.mMusics.add(mMusics.size)

        for (artistName in music.mArtistNames) {
            mArtists.find {
                it.mName == artistName
            }?.let {

                it.mMusics.add(mMusics.size)
                it.mAlbums.find {
                    mAlbums.get(it).mName == albumName
                } ?: run {
                    it.mAlbums.add(mAlbums.indexOf(album))
                }
            } ?: run {
                val artist = ArtistMod(artistName)
                mArtists.add(artist)
                artist.mMusics.add(mMusics.size)
                artist.mAlbums.add(mAlbums.indexOf(album))
            }
        }
        mMusics.add(music)
    }
}