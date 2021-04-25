package org.soldier.platform.thriftgen.util;

import java.io.*;
import java.nio.channels.FileChannel;

public class FileUtil {

    public static boolean fileExist(String path) {
        if (!TextUtil.isEmpty(path)) {
            return new File(path).exists();
        }
        return false;
    }

    public static String getFileContentByLine(String path) {
        File file = new File(path);
        if(file.exists() || !file.isDirectory()) {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
                String temp;
                StringBuffer sb = new StringBuffer();
                temp = br.readLine();
                while (temp != null) {
                    sb.append(temp).append("\n");
                    temp = br.readLine();
                }
                return sb.toString();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                safelyClose(br);
            }
        }
        return "";
    }

    public static byte[] readFileToByteArray(File f) {
        if (f != null && f.exists() && !f.isDirectory()) {
        }
        return null;
    }

    public static boolean mkdirs(String path) {
        if (!TextUtil.isEmpty(path)) {
            File file = new File(path);
            if (!file.exists()) {
                return file.mkdirs();
            }
        }

        return false;
    }

    public static void storeInputStreamToFile(String dstPath, InputStream is) {
        File of = new File(dstPath);
        try {
            of.createNewFile();
            FileOutputStream os = new FileOutputStream(of);

            copyInStreams(is, os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyInStreams(InputStream is, OutputStream os) {
        byte[] buffer = new byte[4 * 1024];
        int nRead;
        try {
            while ((nRead = is.read(buffer)) != -1) {
                if (nRead == 0) {
                    nRead = is.read();
                    if (nRead < 0)
                        break;
                    os.write(nRead);
                    continue;
                }
                os.write(buffer, 0, nRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        safelyClose(is);
        safelyClose(os);
    }

    public static void safelyClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void fileChannelCopy(File s, File t) {
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;

        try {
            fi = new FileInputStream(s);
            fo = new FileOutputStream(t);

            if (fi != null && fo != null) {
                in = fi.getChannel();
                out = fo.getChannel();
                in.transferTo(0, in.size(), out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            safelyClose(fi);
            safelyClose(fo);
            safelyClose(in);
            safelyClose(out);
        }
    }

    /**
     *  根据路径删除指定的目录或文件，无论存在与否
     *@param sPath  要删除的目录或文件
     *@return 删除成功返回 true，否则返回 false。
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 判断目录或文件是否存在
        if (!file.exists()) {  // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) {  // 为文件时调用删除文件方法
                return file.delete();
            } else {  // 为目录时调用删除目录方法
                return deleteDirectory(sPath);
            }
        }
    }

    /**
     *  根据路径删除指定的目录或文件，无论存在与否
     *@param sPath  要删除的目录或文件
     *@return 删除成功返回 true，否则返回 false。
     */
    public static boolean deleteFolder(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 判断目录或文件是否存在
        if (!file.exists()) {  // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) {  // 为文件时调用删除文件方法
                return deleteFile(sPath);
            } else {  // 为目录时调用删除目录方法
                return deleteDirectory(sPath);
            }
        }
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     * @param   sPath 被删除目录的文件路径
     * @return  目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (File file : files) {
            //删除子文件
            if (file.isFile()) {
                flag = deleteFile(file.getAbsolutePath());
                if (!flag) {
                    break;
                }
            } //删除子目录
            else {
                flag = deleteDirectory(file.getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }
        if (!flag) {
            return false;
        }
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }
}
