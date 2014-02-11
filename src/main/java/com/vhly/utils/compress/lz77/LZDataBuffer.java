package com.vhly.utils.compress.lz77;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 14-1-17
 * Email: vhly@163.com
 */
public class LZDataBuffer {
    public int totalLength;
    public int currentBufferPos;

    public int contentLength;

    public byte[] data;
    public int currentReadPos;

    public LZDataBuffer(int totalLength) {
        if (totalLength <= 0) {
            totalLength = 1024;
        } else if (totalLength > 102400) {
            totalLength = 102400;
        }
        this.totalLength = totalLength;
        data = new byte[totalLength];
    }

    public boolean isFull() {
        return contentLength == totalLength;
    }

    public void write(int b) throws DataBufferException {
        if (contentLength == totalLength) {
            throw new DataBufferException("Need Create Next Buffer");
        }
        data[contentLength++] = (byte) b;
    }

    public int read() throws DataBufferException {
        int ret = -1;
        if (data != null) {
            if (currentReadPos < totalLength && currentReadPos < contentLength) {
                ret = (data[currentReadPos++] & 0x0ff);
            }
        }
        return ret;
    }

    public int getByte(int currentGetPos) {
        int ret = -1;
        if (currentGetPos >= 0) {
            if (currentGetPos < totalLength) {
                ret = data[currentGetPos];
            }
        }
        return ret;
    }
}
