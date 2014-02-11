package com.vhly.utils;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 14-2-11
 * Email: vhly@163.com
 */
public final class StreamUtil {
    public static void close(Object stream) {
        if (stream != null) {
            try {
                if (stream instanceof InputStream) {
                    ((InputStream) stream).close();
                } else if (stream instanceof Reader) {
                    ((Reader) stream).close();
                } else if (stream instanceof OutputStream) {
                    ((OutputStream) stream).close();
                } else if (stream instanceof Writer) {
                    ((Writer) stream).close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static byte[] readStream(InputStream in) {
        byte[] ret = null;
        if (in != null) {
            ByteArrayOutputStream bout = null;
            bout = new ByteArrayOutputStream();
            try {
                readStream(in, bout);
                bout.flush();
                ret = bout.toByteArray();
                bout.reset();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close(bout);
                bout = null;
            }

        }
        return ret;
    }

    public static void readStream(InputStream in, OutputStream out) throws IOException {
        if (in != null && out != null) {
            byte[] buf = new byte[128];
            int len;
            while (true) {
                len = in.read(buf);
                if (len == -1) {
                    break;
                }
                out.write(buf, 0, len);
            }
            out.flush();
            buf = null;
        }
    }

    public static void readCharStream(Reader reader, Writer writer) throws IOException {
        if (reader != null && writer != null) {
            char[] chars = new char[128];
            int len;
            while (true) {
                len = reader.read(chars);
                if (len == -1) {
                    break;
                }
                writer.write(chars, 0, len);
            }
            chars = null;
        }
    }

    public static byte[] gzipCompress(byte[] orig) {
        byte[] ret = null;
        if (orig != null) {
            ByteArrayInputStream bin = null;
            ByteArrayOutputStream bout = null;
            GZIPOutputStream gzipOutputStream = null;
            try {
                bin = new ByteArrayInputStream(orig);
                bout = new ByteArrayOutputStream();
                gzipOutputStream = new GZIPOutputStream(bout, true);
                readStream(bin, gzipOutputStream);
                gzipOutputStream.finish();
                ret = bout.toByteArray();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                close(bin);
                close(gzipOutputStream);
                close(bout);

                bin = null;
                gzipOutputStream = null;
                bout = null;
            }
        }
        return ret;
    }

    public static byte[] gzipDecompress(byte[] compressedData) {
        byte[] ret = null;
        if (compressedData != null) {
            ByteArrayInputStream bin = null;
            ByteArrayOutputStream bout = null;
            GZIPInputStream gzipInputStream = null;
            try {
                bin = new ByteArrayInputStream(compressedData);
                gzipInputStream = new GZIPInputStream(bin);
                bout = new ByteArrayOutputStream();
                readStream(gzipInputStream, bout);
                ret = bout.toByteArray();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                close(bin);
                close(gzipInputStream);
                close(bout);

                bin = null;
                gzipInputStream = null;
                bout = null;
            }
        }
        return ret;
    }

    public static long readLongLE(InputStream in) throws IOException {
        long ret = -1;
        if (in != null) {
            ret = 0;
            for (int i = 0; i < 8; i++) {
                long i1 = in.read() & 0x0ff;
                ret += (i1 << (i * 8));
            }
        }
        return ret;
    }

    public static int readIntLE(InputStream in) throws IOException {
        int ret = -1;
        if (in != null) {
            ret = 0;
            for (int i = 0; i < 4; i++) {
                ret += ((in.read() & 0x0ff) << (i * 8));
            }
        }
        return ret;
    }

    public static short readShortLE(InputStream in) throws IOException {
        short ret = -1;
        if (in != null) {
            int l = in.read();
            int h = in.read();
            ret = (short) (((h & 0x0ff) << 8) + (l & 0x0ff));
            ret &= 0x0ffff;
        }
        return ret;
    }
}
