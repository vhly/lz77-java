package com.vhly.utils.compress.lz77;

import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 14-2-11
 * Email: vhly@163.com
 */
public class LZDataBufferStreamTest extends TestCase {
    public void testReadWrite() throws DataBufferException {
        LZDataBufferStream stream = new LZDataBufferStream();
        stream.setBufferSize(10);
        for (int i = 0; i < 255; i++) {
            stream.write(i);
        }
        for (int i = 0; i < 255; i++) {
            int b = stream.read();
            assertEquals(i, b);
        }

        byte[] bytes = stream.readBackwardData(3, 1);
        assertNotNull(bytes);
        assertEquals(252, (bytes[0] & 0x0ff));

        bytes = stream.readBackwardData(20, 1);
        assertNotNull(bytes);
        assertEquals(235, (bytes[0] & 0x0ff));

        bytes = stream.readBackwardData(6, 2);
        assertNotNull(bytes);
        assertEquals(249, (bytes[0] & 0x0ff));
        assertEquals(250, (bytes[1] & 0x0ff));

    }

    public void testSkipAbs() {
        LZDataBufferStream stream = new LZDataBufferStream();
        stream.setBufferSize(10);
        for (int i = 0; i < 255; i++) {
            stream.write(i);
        }
        for (int i = 0; i < 255; i++) {
            int b = stream.read();
            assertEquals(i, b);
        }
        stream.skipAbs(0);
        int v = stream.read();
        assertEquals(v, 0);

        stream.skipAbs(10);
        v = stream.read();
        assertEquals(v, 10);

    }
}
