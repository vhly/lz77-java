package com.vhly.utils.compress.lz77;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 14-1-14
 * Email: vhly@163.com
 */
public class LZ77InputStream extends FilterInputStream {

    private LZDataBufferStream stream;

    public LZ77InputStream(InputStream in) {
        super(in);
        stream = new LZDataBufferStream();
    }

    @Override
    public void close() throws IOException {
        super.close();
        stream.close();
        stream = null;
    }

    @Override
    public int read() throws IOException {
        int ret = -1;
        int tmp = stream.read();
        if (tmp == -1) {
            int b = super.read();
            if (b >= 1 && b <= 8) {
                for (int i = 0; i < b; i++) {
                    int b1 = super.read();
                    stream.write(b1);
                }
                ret = stream.read();
            } else if (b == 0 || (b >= 9 && b <= 0x7f)) {
                stream.write(b);
                ret = stream.read();
            } else if (b >= 0xc0 && b <= 0xff) {
                int v = b ^ 0x80;
                stream.write(' ');
                stream.write(v);
                ret = stream.read();
            } else if (b >= 0x80 && b <= 0xbf) {
                int v = super.read();
                int nn = (b << 8) | v;
                nn &= 0x3fff;
                int d = nn >> 3;
                int l = (nn & 0x07) + 3;
                try {
                    stream.readBackwardData(d, l);
                    ret = stream.read();
                } catch (DataBufferException e) {
                    ret = -1;
                    e.printStackTrace();
                }
            }

        } else {
            ret = tmp;
        }
        return ret;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (b == null) {
            throw new NullPointerException();
        } else if (off < 0 || len < 0 || len > b.length - off) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return 0;
        }

        int c = read();
        if (c == -1) {
            return -1;
        }
        b[off] = (byte) c;

        int i = 1;
        try {
            for (; i < len; i++) {
                c = read();
                if (c == -1) {
                    break;
                }
                b[off + i] = (byte) c;
            }
        } catch (IOException ee) {
        }
        return i;
    }
}
