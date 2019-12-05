/*
 *  MusicTag Copyright (C)2003,2004
 *
 *  This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser
 *  General Public  License as published by the Free Software Foundation; either version 2.1 of the License,
 *  or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 *  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License along with this library; if not,
 *  you can get a copy from http://www.opensource.org/licenses/lgpl-license.php or write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */
package ealvatag.tag.id3.framebody;

import ealvatag.tag.InvalidTagException;
import ealvatag.tag.datatype.DataTypes;
import ealvatag.tag.datatype.NumberVariableLength;
import ealvatag.tag.id3.ID3v24Frames;
import okio.Buffer;

import java.nio.ByteBuffer;

/**
 * Play counter frame.
 * <p>
 * <p>
 * This is simply a counter of the number of times a file has been
 * played. The value is increased by one every time the file begins to
 * play. There may only be one "PCNT" frame in each tag. When the
 * counter reaches all one's, one byte is inserted in front of the
 * counter thus making the counter eight bits bigger. The counter must
 * be at least 32-bits long to begin with.
 * <p><table border=0 width="70%">
 * <tr><td colspan=2> &lt;Header for 'Play counter', ID: "PCNT"&gt;</td></tr>
 * <tr><td>Counter </td><td>$xx xx xx xx (xx ...)</td></tr>
 * </table>
 * <p>
 * <p>For more details, please refer to the ID3 specifications:
 * <ul>
 * <li><a href="http://www.id3.org/id3v2.3.0.txt">ID3 v2.3.0 Spec</a>
 * </ul>
 *
 * @author : Paul Taylor
 * @author : Eric Farng
 * @version $Id$
 */
public class FrameBodyPCNT extends AbstractID3v2FrameBody implements ID3v24FrameBody, ID3v23FrameBody {
    private static final int COUNTER_MINIMUM_FIELD_SIZE = 4;

    /**
     * Creates a new FrameBodyPCNT datatype.
     */
    public FrameBodyPCNT() {
        this.setObjectValue(DataTypes.OBJ_NUMBER, 0L);
    }

    public FrameBodyPCNT(FrameBodyPCNT body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyPCNT datatype.
     *
     * @param counter
     */
    public FrameBodyPCNT(long counter) {
        this.setObjectValue(DataTypes.OBJ_NUMBER, counter);
    }

    /**
     * Creates a new FrameBodyPCNT datatype.
     *
     * @param byteBuffer
     * @param frameSize
     *
     * @throws InvalidTagException if unable to create framebody from buffer
     */
    public FrameBodyPCNT(ByteBuffer byteBuffer, int frameSize) throws InvalidTagException {
        super(byteBuffer, frameSize);
    }

    public FrameBodyPCNT(Buffer byteBuffer, int frameSize) throws InvalidTagException {
        super(byteBuffer, frameSize);
    }

    /**
     * @return the play count of this file
     */
    public long getCounter() {
        return ((Number)getObjectValue(DataTypes.OBJ_NUMBER)).longValue();
    }

    /**
     * Set the play counter of this file
     *
     * @param counter
     */
    public void setCounter(long counter) {
        setObjectValue(DataTypes.OBJ_NUMBER, counter);
    }

    /**
     * The ID3v2 frame identifier
     *
     * @return the ID3v2 frame identifier  for this frame type
     */
    public String getIdentifier() {
        return ID3v24Frames.FRAME_ID_PLAY_COUNTER;
    }

    /**
     *
     */
    protected void setupObjectList() {
        addDataType(new NumberVariableLength(DataTypes.OBJ_NUMBER, this, COUNTER_MINIMUM_FIELD_SIZE));
    }
}
