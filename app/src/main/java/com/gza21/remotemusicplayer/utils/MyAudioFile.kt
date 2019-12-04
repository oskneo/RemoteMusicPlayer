package com.gza21.remotemusicplayer.utils

import com.google.common.base.Optional
import com.google.common.base.Supplier
import com.google.common.io.Files
import ealvatag.audio.*
import ealvatag.audio.exceptions.CannotWriteException
import ealvatag.tag.Tag
import ealvatag.tag.TagFieldContainer
import ealvatag.tag.TagOptionSingleton
import ealvatag.tag.id3.AbstractID3v2Tag
import ealvatag.tag.id3.ID3v22Tag
import ealvatag.tag.id3.ID3v23Tag
import ealvatag.tag.id3.ID3v24Tag
import ealvatag.tag.reference.ID3V2Version
import ealvatag.utils.Check
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.RandomAccessFile
import java.nio.channels.FileChannel

open class AudioFileImpl : AudioFile {
    protected var mFile: File
    protected var mAudioHeader: AudioHeader? = null
    var tagFieldContainer: TagFieldContainer? = null
        protected set
    var ext // we parsed it once to find the reader, so let's store it and not keep parsing
            : String
        protected set

    /**
     * These constructors are used by the different readers, users should not use them.
     *
     *
     * Create the AudioFile representing file f, the encoding audio headers and containing the tag
     *
     * @param file        The file of the audio file
     * @param extension   the file extension (was used to selected the Reader, so we have already parsed it once)
     * @param audioHeader the encoding audioHeaders over this file
     * @param tag         the tag contained in this file or null if no tag exists
     */
    constructor(
        file: File,
        extension: String,
        audioHeader: AudioHeader,
        tag: TagFieldContainer?
    ) {
        Check.checkArgNotNull(file)
        Check.checkArgNotNullOrEmpty(extension)
        Check.checkArgNotNull(audioHeader)
        this.mFile = file
        ext = extension
        this.mAudioHeader = audioHeader
        tagFieldContainer = tag
    }

    protected constructor(file: File, extension: String) {
        Check.checkArgNotNull(file)
        Check.checkArgNotNullOrEmpty(extension)
        this.mFile = file
        ext = extension
    }

    override fun readOnly(): Boolean {
        return tagFieldContainer!!.isReadOnly
    }

    @Throws(CannotWriteException::class)
    override fun save() {
        checkReadOnly()
        AudioFileIO.instance().writeFile(this)
    }

    @Throws(CannotWriteException::class)
    private fun checkReadOnly() {
        if (tagFieldContainer != null && tagFieldContainer!!.isReadOnly) {
            throw CannotWriteException("Opened read only")
        }
    }

    @Throws(
        IllegalArgumentException::class,
        CannotWriteException::class
    )
    override fun saveAs(fullPathWithoutExtension: String) {
        checkReadOnly()
        Check.checkArgNotNullOrEmpty(
            fullPathWithoutExtension,
            Check.CANNOT_BE_NULL_OR_EMPTY,
            "fullPathWithoutExtension"
        )
        AudioFileIO.instance().writeFileAs(this, fullPathWithoutExtension)
    }

    @Throws(CannotWriteException::class)
    override fun deleteFileTag() {
        checkReadOnly()
        AudioFileIO.instance().deleteTag(this)
    }

    override fun getFile(): File {
        return mFile
    }

    fun setFile(file: File) {
        this.mFile = file
    }

    override fun getAudioHeader(): AudioHeader {
        return mAudioHeader!!
    }

    override fun getTag(): Optional<Tag> {
        return Optional.fromNullable(tagFieldContainer as Tag?)
    }

    @Throws(UnsupportedFileType::class)
    override fun setNewDefaultTag(): Tag {
        return setTag(makeDefaultTag())!!
    }

    protected open fun setTag(tag: Tag?): Tag? {
        tagFieldContainer = tag as TagFieldContainer?
        return tagFieldContainer
    }

    @Throws(UnsupportedFileType::class)
    protected open fun makeDefaultTag(): Tag? {
        return SupportedFileFormat.fromExtension(Files.getFileExtension(mFile.name))
            .makeDefaultTag()
    }

    @Throws(UnsupportedFileType::class, CannotWriteException::class)
    override fun getTagOrSetNewDefault(): Tag {
        return tag.or(makeTagSupplier())
    }

    @Throws(CannotWriteException::class)
    private fun makeTagSupplier(): Supplier<Tag> {
        return Supplier<Tag?> { setTag(makeDefaultTag()) }
    }

    @Throws(CannotWriteException::class)
    override fun getConvertedTagOrSetNewDefault(): Tag { /* TODO Currently only works for Dsf We need additional check here for Wav and Aif because they wrap the ID3
        tag so never return
         * null for getTag() and the wrapper stores the location of the existing tag, would that be broken if tag set
          * to something else
         * // TODO: 1/7/17 this comment may be outdated
         */
        val tag = tagOrSetNewDefault
        return if (tag is AbstractID3v2Tag) {
            setTag(
                convertID3Tag(
                    tag,
                    TagOptionSingleton.getInstance().iD3V2Version
                )
            )!!
        } else {
            setTag(tag)!!
        }
    }

    /**
     * If using ID3 format convert tag from current version to another as specified by id3V2Version,
     *
     * @return the converted tag or the original if no conversion necessary
     */
    protected fun convertID3Tag(
        tag: AbstractID3v2Tag?,
        id3V2Version: ID3V2Version?
    ): AbstractID3v2Tag? {
        if (tag is ID3v24Tag) {
            when (id3V2Version) {
                ID3V2Version.ID3_V22 -> return ID3v22Tag(tag)
                ID3V2Version.ID3_V23 -> return ID3v23Tag(tag)
                ID3V2Version.ID3_V24 -> return tag
            }
        } else if (tag is ID3v23Tag) {
            when (id3V2Version) {
                ID3V2Version.ID3_V22 -> return ID3v22Tag(tag)
                ID3V2Version.ID3_V23 -> return tag
                ID3V2Version.ID3_V24 -> return ID3v24Tag(tag)
            }
        } else if (tag is ID3v22Tag) {
            when (id3V2Version) {
                ID3V2Version.ID3_V22 -> return tag
                ID3V2Version.ID3_V23 -> return ID3v23Tag(tag)
                ID3V2Version.ID3_V24 -> return ID3v24Tag(tag)
            }
        }
        return null
    }

    @Throws(FileNotFoundException::class)
    protected fun getReadFileChannel(file: File): FileChannel {
        val channel =
            RandomAccessFile(file, "r").channel
        return try {
            if (channel.size() == 0L) {
                throw FileNotFoundException("Not found or 0 size " + file.path)
            }
            channel
        } catch (e: IOException) {
            throw FileNotFoundException(file.path + " " + e.message)
        }
    }

    /**
     * Optional debugging method. Must override to do anything interesting.
     *
     * @return Empty string.
     */
    open fun displayStructureAsXML(): String? {
        return ""
    }

    /**
     * Optional debugging method. Must override to do anything interesting.
     */
    open fun displayStructureAsPlainText(): String? {
        return ""
    }

    override fun toString(): String {
        val sb = StringBuilder("AudioFileImpl{")
        sb.append("file=").append(mFile)
        sb.append(", audioHeader=").append(mAudioHeader)
        sb.append(", tag=").append(tagFieldContainer)
        sb.append(", extension='").append(ext).append('\'')
        sb.append('}')
        return sb.toString()
    }
}