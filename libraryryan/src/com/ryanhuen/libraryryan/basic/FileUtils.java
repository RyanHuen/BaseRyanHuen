
package com.ryanhuen.libraryryan.basic;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import android.text.TextUtils;

/**
 * Created by ryanhuen
 */
public class FileUtils {
    public static final String TAG = FileUtils.class.getName();

    /**
     * 确认一个目录存在，如果不存在，则尝试创建此目录。
     *
     * @param path 目录的全路径名
     * @return 如果目录存在，则返回 true，如果无法创建此目录，则返回 false.
     */
    public static boolean makeSurePathExists(String path) {
        File file = new File(path);
        return makeSurePathExists(file);
    }

    /** @see FileUtils#makeSurePathExists(String) */
    public static boolean makeSurePathExists(File path) {
        return makeSurePathExists(path, false);
    }

    /**
     * 确认一个目录存在，如果不存在，则尝试创建此目录。
     *
     * @param path 路径全路径名
     * @param delete 如果path是文件而不是文件夹，是否删除这个文件，创建文件夹
     * @return true:存在目录或创建成功 false:创建失败
     */
    public static boolean makeSurePathExists(File path, boolean delete) {
        if (path == null) {
            return false;
        }
        if (path.isDirectory()) {
            return true;
        }

        if (!path.exists()) {
            return path.mkdirs();
        } else {
            // 删除文件，创建文件夹
            if (delete) {
                if (!path.delete()) {
                    return false;
                }
                return path.mkdirs();
            }
            // 存在，但是上面的 isDirectory() 返回了 false，说明这是一个已经存在的文件，不是目录
            return false;
        }
    }

    /**
     * 读取源文件中的字符串
     */
    public static String readFileContentAsString(File file) {
        byte[] byteArray = readFileContentAsByteArray(file);
        if (byteArray != null) {
            return new String(byteArray);
        }

        return null;
    }

    /**
     * 读取源文件字符数组
     *
     * @param file 获取字符数组的文件
     * @return 字符数组
     */
    public static byte[] readFileContentAsByteArray(File file) {
        FileInputStream fis = null;
        FileChannel fc = null;
        byte[] data = null;
        try {
            fis = new FileInputStream(file);
            fc = fis.getChannel();
            data = new byte[(int) (fc.size())];
            fc.read(ByteBuffer.wrap(data));
        } catch (Exception e) {
        } finally {
            if (fc != null) {
                try {
                    fc.close();
                } catch (IOException e) {
                    // ignore
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
        return data;
    }

    /**
     * 把字符串转换成字符数组写入文件
     * 
     * @param text 写入文件的字符串
     * @param destFile 目标文件
     * @return
     */
    public static boolean writeTextFile(String text, File destFile) {
        if (text == null) {
            return false;
        }

        return writeByteFile(text.getBytes(), destFile);
    }

    /**
     * 把一个字符数组写入文件
     * 
     * @param bytes 写入文件的字符数组
     * @param destFile 目标文件
     * @return
     */
    public static boolean writeByteFile(byte[] bytes, File destFile) {
        if (bytes == null) {
            return false;
        }

        boolean ret = false;
        ByteArrayInputStream fis = null;
        try {
            fis = new ByteArrayInputStream(bytes);
            ret = copyStreamToFile(fis, destFile);
        } catch (Exception e) {
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                }
            }
        }
        return ret;
    }

    /**
     * 复制整个文件夹，如果文件夹不存在则创建
     *
     * @Param oldPath 旧目录
     * @Param newPath 新目录
     * @Returns
     */
    public static boolean copyFolder(String oldPath, String newPath) {
        File oldFolder = new File(oldPath);
        if (!oldFolder.exists() || !oldFolder.isDirectory()) {
            return false;
        }

        File newFolder = new File(newPath);
        if (!newFolder.exists()) {
            newFolder.mkdirs(); // 如果文件夹不存在 则建立新文件夹
        }

        File[] files = oldFolder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    copyFile(file, new File(newFolder, file.getName()));
                } else if (file.isDirectory()) {
                    copyFolder(file.getAbsolutePath(),
                            new File(newFolder, file.getName()).getAbsolutePath());
                }
            }
        }

        return true;
    }

    /**
     * 拷贝文件内容
     */
    public static boolean copyFile(File srcFile, File destFile) {
        boolean result = false;
        if (srcFile != null && srcFile.exists()) {
            InputStream in = null;
            try {
                in = new FileInputStream(srcFile);
                result = copyStreamToFile(in, destFile);
            } catch (Exception ex) {
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (Exception ex) {
                        // ignore
                    }
                }
            }
        }
        return result;
    }

    /**
     * 把一个InputStream写入文件
     */
    public static boolean copyStreamToFile(InputStream inputStream, File destFile) {
        try {
            FileOutputStream out = new FileOutputStream(destFile);
            try {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) >= 0) {
                    out.write(buffer, 0, bytesRead);
                }
            } finally {
                out.flush();
                try {
                    out.getFD().sync();
                } catch (IOException e) {
                }
                out.close();
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 删除目录以及子目录
     *
     * @param filepath 要删除的目录地址
     */
    public static boolean deleteDir(String filepath) {
        if (TextUtils.isEmpty(filepath)) {
            return false;
        }

        return deleteDir(new File(filepath));
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.exists() && dir.isDirectory()) {
            File[] delFiles = dir.listFiles();
            if (delFiles != null) {
                if (delFiles.length == 0) {
                    return dir.delete();
                } else {
                    for (File delFile : delFiles) {
                        if (delFile != null) {
                            if (delFile.isDirectory()) {
                                deleteDir(delFile.getAbsolutePath());
                            }
                            delFile.delete();
                        }
                    }
                    return dir.delete();
                }
            }
        }

        return false;
    }

    /**
     * 删除一个文件或文件夹
     *
     * @param filepath 要删除的文件路径
     */

    public static boolean deleteFile(String filepath) {
        if (TextUtils.isEmpty(filepath)) {
            return false;
        }

        return deleteFile(new File(filepath));
    }

    public static boolean deleteFile(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                return deleteDir(file);
            } else {
                return file.delete();
            }
        }

        return false;
    }

}
