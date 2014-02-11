package com.vhly.utils.compress.lz77;

import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 14-1-17
 * Email: vhly@163.com
 */
public class LZDataBufferStream {
    private Vector<LZDataBuffer> buffers;

    private int bufferSize = 1024;

    public LZDataBufferStream() {
        this.buffers = new Vector<LZDataBuffer>();
    }

    /**
     * Set new create block buffer size, in byte.<br/>
     * This method only support new create block<br/>
     * If you first set it to 10, and the second set it to 1024, first create block size only 10, not 1024.
     *
     * @param bufferSize int this size from 1 to 102400 bytes. 100K bytes must.
     */
    public void setBufferSize(int bufferSize) {
        if (bufferSize > 0 && bufferSize <= 102400) {
            this.bufferSize = bufferSize;
        }
    }

    public void resetReadPoint() {
        if (buffers != null) {
            for (LZDataBuffer buffer : buffers) {
                buffer.currentReadPos = 0;
            }
        }
    }

    public int read() {
        int ret = -1;
        if (!buffers.isEmpty()) {

            LZDataBuffer lzDataBuffer = null;
            for (LZDataBuffer buffer : buffers) {
                if (buffer.currentReadPos != buffer.totalLength) {
                    lzDataBuffer = buffer;
                    break;
                }
            }
            if (lzDataBuffer != null) {
                try {
                    ret = lzDataBuffer.read();
                } catch (DataBufferException e) {
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }

    /**
     * Read backward characters and append stream end.
     *
     * @param currentReadDistance current read position back distance
     * @param len                 read length
     * @return byte[]
     * @throws DataBufferException
     */
    public byte[] readBackwardData(int currentReadDistance, int len) throws DataBufferException {
        byte[] ret = null;
        if (!buffers.isEmpty()) {

            LZDataBuffer lzDataBuffer = null;
            for (LZDataBuffer buffer : buffers) {
                if (buffer.currentReadPos < buffer.totalLength) {
                    lzDataBuffer = buffer;
                    break;
                }
            }
            if (lzDataBuffer == null) {
                lzDataBuffer = buffers.lastElement();
            }
            if (lzDataBuffer != null) {
                int cp = lzDataBuffer.currentReadPos;
                if (cp >= currentReadDistance) {

                    ret = new byte[len];
                    if (currentReadDistance >= len) {
                        System.arraycopy(lzDataBuffer.data, cp - currentReadDistance, ret, 0, len);
                        for (byte b : ret) {
                            write(b);
                        }
                    } else {
                        for (int i = 0; i < len; i++) {
                            byte dd = lzDataBuffer.data[cp - currentReadDistance];
                            write(dd);
                            ret[i] = dd;
                            cp++;
                        }
                    }
                } else {
                    // 当前的Buffer读取的位置小于指定的距离，那么就要将这个Buffer之前的所有Buffer 都取出来，拼出完整距离
                    int cc = currentReadDistance - cp;
                    int count = buffers.size();
                    if (count > 1) {
                        int firstBufferIndex = -1;
                        int firstReadPos = -1;
                        int firstLength = len;
                        for (int i = count - 2; i >= 0; i--) {
                            LZDataBuffer buffer = buffers.get(i);
                            if (buffer.totalLength >= cc) {
                                // find and read from here
                                firstBufferIndex = i;
                                firstReadPos = buffer.totalLength - cc;
                                break;
                            } else {
                                cc -= buffer.totalLength;
                            }
                        }
                        if (firstBufferIndex == -1) {
                            throw new DataBufferException("prev Buffer not enough data, distance to long");
                        }

                        ret = new byte[len];
                        // 读第一个区
                        LZDataBuffer buffer = buffers.get(firstBufferIndex);
                        if (len + firstReadPos > buffer.totalLength) {
                            firstLength = buffer.totalLength - firstReadPos;
                        } else {
                            firstLength = len;
                        }
                        cc = 0;
                        for (int i = 0; i < firstLength; i++) {
                            ret[i] = (byte) buffer.getByte(firstReadPos + i);
                            write(ret[i]);
                            cc++;
                        }

                        boolean finished = false;

                        if (cc < len) {
                            for (int i = firstBufferIndex + 1; i < count; i++) {
                                buffer = buffers.get(i);
                                int tt = buffer.totalLength;
                                for (int j = 0; j < tt; j++) {
                                    byte aByte = (byte) buffer.getByte(j);
                                    ret[cc++] = aByte;
                                    write(aByte);
                                    if (cc == len) {
                                        finished = true;
                                        break;
                                    }
                                }
                                if (finished) {
                                    break;
                                }
                            }
                        }
                    } else {
                        throw new DataBufferException("prev Buffer not enough data");
                    }
                }
            }
        }
        return ret;
    }

    /**
     * Write a byte to end, if buffer block not enough, create one and append stream end.<br/>
     * Default block size is 1024, you can reset it with setBufferSize(int size)
     *
     * @param b int
     * @see LZDataBufferStream#setBufferSize(int)
     */
    public void write(int b) {
        LZDataBuffer lastBuffer = null;
        if (buffers.isEmpty()) {
            lastBuffer = new LZDataBuffer(bufferSize);
            lastBuffer.currentBufferPos = 0;
            buffers.add(lastBuffer);
        } else {
            lastBuffer = buffers.lastElement();
            if (lastBuffer.isFull()) {
                LZDataBuffer b1 = lastBuffer;
                lastBuffer = new LZDataBuffer(bufferSize);
                lastBuffer.currentBufferPos = b1.currentBufferPos + b1.totalLength;
                buffers.add(lastBuffer);
            }
        }
        try {
            lastBuffer.write(b);
        } catch (DataBufferException e) {
            e.printStackTrace();
        }
    }

    public void skipAbs(long posFromHead) {
        if (posFromHead >= 0) {
            int index = -1;
            boolean found = false;
            for (LZDataBuffer buffer : buffers) {
                index++;
                if (buffer.currentBufferPos <= posFromHead && buffer.currentBufferPos + buffer.totalLength > posFromHead) {
                    buffer.currentReadPos = (int) posFromHead - buffer.currentBufferPos;
                    found = true;
                    break;
                }
            }
            if (found && index != -1) {
                int size = buffers.size();
                for (int i = 0; i < index; i++) {
                    LZDataBuffer buffer = buffers.get(i);
                    buffer.currentReadPos = buffer.totalLength;
                }
                for (int i = index + 1; i < size; i++) {
                    LZDataBuffer buffer = buffers.get(i);
                    buffer.currentReadPos = 0;
                }
            }
        }
    }

    public void close() {
        buffers.clear();
        buffers = null;
    }
}
