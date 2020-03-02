package com.gza21.remotemusicplayer.managers

import android.database.sqlite.SQLiteConstraintException
import com.gza21.remotemusicplayer.dao.*
import com.gza21.remotemusicplayer.entities.*
import com.gza21.remotemusicplayer.utils.AppDatabase

class DatabaseManager {
    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { DatabaseManager() }
    }
    private var mMusicDao: MusicDao? = null
    private var mAlbumDao: AlbumDao? = null
    private var mArtistDao: ArtistDao? = null
    private var mGenreDao: GenreDao? = null
    private var mPlaylistDao: PlaylistDao? = null
    private var mServerDao: ServerDao? = null
    private var mMusicPlaylistDao: MusicPlaylistDao? = null
    private var mAlbumArtistDao: AlbumArtistDao? = null

    private fun initDao() {
        if (mMusicDao == null) {
            mMusicDao = AppDatabase.invoke().musicDao()
        }
        if (mAlbumDao == null) {
            mAlbumDao = AppDatabase.invoke().albumDao()
        }
        if (mArtistDao == null) {
            mArtistDao = AppDatabase.invoke().artistDao()
        }
    }

    fun addMusicToDb(music: MusicMod) {
        initDao()
        mMusicDao?.insertAll(music)
    }

    fun getMusicByAlbum(albumId: Int, callback: (List<MusicMod>) -> Unit) {
        Thread {
            callback(mMusicDao?.loadAllByAlbumId(albumId) ?: listOf())
        }.start()

    }
    fun getAlbum(albumId: Int, callback: (AlbumMod?) -> Unit) {
        Thread {
            val list = mAlbumDao?.loadAllByIds(IntArray(1).also { it[0] = albumId })
            if (list != null && list.isNotEmpty()) {
                callback(list[0])
            }
            callback(null)
        }.start()
    }

    fun updateAlbum(album: AlbumMod): AlbumMod? {
        val id = mAlbumDao?.insert(album)
        if (id == null || id < 0L) {
            val rs = mAlbumDao?.loadByName(album.mName)
            if (rs?.isNotEmpty() == true) {
                return (rs[0])
            }
            return null
        } else {
            album.mId = id.toInt()
            return album
        }
    }

    fun getArtist(artistId: Int, callback: (ArtistMod?) -> Unit) {
        Thread {
            val list = mArtistDao?.loadAllByIds(IntArray(1).also { it[0] = artistId })
            if (list != null && list.isNotEmpty()) {
                callback(list[0])
            }
            callback(null)
        }.start()
    }

    fun updateArtist(artist: ArtistMod): ArtistMod? {
        val id = mArtistDao?.insert(artist)
        if (id == null || id < 0L) {
            val rs = mArtistDao?.loadByName(artist.mName)
            if (rs?.isNotEmpty() == true) {
                return (rs[0])
            }
            return null
        } else {
            artist.mId = id.toInt()
            return artist
        }
    }

    fun getGenre(genreId: Int, callback: (GenreMod?) -> Unit) {
        Thread {
            val list = mGenreDao?.loadAllByIds(IntArray(1).also { it[0] = genreId })
            if (list != null && list.isNotEmpty()) {
                callback(list[0])
            }
            callback(null)
        }.start()
    }

    fun updateGenre(genre: GenreMod): GenreMod? {
        val id = mGenreDao?.insert(genre)
        if (id == null || id < 0L) {
            val rs = mGenreDao?.loadByName(genre.mName)
            if (rs?.isNotEmpty() == true) {
                return (rs[0])
            }
            return null
        } else {
            genre.mId = id.toInt()
            return genre
        }
    }

    fun getPlaylist(playlistId: Int, callback: (PlaylistMod?) -> Unit) {
        Thread {
            val list = mPlaylistDao?.loadAllByIds(IntArray(1).also { it[0] = playlistId })
            if (list != null && list.isNotEmpty()) {
                callback(list[0])
            }
            callback(null)
        }.start()
    }

    fun getServer(serverId: Int, callback: (ServerMod?) -> Unit) {
        Thread {
            val list = mServerDao?.loadAllByIds(IntArray(1).also { it[0] = serverId })
            if (list != null && list.isNotEmpty()) {
                callback(list[0])
            }
            callback(null)
        }.start()
    }

    fun getMusicPlaylist(playlistId: Int, callback: (List<MusicMod>) -> Unit) {
        Thread {
            val musicPlaylistList = mMusicPlaylistDao
                ?.loadAllByPlaylistId(playlistId) ?: listOf()

            if (musicPlaylistList.isNotEmpty()) {
                val list = musicPlaylistList.map {
                    it.musicId
                }.toIntArray()
                val musicList = mMusicDao?.loadAllByIds(list) ?: listOf()
                callback(musicList)
            }
        }.start()
    }

    fun getArtistAlbums(artistId: Int, callback: (List<AlbumMod>) -> Unit) {
        Thread {
            val artistAlbumList = mAlbumArtistDao
                ?.loadAllByArtistId(artistId) ?: listOf()

            if (artistAlbumList.isNotEmpty()) {
                val list = artistAlbumList.map {
                    it.mAlbumId
                }.toIntArray()
                val albumList = mAlbumDao?.loadAllByIds(list) ?: listOf()
                callback(albumList)
            }
        }.start()
    }

    fun updateAlbmuArtist(album: AlbumMod, artist: ArtistMod): AlbumArtist? {
        val albumArtist = AlbumArtist()
        albumArtist.mAlbumId = album.mId
        albumArtist.mArtistId = artist.mId
        return try {
            mAlbumArtistDao?.insert(albumArtist)
            albumArtist
        } catch (e: SQLiteConstraintException) {
            val rs = mAlbumArtistDao?.load(album.mId, artist.mId)
            if (rs?.isNotEmpty() == true) {
                rs[0]
            } else null
        }
    }
}