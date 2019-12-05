/*
 * Jaudiotagger Copyright (C)2004,2005
 *
 * This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public  License as published by the Free Software Foundation; either version 2.1 of the License,
 * or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this library; if not,
 * you can getFields a copy from http://www.opensource.org/licenses/lgpl-license.php or write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */
package ealvatag.tag.id3;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import ealvatag.tag.FieldKey;

/**
 * Defines ID3v23 frames and collections that categorise frames within an ID3v23 tag.
 * <p>
 * You can include frames here that are not officially supported as long as they can be used within an
 * ID3v23Tag
 *
 * @author Paul Taylor
 * @version $Id$
 */
public class ID3v23Frames extends ID3Frames {
  /**
   * Define all frames that are valid within ID3v23
   * Frame IDs beginning with T are text frames, and with W are url frames
   */
  public static final String FRAME_ID_V3_ACCOMPANIMENT = "TPE2";
  public static final String FRAME_ID_V3_ALBUM = "TALB";
  public static final String FRAME_ID_V3_ARTIST = "TPE1";
  public static final String FRAME_ID_V3_ATTACHED_PICTURE = "APIC";
  public static final String FRAME_ID_V3_AUDIO_ENCRYPTION = "AENC";
  public static final String FRAME_ID_V3_BPM = "TBPM";
  public static final String FRAME_ID_V3_CHAPTER = ID3v2ChapterFrames.FRAME_ID_CHAPTER;
  public static final String FRAME_ID_V3_CHAPTER_TOC = ID3v2ChapterFrames.FRAME_ID_TABLE_OF_CONTENT;
  public static final String FRAME_ID_V3_COMMENT = "COMM";
  public static final String FRAME_ID_V3_COMMERCIAL_FRAME = "COMR";
  public static final String FRAME_ID_V3_COMPOSER = "TCOM";
  public static final String FRAME_ID_V3_CONDUCTOR = "TPE3";
  public static final String FRAME_ID_V3_CONTENT_GROUP_DESC = "TIT1";
  public static final String FRAME_ID_V3_COPYRIGHTINFO = "TCOP";
  public static final String FRAME_ID_V3_ENCODEDBY = "TENC";
  public static final String FRAME_ID_V3_ENCRYPTION = "ENCR";
  public static final String FRAME_ID_V3_EQUALISATION = "EQUA";
  public static final String FRAME_ID_V3_EVENT_TIMING_CODES = "ETCO";
  public static final String FRAME_ID_V3_FILE_OWNER = "TOWN";
  public static final String FRAME_ID_V3_FILE_TYPE = "TFLT";
  public static final String FRAME_ID_V3_GENERAL_ENCAPS_OBJECT = "GEOB";
  public static final String FRAME_ID_V3_GENRE = "TCON";
  public static final String FRAME_ID_V3_GROUP_ID_REG = "GRID";
  public static final String FRAME_ID_V3_HW_SW_SETTINGS = "TSSE";
  public static final String FRAME_ID_V3_INITIAL_KEY = "TKEY";
  public static final String FRAME_ID_V3_INVOLVED_PEOPLE = "IPLS";
  public static final String FRAME_ID_V3_ISRC = "TSRC";
  public static final String FRAME_ID_V3_ITUNES_GROUPING = "GRP1";
  public static final String FRAME_ID_V3_LANGUAGE = "TLAN";
  public static final String FRAME_ID_V3_LENGTH = "TLEN";
  public static final String FRAME_ID_V3_LINKED_INFO = "LINK";
  public static final String FRAME_ID_V3_LYRICIST = "TEXT";
  public static final String FRAME_ID_V3_MEDIA_TYPE = "TMED";
  public static final String FRAME_ID_V3_MOVEMENT = "MVNM";
  public static final String FRAME_ID_V3_MOVEMENT_NO = "MVIN";
  public static final String FRAME_ID_V3_MPEG_LOCATION_LOOKUP_TABLE = "MLLT";
  public static final String FRAME_ID_V3_MUSIC_CD_ID = "MCDI";
  public static final String FRAME_ID_V3_ORIGARTIST = "TOPE";
  public static final String FRAME_ID_V3_ORIG_FILENAME = "TOFN";
  public static final String FRAME_ID_V3_ORIG_LYRICIST = "TOLY";
  public static final String FRAME_ID_V3_ORIG_TITLE = "TOAL";
  public static final String FRAME_ID_V3_OWNERSHIP = "OWNE";
  public static final String FRAME_ID_V3_PLAYLIST_DELAY = "TDLY";
  public static final String FRAME_ID_V3_PLAY_COUNTER = "PCNT";
  public static final String FRAME_ID_V3_POPULARIMETER = "POPM";
  public static final String FRAME_ID_V3_POSITION_SYNC = "POSS";
  public static final String FRAME_ID_V3_PRIVATE = "PRIV";
  public static final String FRAME_ID_V3_PUBLISHER = "TPUB";
  public static final String FRAME_ID_V3_RADIO_NAME = "TRSN";
  public static final String FRAME_ID_V3_RADIO_OWNER = "TRSO";
  public static final String FRAME_ID_V3_RECOMMENDED_BUFFER_SIZE = "RBUF";
  public static final String FRAME_ID_V3_RELATIVE_VOLUME_ADJUSTMENT = "RVAD";
  public static final String FRAME_ID_V3_REMIXED = "TPE4";
  public static final String FRAME_ID_V3_REVERB = "RVRB";
  public static final String FRAME_ID_V3_SET = "TPOS";
  public static final String FRAME_ID_V3_SYNC_LYRIC = "SYLT";
  public static final String FRAME_ID_V3_SYNC_TEMPO = "SYTC";
  public static final String FRAME_ID_V3_TDAT = "TDAT";
  public static final String FRAME_ID_V3_TERMS_OF_USE = "USER";
  public static final String FRAME_ID_V3_TIME = "TIME";
  public static final String FRAME_ID_V3_TITLE = "TIT2";
  public static final String FRAME_ID_V3_TITLE_REFINEMENT = "TIT3";
  public static final String FRAME_ID_V3_TORY = "TORY";
  public static final String FRAME_ID_V3_TRACK = "TRCK";
  public static final String FRAME_ID_V3_TRDA = "TRDA";
  public static final String FRAME_ID_V3_TSIZ = "TSIZ";
  public static final String FRAME_ID_V3_TYER = "TYER";
  public static final String FRAME_ID_V3_UNIQUE_FILE_ID = "UFID";
  public static final String FRAME_ID_V3_UNSYNC_LYRICS = "USLT";
  public static final String FRAME_ID_V3_URL_ARTIST_WEB = "WOAR";
  public static final String FRAME_ID_V3_URL_COMMERCIAL = "WCOM";
  public static final String FRAME_ID_V3_URL_COPYRIGHT = "WCOP";
  public static final String FRAME_ID_V3_URL_FILE_WEB = "WOAF";
  public static final String FRAME_ID_V3_URL_OFFICIAL_RADIO = "WORS";
  public static final String FRAME_ID_V3_URL_PAYMENT = "WPAY";
  public static final String FRAME_ID_V3_URL_PUBLISHERS = "WPUB";
  public static final String FRAME_ID_V3_URL_SOURCE_WEB = "WOAS";
  public static final String FRAME_ID_V3_USER_DEFINED_INFO = "TXXX";
  public static final String FRAME_ID_V3_USER_DEFINED_URL = "WXXX";

  public static final String FRAME_ID_V3_IS_COMPILATION = "TCMP";
  public static final String FRAME_ID_V3_TITLE_SORT_ORDER_ITUNES = "TSOT";
  public static final String FRAME_ID_V3_ARTIST_SORT_ORDER_ITUNES = "TSOP";
  public static final String FRAME_ID_V3_ALBUM_SORT_ORDER_ITUNES = "TSOA";
  public static final String FRAME_ID_V3_TITLE_SORT_ORDER_MUSICBRAINZ = "XSOT";
  public static final String FRAME_ID_V3_ARTIST_SORT_ORDER_MUSICBRAINZ = "XSOP";
  public static final String FRAME_ID_V3_ALBUM_SORT_ORDER_MUSICBRAINZ = "XSOA";
  public static final String FRAME_ID_V3_ALBUM_ARTIST_SORT_ORDER_ITUNES = "TSO2";
  public static final String FRAME_ID_V3_COMPOSER_SORT_ORDER_ITUNES = "TSOC";
  public static final String FRAME_ID_V3_SET_SUBTITLE = "TSST";

  private static volatile ID3v23Frames instance;

  /**
   * Maps from Generic key to ID3 key
   */
  private volatile ImmutableBiMap<FieldKey, ID3v23FieldKey> tagFieldToId3;
  private volatile ImmutableBiMap<ID3v23FieldKey, FieldKey> id3ToTagField;
  private volatile ImmutableMap<String, String> idToValue;

  public static ID3v23Frames getInstanceOf() {
    if (instance == null) {
      synchronized (ID3v23Frames.class) {
        if (instance == null) {
          instance = new ID3v23Frames();
        }
      }
    }
    return instance;
  }

  private ID3v23Frames() {}

  /**
   * @param genericKey FieldKey to query
   *
   * @return id3 key for generic key
   */
  public ID3v23FieldKey getId3KeyFromGenericKey(FieldKey genericKey) {
    return getTagFieldToId3().get(genericKey);
  }

  /**
   * Get generic key for ID3 field key
   *
   * @param fieldKey {@link ID3v23FieldKey} to query
   *
   * @return {@link FieldKey} analog to the {@link ID3v23FieldKey}
   */
  public FieldKey getGenericKeyFromId3(ID3v23FieldKey fieldKey) {
    return getId3ToTagField().get(fieldKey);
  }

  ImmutableSet<FieldKey> getSupportedFields() {
    return getTagFieldToId3().keySet();
  }

  public boolean containsKey(String key) {
    return getIdToValue().containsKey(key);
  }

  public String getValue(String id) {
    return getIdToValue().get(id);
  }

  private ImmutableBiMap<FieldKey, ID3v23FieldKey> getTagFieldToId3() {
    if (tagFieldToId3 == null) {
      synchronized (this) {
        if (tagFieldToId3 == null) {
          tagFieldToId3 = makeTagFieldToId3();
        }
      }
    }
    return tagFieldToId3;
  }

  private ImmutableBiMap<FieldKey, ID3v23FieldKey> makeTagFieldToId3() {
    final ImmutableBiMap.Builder<FieldKey, ID3v23FieldKey> builder = ImmutableBiMap.builder();
    builder.put(FieldKey.ACOUSTID_FINGERPRINT, ID3v23FieldKey.ACOUSTID_FINGERPRINT)
           .put(FieldKey.ACOUSTID_ID, ID3v23FieldKey.ACOUSTID_ID)
           .put(FieldKey.ALBUM, ID3v23FieldKey.ALBUM)
           .put(FieldKey.ALBUM_ARTIST, ID3v23FieldKey.ALBUM_ARTIST)
           .put(FieldKey.ALBUM_ARTIST_SORT, ID3v23FieldKey.ALBUM_ARTIST_SORT)
           .put(FieldKey.ALBUM_ARTISTS, ID3v23FieldKey.ALBUM_ARTISTS)
           .put(FieldKey.ALBUM_ARTISTS_SORT, ID3v23FieldKey.ALBUM_ARTISTS_SORT)
           .put(FieldKey.ALBUM_SORT, ID3v23FieldKey.ALBUM_SORT)
           .put(FieldKey.AMAZON_ID, ID3v23FieldKey.AMAZON_ID)
           .put(FieldKey.ARRANGER, ID3v23FieldKey.ARRANGER)
           .put(FieldKey.ARRANGER_SORT, ID3v23FieldKey.ARRANGER_SORT)
           .put(FieldKey.ARTIST, ID3v23FieldKey.ARTIST)
           .put(FieldKey.ARTISTS, ID3v23FieldKey.ARTISTS)
           .put(FieldKey.ARTISTS_SORT, ID3v23FieldKey.ARTISTS_SORT)
           .put(FieldKey.ARTIST_SORT, ID3v23FieldKey.ARTIST_SORT)
           .put(FieldKey.BARCODE, ID3v23FieldKey.BARCODE)
           .put(FieldKey.BPM, ID3v23FieldKey.BPM)
           .put(FieldKey.CATALOG_NO, ID3v23FieldKey.CATALOG_NO)
           .put(FieldKey.CHOIR, ID3v23FieldKey.CHOIR)
           .put(FieldKey.CHOIR_SORT, ID3v23FieldKey.CHOIR_SORT)
           .put(FieldKey.CLASSICAL_CATALOG, ID3v23FieldKey.CLASSICAL_CATALOG)
           .put(FieldKey.CLASSICAL_NICKNAME, ID3v23FieldKey.CLASSICAL_NICKNAME)
           .put(FieldKey.COMMENT, ID3v23FieldKey.COMMENT)
           .put(FieldKey.COMPOSER, ID3v23FieldKey.COMPOSER)
           .put(FieldKey.COMPOSER_SORT, ID3v23FieldKey.COMPOSER_SORT)
           .put(FieldKey.CONDUCTOR, ID3v23FieldKey.CONDUCTOR)
           .put(FieldKey.CONDUCTOR_SORT, ID3v23FieldKey.CONDUCTOR_SORT)
           .put(FieldKey.COUNTRY, ID3v23FieldKey.COUNTRY)
           .put(FieldKey.COVER_ART, ID3v23FieldKey.COVER_ART)
           .put(FieldKey.CUSTOM1, ID3v23FieldKey.CUSTOM1)
           .put(FieldKey.CUSTOM2, ID3v23FieldKey.CUSTOM2)
           .put(FieldKey.CUSTOM3, ID3v23FieldKey.CUSTOM3)
           .put(FieldKey.CUSTOM4, ID3v23FieldKey.CUSTOM4)
           .put(FieldKey.CUSTOM5, ID3v23FieldKey.CUSTOM5)
           .put(FieldKey.DISC_NO, ID3v23FieldKey.DISC_NO)
           .put(FieldKey.DISC_SUBTITLE, ID3v23FieldKey.DISC_SUBTITLE)
           .put(FieldKey.DISC_TOTAL, ID3v23FieldKey.DISC_TOTAL)
           .put(FieldKey.DJMIXER, ID3v23FieldKey.DJMIXER)
           .put(FieldKey.MOOD_ELECTRONIC, ID3v23FieldKey.MOOD_ELECTRONIC)
           .put(FieldKey.ENCODER, ID3v23FieldKey.ENCODER)
           .put(FieldKey.ENGINEER, ID3v23FieldKey.ENGINEER)
           .put(FieldKey.ENSEMBLE, ID3v23FieldKey.ENSEMBLE)
           .put(FieldKey.ENSEMBLE_SORT, ID3v23FieldKey.ENSEMBLE_SORT)
           .put(FieldKey.FBPM, ID3v23FieldKey.FBPM)
           .put(FieldKey.GENRE, ID3v23FieldKey.GENRE)
           .put(FieldKey.GROUPING, ID3v23FieldKey.GROUPING)
           .put(FieldKey.MOOD_INSTRUMENTAL, ID3v23FieldKey.MOOD_INSTRUMENTAL)
           .put(FieldKey.INVOLVED_PERSON, ID3v23FieldKey.INVOLVED_PERSON)
           .put(FieldKey.ISRC, ID3v23FieldKey.ISRC)
           .put(FieldKey.IS_CLASSICAL, ID3v23FieldKey.IS_CLASSICAL)
           .put(FieldKey.IS_COMPILATION, ID3v23FieldKey.IS_COMPILATION)
           .put(FieldKey.IS_SOUNDTRACK, ID3v23FieldKey.IS_SOUNDTRACK)
           .put(FieldKey.ITUNES_GROUPING, ID3v23FieldKey.ITUNES_GROUPING)
           .put(FieldKey.KEY, ID3v23FieldKey.KEY)
           .put(FieldKey.LANGUAGE, ID3v23FieldKey.LANGUAGE)
           .put(FieldKey.LYRICIST, ID3v23FieldKey.LYRICIST)
           .put(FieldKey.LYRICS, ID3v23FieldKey.LYRICS)
           .put(FieldKey.MEDIA, ID3v23FieldKey.MEDIA)
           .put(FieldKey.MIXER, ID3v23FieldKey.MIXER)
           .put(FieldKey.MOOD, ID3v23FieldKey.MOOD)
           .put(FieldKey.MOOD_ACOUSTIC, ID3v23FieldKey.MOOD_ACOUSTIC)
           .put(FieldKey.MOOD_AGGRESSIVE, ID3v23FieldKey.MOOD_AGGRESSIVE)
           .put(FieldKey.MOOD_AROUSAL, ID3v23FieldKey.MOOD_AROUSAL)
           .put(FieldKey.MOOD_DANCEABILITY, ID3v23FieldKey.MOOD_DANCEABILITY)
           .put(FieldKey.MOOD_HAPPY, ID3v23FieldKey.MOOD_HAPPY)
           .put(FieldKey.MOOD_PARTY, ID3v23FieldKey.MOOD_PARTY)
           .put(FieldKey.MOOD_RELAXED, ID3v23FieldKey.MOOD_RELAXED)
           .put(FieldKey.MOOD_SAD, ID3v23FieldKey.MOOD_SAD)
           .put(FieldKey.MOOD_VALENCE, ID3v23FieldKey.MOOD_VALENCE)
           .put(FieldKey.MOVEMENT, ID3v23FieldKey.MOVEMENT)
           .put(FieldKey.MOVEMENT_NO, ID3v23FieldKey.MOVEMENT_NO)
           .put(FieldKey.MOVEMENT_TOTAL, ID3v23FieldKey.MOVEMENT_TOTAL)
           .put(FieldKey.MUSICBRAINZ_ARTISTID, ID3v23FieldKey.MUSICBRAINZ_ARTISTID)
           .put(FieldKey.MUSICBRAINZ_DISC_ID, ID3v23FieldKey.MUSICBRAINZ_DISC_ID)
           .put(FieldKey.MUSICBRAINZ_ORIGINAL_RELEASE_ID, ID3v23FieldKey.MUSICBRAINZ_ORIGINAL_RELEASEID)
           .put(FieldKey.MUSICBRAINZ_RELEASEARTISTID, ID3v23FieldKey.MUSICBRAINZ_RELEASEARTISTID)
           .put(FieldKey.MUSICBRAINZ_RELEASEID, ID3v23FieldKey.MUSICBRAINZ_RELEASEID)
           .put(FieldKey.MUSICBRAINZ_RELEASE_COUNTRY, ID3v23FieldKey.MUSICBRAINZ_RELEASE_COUNTRY)
           .put(FieldKey.MUSICBRAINZ_RELEASE_GROUP_ID, ID3v23FieldKey.MUSICBRAINZ_RELEASE_GROUP_ID)
           .put(FieldKey.MUSICBRAINZ_RELEASE_STATUS, ID3v23FieldKey.MUSICBRAINZ_RELEASE_STATUS)
           .put(FieldKey.MUSICBRAINZ_RELEASE_TRACK_ID, ID3v23FieldKey.MUSICBRAINZ_RELEASE_TRACK_ID)
           .put(FieldKey.MUSICBRAINZ_RELEASE_TYPE, ID3v23FieldKey.MUSICBRAINZ_RELEASE_TYPE)
           .put(FieldKey.MUSICBRAINZ_TRACK_ID, ID3v23FieldKey.MUSICBRAINZ_TRACK_ID)
           .put(FieldKey.MUSICBRAINZ_WORK, ID3v23FieldKey.MUSICBRAINZ_WORK)
           .put(FieldKey.MUSICBRAINZ_WORK_ID, ID3v23FieldKey.MUSICBRAINZ_WORK_ID)
           .put(FieldKey.MUSICBRAINZ_WORK_COMPOSITION_ID, ID3v23FieldKey.MUSICBRAINZ_WORK_COMPOSITION_ID)
           .put(FieldKey.MUSICBRAINZ_WORK_PART_LEVEL1_ID, ID3v23FieldKey.MUSICBRAINZ_WORK_PART_LEVEL1_ID)
           .put(FieldKey.MUSICBRAINZ_WORK_PART_LEVEL2_ID, ID3v23FieldKey.MUSICBRAINZ_WORK_PART_LEVEL2_ID)
           .put(FieldKey.MUSICBRAINZ_WORK_PART_LEVEL3_ID, ID3v23FieldKey.MUSICBRAINZ_WORK_PART_LEVEL3_ID)
           .put(FieldKey.MUSICBRAINZ_WORK_PART_LEVEL4_ID, ID3v23FieldKey.MUSICBRAINZ_WORK_PART_LEVEL4_ID)
           .put(FieldKey.MUSICBRAINZ_WORK_PART_LEVEL5_ID, ID3v23FieldKey.MUSICBRAINZ_WORK_PART_LEVEL5_ID)
           .put(FieldKey.MUSICBRAINZ_WORK_PART_LEVEL6_ID, ID3v23FieldKey.MUSICBRAINZ_WORK_PART_LEVEL6_ID)
           .put(FieldKey.MUSICIP_ID, ID3v23FieldKey.MUSICIP_ID)
           .put(FieldKey.OCCASION, ID3v23FieldKey.OCCASION)
           .put(FieldKey.OPUS, ID3v23FieldKey.OPUS)
           .put(FieldKey.ORCHESTRA, ID3v23FieldKey.ORCHESTRA)
           .put(FieldKey.ORCHESTRA_SORT, ID3v23FieldKey.ORCHESTRA_SORT)
           .put(FieldKey.ORIGINAL_ALBUM, ID3v23FieldKey.ORIGINAL_ALBUM)
           .put(FieldKey.ORIGINAL_ARTIST, ID3v23FieldKey.ORIGINAL_ARTIST)
           .put(FieldKey.ORIGINAL_LYRICIST, ID3v23FieldKey.ORIGINAL_LYRICIST)
           .put(FieldKey.ORIGINAL_YEAR, ID3v23FieldKey.ORIGINAL_YEAR)
           .put(FieldKey.PART, ID3v23FieldKey.PART)
           .put(FieldKey.PART_NUMBER, ID3v23FieldKey.PART_NUMBER)
           .put(FieldKey.PART_TYPE, ID3v23FieldKey.PART_TYPE)
           .put(FieldKey.PERFORMER, ID3v23FieldKey.PERFORMER)
           .put(FieldKey.PERFORMER_NAME, ID3v23FieldKey.PERFORMER_NAME)
           .put(FieldKey.PERFORMER_NAME_SORT, ID3v23FieldKey.PERFORMER_NAME_SORT)
           .put(FieldKey.PERIOD, ID3v23FieldKey.PERIOD)
           .put(FieldKey.PRODUCER, ID3v23FieldKey.PRODUCER)
           .put(FieldKey.QUALITY, ID3v23FieldKey.QUALITY)
           .put(FieldKey.RANKING, ID3v23FieldKey.RANKING)
           .put(FieldKey.RATING, ID3v23FieldKey.RATING)
           .put(FieldKey.RECORD_LABEL, ID3v23FieldKey.RECORD_LABEL)
           .put(FieldKey.REMIXER, ID3v23FieldKey.REMIXER)
           .put(FieldKey.SCRIPT, ID3v23FieldKey.SCRIPT)
           .put(FieldKey.SINGLE_DISC_TRACK_NO, ID3v23FieldKey.SINGLE_DISC_TRACK_NO)
           .put(FieldKey.SUBTITLE, ID3v23FieldKey.SUBTITLE)
           .put(FieldKey.TAGS, ID3v23FieldKey.TAGS)
           .put(FieldKey.TEMPO, ID3v23FieldKey.TEMPO)
           .put(FieldKey.TIMBRE, ID3v23FieldKey.TIMBRE)
           .put(FieldKey.TITLE, ID3v23FieldKey.TITLE)
           .put(FieldKey.TITLE_MOVEMENT, ID3v23FieldKey.TITLE_MOVEMENT)
           .put(FieldKey.TITLE_SORT, ID3v23FieldKey.TITLE_SORT)
           .put(FieldKey.TONALITY, ID3v23FieldKey.TONALITY)
           .put(FieldKey.TRACK, ID3v23FieldKey.TRACK)
           .put(FieldKey.TRACK_TOTAL, ID3v23FieldKey.TRACK_TOTAL)
           .put(FieldKey.URL_DISCOGS_ARTIST_SITE, ID3v23FieldKey.URL_DISCOGS_ARTIST_SITE)
           .put(FieldKey.URL_DISCOGS_RELEASE_SITE, ID3v23FieldKey.URL_DISCOGS_RELEASE_SITE)
           .put(FieldKey.URL_LYRICS_SITE, ID3v23FieldKey.URL_LYRICS_SITE)
           .put(FieldKey.URL_OFFICIAL_ARTIST_SITE, ID3v23FieldKey.URL_OFFICIAL_ARTIST_SITE)
           .put(FieldKey.URL_OFFICIAL_RELEASE_SITE, ID3v23FieldKey.URL_OFFICIAL_RELEASE_SITE)
           .put(FieldKey.URL_WIKIPEDIA_ARTIST_SITE, ID3v23FieldKey.URL_WIKIPEDIA_ARTIST_SITE)
           .put(FieldKey.URL_WIKIPEDIA_RELEASE_SITE, ID3v23FieldKey.URL_WIKIPEDIA_RELEASE_SITE)
           .put(FieldKey.WORK, ID3v23FieldKey.WORK)
           .put(FieldKey.MUSICBRAINZ_WORK_COMPOSITION, ID3v23FieldKey.MUSICBRAINZ_WORK_COMPOSITION)
           .put(FieldKey.MUSICBRAINZ_WORK_PART_LEVEL1, ID3v23FieldKey.WORK_PART_LEVEL1)
           .put(FieldKey.MUSICBRAINZ_WORK_PART_LEVEL1_TYPE, ID3v23FieldKey.WORK_PART_LEVEL1_TYPE)
           .put(FieldKey.MUSICBRAINZ_WORK_PART_LEVEL2, ID3v23FieldKey.WORK_PART_LEVEL2)
           .put(FieldKey.MUSICBRAINZ_WORK_PART_LEVEL2_TYPE, ID3v23FieldKey.WORK_PART_LEVEL2_TYPE)
           .put(FieldKey.MUSICBRAINZ_WORK_PART_LEVEL3, ID3v23FieldKey.WORK_PART_LEVEL3)
           .put(FieldKey.MUSICBRAINZ_WORK_PART_LEVEL3_TYPE, ID3v23FieldKey.WORK_PART_LEVEL3_TYPE)
           .put(FieldKey.MUSICBRAINZ_WORK_PART_LEVEL4, ID3v23FieldKey.WORK_PART_LEVEL4)
           .put(FieldKey.MUSICBRAINZ_WORK_PART_LEVEL4_TYPE, ID3v23FieldKey.WORK_PART_LEVEL4_TYPE)
           .put(FieldKey.MUSICBRAINZ_WORK_PART_LEVEL5, ID3v23FieldKey.WORK_PART_LEVEL5)
           .put(FieldKey.MUSICBRAINZ_WORK_PART_LEVEL5_TYPE, ID3v23FieldKey.WORK_PART_LEVEL5_TYPE)
           .put(FieldKey.MUSICBRAINZ_WORK_PART_LEVEL6, ID3v23FieldKey.WORK_PART_LEVEL6)
           .put(FieldKey.MUSICBRAINZ_WORK_PART_LEVEL6_TYPE, ID3v23FieldKey.WORK_PART_LEVEL6_TYPE)
           .put(FieldKey.WORK_TYPE, ID3v23FieldKey.WORK_TYPE)
           .put(FieldKey.YEAR, ID3v23FieldKey.YEAR);
    return builder.build();
  }

  /**
   * Maps from ID3 key to Generic key
   */
  private ImmutableBiMap<ID3v23FieldKey, FieldKey> getId3ToTagField() {
    if (id3ToTagField == null) {
      synchronized (this) {
        if (id3ToTagField == null) {
          id3ToTagField = getTagFieldToId3().inverse();
        }
      }
    }
    return id3ToTagField;
  }

  private ImmutableMap<String, String> getIdToValue() {
    if (idToValue == null) {
      synchronized (this) {
        if (idToValue == null) {
          idToValue = makeIdToValue();
        }
      }
    }
    return idToValue;
  }

  private ImmutableMap<String, String> makeIdToValue() {
    // Map frameid to a name
    ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
    builder.put(FRAME_ID_V3_ACCOMPANIMENT, "Text: Band/Orchestra/Accompaniment")
           .put(FRAME_ID_V3_ALBUM, "Text: Album/Movie/Show title")
           .put(FRAME_ID_V3_ARTIST, "Text: Lead artist(s)/Lead performer(s)/Soloist(s)/Performing group")
           .put(FRAME_ID_V3_ATTACHED_PICTURE, "Attached picture")
           .put(FRAME_ID_V3_AUDIO_ENCRYPTION, "Audio encryption")
           .put(FRAME_ID_V3_BPM, "Text: BPM (Beats Per Minute)")
           .put(FRAME_ID_V3_CHAPTER, "Chapter")
           .put(FRAME_ID_V3_CHAPTER_TOC, "Chapter TOC")
           .put(FRAME_ID_V3_COMMENT, "Comments")
           .put(FRAME_ID_V3_COMMERCIAL_FRAME, "")
           .put(FRAME_ID_V3_COMPOSER, "Text: Composer")
           .put(FRAME_ID_V3_CONDUCTOR, "Text: Conductor/Performer refinement")
           .put(FRAME_ID_V3_CONTENT_GROUP_DESC, "Text: Content group description")
           .put(FRAME_ID_V3_COPYRIGHTINFO, "Text: Copyright message")
           .put(FRAME_ID_V3_ENCODEDBY, "Text: Encoded by")
           .put(FRAME_ID_V3_ENCRYPTION, "Encryption method registration")
           .put(FRAME_ID_V3_EQUALISATION, "Equalization")
           .put(FRAME_ID_V3_EVENT_TIMING_CODES, "Event timing codes")
           .put(FRAME_ID_V3_FILE_OWNER, "")
           .put(FRAME_ID_V3_FILE_TYPE, "Text: File type")
           .put(FRAME_ID_V3_GENERAL_ENCAPS_OBJECT, "General encapsulated datatype")
           .put(FRAME_ID_V3_GENRE, "Text: Content type")
           .put(FRAME_ID_V3_GROUP_ID_REG, "")
           .put(FRAME_ID_V3_HW_SW_SETTINGS, "Text: Software/hardware and settings used for encoding")
           .put(FRAME_ID_V3_INITIAL_KEY, "Text: Initial key")
           .put(FRAME_ID_V3_INVOLVED_PEOPLE, "Involved people list")
           .put(FRAME_ID_V3_ISRC, "Text: ISRC (International Standard Recording Code)")
           .put(FRAME_ID_V3_ITUNES_GROUPING, "Text: iTunes Grouping")
           .put(FRAME_ID_V3_LANGUAGE, "Text: Language(s)")
           .put(FRAME_ID_V3_LENGTH, "Text: Length")
           .put(FRAME_ID_V3_LINKED_INFO, "Linked information")
           .put(FRAME_ID_V3_LYRICIST, "Text: Lyricist/text writer")
           .put(FRAME_ID_V3_MEDIA_TYPE, "Text: Media type")
           .put(FRAME_ID_V3_MOVEMENT, "Text: Movement")
           .put(FRAME_ID_V3_MOVEMENT_NO, "Text: Movement No")
           .put(FRAME_ID_V3_MPEG_LOCATION_LOOKUP_TABLE, "MPEG location lookup table")
           .put(FRAME_ID_V3_MUSIC_CD_ID, "Music CD Identifier")
           .put(FRAME_ID_V3_ORIGARTIST, "Text: Original artist(s)/performer(s)")
           .put(FRAME_ID_V3_ORIG_FILENAME, "Text: Original filename")
           .put(FRAME_ID_V3_ORIG_LYRICIST, "Text: Original Lyricist(s)/text writer(s)")
           .put(FRAME_ID_V3_ORIG_TITLE, "Text: Original album/Movie/Show title")
           .put(FRAME_ID_V3_OWNERSHIP, "")
           .put(FRAME_ID_V3_PLAYLIST_DELAY, "Text: Playlist delay")
           .put(FRAME_ID_V3_PLAY_COUNTER, "Play counter")
           .put(FRAME_ID_V3_POPULARIMETER, "Popularimeter")
           .put(FRAME_ID_V3_POSITION_SYNC, "Position Sync")
           .put(FRAME_ID_V3_PRIVATE, "Private frame")
           .put(FRAME_ID_V3_PUBLISHER, "Text: Publisher")
           .put(FRAME_ID_V3_RADIO_NAME, "")
           .put(FRAME_ID_V3_RADIO_OWNER, "")
           .put(FRAME_ID_V3_RECOMMENDED_BUFFER_SIZE, "Recommended buffer size")
           .put(FRAME_ID_V3_RELATIVE_VOLUME_ADJUSTMENT, "Relative volume adjustment")
           .put(FRAME_ID_V3_REMIXED, "Text: Interpreted, remixed, or otherwise modified by")
           .put(FRAME_ID_V3_REVERB, "Reverb")
           .put(FRAME_ID_V3_SET, "Text: Part of a setField")
           .put(FRAME_ID_V3_SET_SUBTITLE, "Text: SubTitle")
           .put(FRAME_ID_V3_SYNC_LYRIC, "Synchronized lyric/text")
           .put(FRAME_ID_V3_SYNC_TEMPO, "Synced tempo codes")
           .put(FRAME_ID_V3_TDAT, "Text: Date")
           .put(FRAME_ID_V3_TERMS_OF_USE, "")
           .put(FRAME_ID_V3_TIME, "Text: Time")
           .put(FRAME_ID_V3_TITLE, "Text: Title/Songname/Content description")
           .put(FRAME_ID_V3_TITLE_REFINEMENT, "Text: Subtitle/Description refinement")
           .put(FRAME_ID_V3_TORY, "Text: Original release year")
           .put(FRAME_ID_V3_TRACK, "Text: Track number/Position in setField")
           .put(FRAME_ID_V3_TRDA, "Text: Recording dates")
           .put(FRAME_ID_V3_TSIZ, "Text: Size")
           .put(FRAME_ID_V3_TYER, "Text: Year")
           .put(FRAME_ID_V3_UNIQUE_FILE_ID, "Unique file identifier")
           .put(FRAME_ID_V3_UNSYNC_LYRICS, "Unsychronized lyric/text transcription")
           .put(FRAME_ID_V3_URL_ARTIST_WEB, "URL: Official artist/performer webpage")
           .put(FRAME_ID_V3_URL_COMMERCIAL, "URL: Commercial information")
           .put(FRAME_ID_V3_URL_COPYRIGHT, "URL: Copyright/Legal information")
           .put(FRAME_ID_V3_URL_FILE_WEB, "URL: Official audio file webpage")
           .put(FRAME_ID_V3_URL_OFFICIAL_RADIO, "Official Radio")
           .put(FRAME_ID_V3_URL_PAYMENT, "URL: Payment")
           .put(FRAME_ID_V3_URL_PUBLISHERS, "URL: Publishers official webpage")
           .put(FRAME_ID_V3_URL_SOURCE_WEB, "URL: Official audio source webpage")
           .put(FRAME_ID_V3_USER_DEFINED_INFO, "User defined text information frame")
           .put(FRAME_ID_V3_USER_DEFINED_URL, "User defined URL link frame")
           .put(FRAME_ID_V3_IS_COMPILATION, "Is Compilation")
           .put(FRAME_ID_V3_TITLE_SORT_ORDER_ITUNES, "Text: title sort order")
           .put(FRAME_ID_V3_ARTIST_SORT_ORDER_ITUNES, "Text: artist sort order")
           .put(FRAME_ID_V3_ALBUM_SORT_ORDER_ITUNES, "Text: album sort order")
           .put(FRAME_ID_V3_TITLE_SORT_ORDER_MUSICBRAINZ, "Text: title sort order")
           .put(FRAME_ID_V3_ARTIST_SORT_ORDER_MUSICBRAINZ, "Text: artist sort order")
           .put(FRAME_ID_V3_ALBUM_SORT_ORDER_MUSICBRAINZ, "Text: album sort order")
           .put(FRAME_ID_V3_ALBUM_ARTIST_SORT_ORDER_ITUNES, "Text:Album Artist Sort Order Frame")
           .put(FRAME_ID_V3_COMPOSER_SORT_ORDER_ITUNES, "Text:Composer Sort Order Frame");
    return builder.build();
  }

  @Override protected ImmutableSet<String> makeDiscardIfFileAlteredFrames() {
    return ImmutableSet.of(FRAME_ID_V3_EVENT_TIMING_CODES,
                           FRAME_ID_V3_EQUALISATION,
                           FRAME_ID_V3_MPEG_LOCATION_LOOKUP_TABLE,
                           FRAME_ID_V3_POSITION_SYNC,
                           FRAME_ID_V3_SYNC_LYRIC,
                           FRAME_ID_V3_SYNC_TEMPO,
                           FRAME_ID_V3_RELATIVE_VOLUME_ADJUSTMENT,
                           FRAME_ID_V3_EVENT_TIMING_CODES,
                           FRAME_ID_V3_ENCODEDBY,
                           FRAME_ID_V3_LENGTH,
                           FRAME_ID_V3_TSIZ);
  }

  @Override protected ImmutableSet<String> makeMultipleFrames() {
    return ImmutableSet.of(FRAME_ID_V3_USER_DEFINED_INFO,
                           FRAME_ID_V3_USER_DEFINED_URL,
                           FRAME_ID_V3_ATTACHED_PICTURE,
                           FRAME_ID_V3_PRIVATE,
                           FRAME_ID_V3_COMMENT,
                           FRAME_ID_V3_UNIQUE_FILE_ID,
                           FRAME_ID_V3_UNSYNC_LYRICS,
                           FRAME_ID_V3_POPULARIMETER,
                           FRAME_ID_V3_GENERAL_ENCAPS_OBJECT,
                           FRAME_ID_V3_URL_ARTIST_WEB);
  }

  @Override protected ImmutableSet<String> makeSupportedFrames() {
    // The defined v23 frames,
    return ImmutableSet.of(FRAME_ID_V3_ACCOMPANIMENT,
                           FRAME_ID_V3_ALBUM,
                           FRAME_ID_V3_ARTIST,
                           FRAME_ID_V3_ATTACHED_PICTURE,
                           FRAME_ID_V3_AUDIO_ENCRYPTION,
                           FRAME_ID_V3_BPM,
                           FRAME_ID_V3_CHAPTER,
                           FRAME_ID_V3_CHAPTER_TOC,
                           FRAME_ID_V3_COMMENT,
                           FRAME_ID_V3_COMMERCIAL_FRAME,
                           FRAME_ID_V3_COMPOSER,
                           FRAME_ID_V3_CONDUCTOR,
                           FRAME_ID_V3_CONTENT_GROUP_DESC,
                           FRAME_ID_V3_COPYRIGHTINFO,
                           FRAME_ID_V3_ENCODEDBY,
                           FRAME_ID_V3_ENCRYPTION,
                           FRAME_ID_V3_EQUALISATION,
                           FRAME_ID_V3_EVENT_TIMING_CODES,
                           FRAME_ID_V3_FILE_OWNER,
                           FRAME_ID_V3_FILE_TYPE,
                           FRAME_ID_V3_GENERAL_ENCAPS_OBJECT,
                           FRAME_ID_V3_GENRE,
                           FRAME_ID_V3_GROUP_ID_REG,
                           FRAME_ID_V3_HW_SW_SETTINGS,
                           FRAME_ID_V3_INITIAL_KEY,
                           FRAME_ID_V3_INVOLVED_PEOPLE,
                           FRAME_ID_V3_ISRC,
                           FRAME_ID_V3_ITUNES_GROUPING,
                           FRAME_ID_V3_LANGUAGE,
                           FRAME_ID_V3_LENGTH,
                           FRAME_ID_V3_LINKED_INFO,
                           FRAME_ID_V3_LYRICIST,
                           FRAME_ID_V3_MEDIA_TYPE,
                           FRAME_ID_V3_MPEG_LOCATION_LOOKUP_TABLE,
                           FRAME_ID_V3_MOVEMENT,
                           FRAME_ID_V3_MOVEMENT_NO,
                           FRAME_ID_V3_MUSIC_CD_ID,
                           FRAME_ID_V3_ORIGARTIST,
                           FRAME_ID_V3_ORIG_FILENAME,
                           FRAME_ID_V3_ORIG_LYRICIST,
                           FRAME_ID_V3_ORIG_TITLE,
                           FRAME_ID_V3_OWNERSHIP,
                           FRAME_ID_V3_PLAYLIST_DELAY,
                           FRAME_ID_V3_PLAY_COUNTER,
                           FRAME_ID_V3_POPULARIMETER,
                           FRAME_ID_V3_POSITION_SYNC,
                           FRAME_ID_V3_PRIVATE,
                           FRAME_ID_V3_PUBLISHER,
                           FRAME_ID_V3_RADIO_NAME,
                           FRAME_ID_V3_RADIO_OWNER,
                           FRAME_ID_V3_RECOMMENDED_BUFFER_SIZE,
                           FRAME_ID_V3_RELATIVE_VOLUME_ADJUSTMENT,
                           FRAME_ID_V3_REMIXED,
                           FRAME_ID_V3_REVERB,
                           FRAME_ID_V3_SET,
                           FRAME_ID_V3_SET_SUBTITLE,
                           FRAME_ID_V3_SYNC_LYRIC,
                           FRAME_ID_V3_SYNC_TEMPO,
                           FRAME_ID_V3_TDAT,
                           FRAME_ID_V3_TERMS_OF_USE,
                           FRAME_ID_V3_TIME,
                           FRAME_ID_V3_TITLE,
                           FRAME_ID_V3_TITLE_REFINEMENT,
                           FRAME_ID_V3_TORY,
                           FRAME_ID_V3_TRACK,
                           FRAME_ID_V3_TRDA,
                           FRAME_ID_V3_TSIZ,
                           FRAME_ID_V3_TYER,
                           FRAME_ID_V3_UNIQUE_FILE_ID,
                           FRAME_ID_V3_UNSYNC_LYRICS,
                           FRAME_ID_V3_URL_ARTIST_WEB,
                           FRAME_ID_V3_URL_COMMERCIAL,
                           FRAME_ID_V3_URL_COPYRIGHT,
                           FRAME_ID_V3_URL_FILE_WEB,
                           FRAME_ID_V3_URL_OFFICIAL_RADIO,
                           FRAME_ID_V3_URL_PAYMENT,
                           FRAME_ID_V3_URL_PUBLISHERS,
                           FRAME_ID_V3_URL_SOURCE_WEB,
                           FRAME_ID_V3_USER_DEFINED_INFO,
                           FRAME_ID_V3_USER_DEFINED_URL);
  }

  @Override protected ImmutableSet<String> makeCommonFrames() {
    return ImmutableSet.of(FRAME_ID_V3_ARTIST,
                           FRAME_ID_V3_ALBUM,
                           FRAME_ID_V3_TITLE,
                           FRAME_ID_V3_GENRE,
                           FRAME_ID_V3_TRACK,
                           FRAME_ID_V3_TYER,
                           FRAME_ID_V3_COMMENT);
  }

  @Override protected ImmutableSet<String> makeBinaryFrames() {
    return ImmutableSet.of(FRAME_ID_V3_ATTACHED_PICTURE,
                           FRAME_ID_V3_AUDIO_ENCRYPTION,
                           FRAME_ID_V3_ENCRYPTION,
                           FRAME_ID_V3_EQUALISATION,
                           FRAME_ID_V3_EVENT_TIMING_CODES,
                           FRAME_ID_V3_GENERAL_ENCAPS_OBJECT,
                           FRAME_ID_V3_RELATIVE_VOLUME_ADJUSTMENT,
                           FRAME_ID_V3_RECOMMENDED_BUFFER_SIZE,
                           FRAME_ID_V3_UNIQUE_FILE_ID);
  }

  @Override protected ImmutableSet<String> makeExtensionFrames() {
    return ImmutableSet.of(FRAME_ID_V3_IS_COMPILATION,
                           FRAME_ID_V3_TITLE_SORT_ORDER_ITUNES,
                           FRAME_ID_V3_ARTIST_SORT_ORDER_ITUNES,
                           FRAME_ID_V3_ALBUM_SORT_ORDER_ITUNES,
                           FRAME_ID_V3_TITLE_SORT_ORDER_MUSICBRAINZ,
                           FRAME_ID_V3_ARTIST_SORT_ORDER_MUSICBRAINZ,
                           FRAME_ID_V3_ALBUM_SORT_ORDER_MUSICBRAINZ,
                           FRAME_ID_V3_ALBUM_ARTIST_SORT_ORDER_ITUNES,
                           FRAME_ID_V3_COMPOSER_SORT_ORDER_ITUNES);
  }
}
