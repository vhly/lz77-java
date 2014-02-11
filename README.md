lz77-java
=========

LZ77 implement for Java, for PalmDoc content de/compress

Usage:

    1. Read compressed content

        InputStream in = .......; // For your resource
        LZ77InputStream inputStream = new LZ77InputStream(in);
        while (true) {
            int d = inputStream.read();
            if (d == -1) {
                break;
            }
            System.out.println("d = " + (char) d);
        }
        inputStream.close();

        or You can use byte[] StreamUtil.readStream(InputStream stream); for read fully.


    2. Compress content. Use LZ77OutputStream,
       When you want get compress data, please invoke LZ77OutputStream.flush() first.

       ByteArrayOutputStream bout = ......; // For your output stream.
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

    3. Important!

        1) LZ77OutputStream implement can't support Unicode content compress now,
            I will research this issue. It will come soon.
