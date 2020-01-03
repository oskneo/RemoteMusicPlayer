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
    val mMusicsHashMap: HashMap<String, MusicMod> = hashMapOf()

    init {
        for (item in mMusics) {
            mMusicsHashMap.put(item.mUri?.toString() ?: "", item)
        }
    }

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

        val albumName = music.mAlbumName ?: ""
        var album: AlbumMod? = null
        for (ab in mAlbums) {
            if (ab.mName == albumName) {
                album = ab
                break
            }
        }
        if (album == null) {
            album = AlbumMod(albumName)
            album.mIndex = mAlbums.size
            mAlbums.add(album)
        }
        album.mMusics.add(mMusics.size)
        music.mAlbumIndex = album.mIndex


        val genreName = music.mGenre
        var genre: GenreMod? = null
        for (gr in mGenres) {
            if (gr.mName == genreName) {
                genre = gr
                break
            }
        }
        if (genre == null) {
            genre = GenreMod(genreName)
            genre.mIndex = mGenres.size
            mGenres.add(genre)
        }
        genre.mMusics.add(mMusics.size)
        music.mAlbumIndex = genre.mIndex



        for (artistName in music.mArtistNames) {
            var artist: ArtistMod? = null
            for (at in mArtists) {
                if (at.mName == artistName) {
                    artist = at
                    break
                }
            }
            if (artist == null) {
                artist = ArtistMod(artistName)
                artist.mIndex = mArtists.size
                mArtists.add(artist)
            }

            artist.mMusics.add(mMusics.size)
            artist.mAlbums.find {
                mAlbums.get(it).mName == albumName
            } ?: run {
                artist.mAlbums.add(album.mIndex)
            }
            album.mArtist.find {
                it == artist.mIndex
            } ?: run {
                album.mArtist.add(artist.mIndex)
            }
            music.mArtistInduces.add(artist.mIndex)
        }
        music.mIndex = mMusics.size
        mMusics.add(music)
    }
}