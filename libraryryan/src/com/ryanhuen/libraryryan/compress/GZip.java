
package com.ryanhuen.libraryryan.compress;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

/**
 * Created by ryanhuen
 */
public class GZip {
    public static final String TAG = GZip.class.getName();

    public static byte[] compress(byte[] content) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        GZIPOutputStream out = new GZIPOutputStream(bos);
        out.write(content);
        out.close();
        return bos.toByteArray();
    }

    public static byte[] compress(File file) throws IOException {
        FileInputStream inStream = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        GZIPOutputStream out = new GZIPOutputStream(bos);
        byte[] buffer = new byte[512 * 1024];
        int length;
        while ((length = inStream.read(buffer)) != -1) {
            out.write(buffer, 0, length);
        }
        inStream.close();
        out.close();
        return bos.toByteArray();
    }

}
