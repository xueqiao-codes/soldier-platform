package org.soldier.platform.thriftgen.util;

import org.soldier.base.logger.AppLog;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

    public static void zipFolder(String folderPath ,String zipPath) throws IOException {
        zipFolder(folderPath, zipPath, false);
    }

    /**
     * 压缩整个文件夹中的所有文件，生成指定名称的zip压缩包
     * @param folderPath 文件所在目录
     * @param zipPath 压缩后zip文件名称
     * @param dirFlag zip文件中第一层是否包含一级目录，true包含；false没有
     * 2015年6月9日
     */
    public static void zipFolder(String folderPath ,String zipPath, boolean dirFlag) throws IOException{
        try {
            File file = new File(folderPath);
            File zipFile = new File(zipPath);
            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
            if (file.isDirectory()){
                File[] files = file.listFiles();
                for (File fileSec:files){
                    if (dirFlag){
                        recursionZip(zipOut, fileSec, file.getName() + File.separator);
                    } else {
                        recursionZip(zipOut, fileSec, "");
                    }
                }
            }
            zipOut.close();
        } catch (IOException e) {
            AppLog.e(e);
        }
    }

    private static void recursionZip(ZipOutputStream zipOut, File file, String baseDir) throws IOException {
        if (file.isDirectory()){
            File[] files = file.listFiles();
            for (File fileSec:files){
                recursionZip(zipOut, fileSec, baseDir + file.getName() + File.separator);
            }
        } else {
            byte[] buf = new byte[1024];
            InputStream input = new FileInputStream(file);
            zipOut.putNextEntry(new ZipEntry(baseDir + file.getName()));
            int len;
            while((len = input.read(buf)) != -1){
                zipOut.write(buf, 0, len);
            }
            input.close();
        }
    }
}
