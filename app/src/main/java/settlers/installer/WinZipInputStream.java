/*
 */
package settlers.installer;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * The WinZipInputStream can handle self-extracting archives.
 * inspired by https://stackoverflow.com/a/7955605/4222206
 *
 * @author hiran
 */
public class WinZipInputStream extends FilterInputStream {
public static final byte[] ZIP_LOCAL = { 0x50, 0x4b, 0x03, 0x04 };
    protected int ip;
    protected int op;

    /**
     * Creates a new WinZipInputStream.
     * 
     * @param is  the file to read from
     */
    public WinZipInputStream(InputStream is) {
        super(is);
    }

    /**
     * Reads the next integer from the stream.
     * @return the data read
     * @throws IOException something went wrong
     */
    public int read() throws IOException {
        while(ip < ZIP_LOCAL.length) {
            int c = super.read();
            if (c == ZIP_LOCAL[ip]) {
                ip++;
            }
            else ip = 0;
        }

        if (op < ZIP_LOCAL.length)
            return ZIP_LOCAL[op++];
        else
            return super.read();
    }

    /**
     * Reads data from this stream.
     * 
     * @param b the buffer to read into
     * @param off the offset in the buffer
     * @param len the length to read
     * @return the number of read bytes
     * @throws IOException something went wrong
     */
    public int read(byte[] b, int off, int len) throws IOException {
        if (op == ZIP_LOCAL.length) return super.read(b, off, len);
        int l = 0;
        while (l < Math.min(len, ZIP_LOCAL.length)) {
            b[l++] = (byte)read();
        }
        return l;
    }
}
