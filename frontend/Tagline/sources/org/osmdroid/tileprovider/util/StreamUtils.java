package org.osmdroid.tileprovider.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamUtils {
    public static final int IO_BUFFER_SIZE = 8192;

    private StreamUtils() {
    }

    public static long copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        InputStream in = inputStream;
        OutputStream out = outputStream;
        long length = 0;
        byte[] b = new byte[8192];
        while (true) {
            int read = in.read(b);
            int read2 = read;
            if (read == -1) {
                return length;
            }
            out.write(b, 0, read2);
            length += (long) read2;
        }
    }

    public static void closeStream(Closeable closeable) {
        Closeable stream = closeable;
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
