package com.vhly.utils.compress.lz77;

import com.vhly.utils.StreamUtil;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 14-2-11
 * Email: vhly@163.com
 */
public class LZ77OutputStreamTest extends TestCase {

    public void testCompressRate(){
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        for(int i = 0;i<100;i++){
            for(int j = 0;j<100;j++){
                bout.write('A');
                bout.write('B');
                bout.write('C');
                bout.write('D');
                bout.write('E');
                bout.write('F');
                bout.write('G');
                bout.write('G');
                bout.write('G');
                bout.write('G');
                bout.write('G');
                bout.write('G');
                bout.write('G');
                bout.write('G');
                bout.write('G');
                bout.write('G');
                bout.write('G');
                bout.write('G');
                bout.write('G');
                bout.write('G');
                bout.write('G');
                bout.write('G');
                bout.write('G');
                bout.write('G');
                bout.write('G');
                bout.write('G');
                bout.write('G');
            }
        }
        byte[] data = bout.toByteArray();

        bout.reset();
        LZ77OutputStream lz77OutputStream = new LZ77OutputStream(bout);
        try {
            lz77OutputStream.write(data);
            lz77OutputStream.flush();
            byte[] encData = bout.toByteArray();
            int origLen = data.length;
            int encLen = encData.length;
            float dol = origLen;
            float eol = encLen;
            float rate = eol / dol;
            System.out.println("rate = " + rate);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamUtil.close(lz77OutputStream);
            StreamUtil.close(bout);
        }
    }

    public void testHTMLSave() throws UnsupportedEncodingException {
        String content = "<html><head><title>GooD Test</title><body><span>char GooD is GooD, Today is good day</span> </body></html>";
        saveStringAndCompare(content);
    }

    public void testUnicodeSave1() throws UnsupportedEncodingException {
        String content = "中文应该是中文吧 T GooD 中文应该是中文吧";
        saveStringAndCompare(content);
    }

    private void saveStringAndCompare(String content) throws UnsupportedEncodingException {
        String dec = "";

        byte[] data = content.getBytes("UTF-8");
        String chnByte = Arrays.toString(data);
        System.out.println("chnByte = " + chnByte);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        LZ77OutputStream lz77OutputStream = new LZ77OutputStream(out);

        ByteArrayInputStream bin = null;
        LZ77InputStream lz77InputStream = null;
        String decStr = "";
        try {
            lz77OutputStream.write(data);
            lz77OutputStream.flush();
            byte[] encBuf = out.toByteArray();

            String encStr = Arrays.toString(encBuf);
            System.out.println("encStr = " + encStr);

            bin = new ByteArrayInputStream(encBuf);
            lz77InputStream = new LZ77InputStream(bin);
            byte[] decBuf = StreamUtil.readStream(lz77InputStream);

            decStr = new String(decBuf,"UTF-8");
            System.out.println("decStr = " + decStr);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamUtil.close(lz77OutputStream);
            StreamUtil.close(out);
        }

        assertEquals(content, decStr);
    }

    public void testWrite() {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayOutputStream out1 = new ByteArrayOutputStream();

        LZ77OutputStream lz77OutputStream = new LZ77OutputStream(out);
        ByteArrayInputStream bin = null;
        LZ77InputStream lz77InputStream = null;
        try {

            for (int i = 0; i < 20; i++) {
                lz77OutputStream.write(' ');
                lz77OutputStream.write('a');
                out1.write(' ');
                out1.write('a');
            }

            for (int i = 0; i < 10; i++) {
                lz77OutputStream.write('A');
                lz77OutputStream.write('B');
                lz77OutputStream.write('C');
                out1.write('A');
                out1.write('B');
                out1.write('C');
            }

            for (int i = 0; i < 20; i++) {
                lz77OutputStream.write('D');
                out1.write('D');
            }

            for (int i = 0; i < 3; i++) {
                lz77OutputStream.write('E');
                lz77OutputStream.write('F');
                out1.write('E');
                out1.write('F');
            }

            for (int i = 0; i < 20; i++) {
                lz77OutputStream.write(' ');
                lz77OutputStream.write('a');
                out1.write(' ');
                out1.write('a');
            }


            lz77OutputStream.flush();
            byte[] bytes2 = out.toByteArray();
            String s2 = Arrays.toString(bytes2);
            System.out.println("compressed = " + s2);


            bin = new ByteArrayInputStream(bytes2);
            lz77InputStream = new LZ77InputStream(bin);
            byte[] bytes = StreamUtil.readStream(lz77InputStream);

            String s1 = Arrays.toString(bytes);
            System.out.println("decompressed = " + s1);

            byte[] bytes1 = out1.toByteArray();
            String sorig = Arrays.toString(bytes1);
            System.out.println("orig = " + sorig);

            boolean equals = Arrays.equals(bytes1, bytes);
            assertTrue(equals);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamUtil.close(lz77OutputStream);
            StreamUtil.close(out);
            StreamUtil.close(out1);
            StreamUtil.close(lz77InputStream);
            StreamUtil.close(bin);
        }
    }

}
