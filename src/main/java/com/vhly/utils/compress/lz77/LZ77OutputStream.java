package com.vhly.utils.compress.lz77;

import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 14-2-10
 * Email: vhly@163.com
 */
public class LZ77OutputStream extends FilterOutputStream {

    private ByteArrayOutputStream bout;

    private int writeLength;

    public LZ77OutputStream(OutputStream out) {
        super(out);
        bout = new ByteArrayOutputStream(2048);
    }

    @Override
    public void write(int b) throws IOException {
        if (writeLength <= 2048) {
            bout.write(b);
        } else {
            flush();
            writeLength = 0;
            bout.write(b);
        }
        writeLength++;
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        super.write(b, off, len);
    }

    @Override
    public void flush() throws IOException {
        // TODO compress byte array output data with LZ77
        bout.flush();
        byte[] origData = bout.toByteArray();
        int length = origData.length;
        if (length > 0) {
            int pos = 0;

            int cc, dd;
            while (pos < length) {
                cc = origData[pos];
                cc = cc & 0x0ff;
                if ((cc < 10 && cc > 0) || (cc > 0x7F)) {
                    int escapeCharCount = 1;
                    for (int i = pos + 1; i < length; i++) {
                        dd = origData[i] & 0x0ff;
                        if ((dd < 10 && dd > 0) || (dd > 0x7F)) {
                            escapeCharCount++;
                        } else {
                            break;
                        }
                    }
                    if(escapeCharCount > 8){
                        escapeCharCount = 8;
                    }
                    super.write(escapeCharCount);
                    for (int i = 0; i < escapeCharCount; i++) {
                        super.write(origData[pos++]);
                    }
                } else {

                    int sameLen = 0;
                    for (int i = pos + 1; i < length; i++) {
                        dd = origData[i];
                        if (dd != cc) {
                            break;
                        } else {
                            sameLen++;
                        }
                    }
                    if (sameLen > 2) {
                        // TODO process same dup write

                        // length is sameLen + 1
                        // pos is current
                        super.write(cc);
                        pos++;
                        if (sameLen > 10) {
                            sameLen = 10;
                        }
                        int d = ((1 << 3) | ((sameLen - 3) & 0x07)) + 0x8000;
                        int h = d >> 8;
                        int l = d & 0x0ff;
                        super.write(h);
                        super.write(l);
                        pos += sameLen;
                    } else {
                        // TODO Process not same for next seq
                        // Check match from prev
                        // chunk match length longest is 10
                        if (pos > 10) {
                            boolean found = false;
                            int chunkLength = 10;
                            int fpos = -1;
                            if (pos + chunkLength > length) {
                                chunkLength = length - pos;
                            }
                            for (; chunkLength > 2; chunkLength--) {
                                fpos = findPos(origData, pos, chunkLength);
                                if (fpos != -1) {
                                    // TODO Write <d,l>
                                    found = true;
                                    break;
                                }
                            }
                            if (!found) {
                                // TODO Write origData
                                if (cc == ' ') {
                                    if (pos + 1 < length) {
                                        byte spc = origData[pos + 1];
                                        if (spc >= 0x40) {
                                            int b = spc ^ 0x80;
                                            super.write(b);
                                            pos += 2;
                                        } else {
                                            super.write(cc);
                                            pos++;
                                        }
                                    }
                                } else {
                                    super.write(cc);
                                    pos++;
                                }
                            } else {
                                int d = (((pos - fpos) << 3) | ((chunkLength - 3) & 0x07)) + 0x8000;
                                int h = d >> 8;
                                int l = d & 0x0ff;
                                super.write(h);
                                super.write(l);
                                pos += chunkLength;
                            }
                        } else {
                            super.write(cc);
                            pos++;
                        }
                    }
                }
            }
        }
        bout.reset();
        super.flush();
    }


    @Override
    public void close() throws IOException {
        flush();
        super.close();
    }

    private static int findPos(byte[] data, int pos, int len) {
        int ret = -1;
        if (data != null) {
            int dlen = data.length;
            if (dlen < pos + len) {
                len = dlen - pos;
            }
            byte[] tmp = new byte[len];
            System.arraycopy(data, pos, tmp, 0, len);
            boolean found = true;
            int dd = 0;
            for (; dd < pos - len; dd++) {
                found = true;
                for (int i = 0; i < len; i++) {
                    byte b1 = tmp[i];
                    byte b2 = data[dd + i];
                    if (b1 != b2) {
                        // TODO Not match
                        found = false;
                        break;
                    }
                }
                if (found) {
                    break;
                }
            }
            if (found) {
                ret = dd;
            }
        }
        return ret;
    }
}
