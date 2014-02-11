package com.vhly.utils.compress.lz77;

import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 14-2-11
 * Email: vhly@163.com
 */
public class LZ77InputStreamTest extends TestCase{
    public void testRead() throws IOException {
        byte[] data = new byte[]{'A', 'B', 'C', (byte) 0x0F4, (byte) 0x80, (byte) 0x18, (byte) 0x0C8, (byte) 0x0F4, (byte) 0x80, (byte) 0x18, (byte) 0x0F4, (byte) 0x80, (byte) 0x18, (byte) 0x0F4, (byte) 0x80, (byte) 0x18, (byte) 0x0F4, (byte) 0x80, (byte) 0x18, (byte) 0x0F4, (byte) 0x80, (byte) 0x18, (byte) 0x0F4, (byte) 0x80, (byte) 0x18, (byte) 0x0F4, (byte) 0x80, (byte) 0x18, (byte) 0x0F4, (byte) 0x80, (byte) 0x18, (byte) 0x0F4, (byte) 0x80, (byte) 0x18,};
        LZ77InputStream inputStream = new LZ77InputStream(new ByteArrayInputStream(data));
        while (true) {
            int d = inputStream.read();
            if (d == -1) {
                break;
            }
            System.out.println("d = " + (char) d);
        }
        inputStream.close();
    }
}
