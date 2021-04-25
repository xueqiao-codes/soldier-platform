package org.soldier.platform.thriftgen.model;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.soldier.platform.thriftgen.Constants;
import org.soldier.platform.thriftgen.util.TextUtil;
import org.springframework.util.Base64Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ThriftFileInfo {

    private static final String ROOT_FOLDER_PATH = Constants.ROOT_FOLDER_PATH;

    private String mId;
    private String mPath;
    private String mName;
    private long mLength;
    private boolean mIsDirectory;
    private List<ThriftFileInfo> mChildFileInfos;

    public ThriftFileInfo(File f) {
        if (f != null && f.exists()) {
            mName = f.getName();
            mLength = f.length();
            mIsDirectory = f.isDirectory();
            loadChildInfo(f);
            mPath = f.getAbsolutePath();

            mId = Base64Utils.encodeToString(mPath.replaceAll(ROOT_FOLDER_PATH, "").getBytes());
        }
    }


    private void loadChildInfo(File f) {
        if (f != null && f.exists() && f.isDirectory()) {
            String[] filePaths = f.list();
            if (filePaths == null || filePaths.length <= 0) {
                return ;
            }

            List<String> childFilePaths = Arrays.asList(filePaths);
            Collections.sort(childFilePaths);

            mChildFileInfos = new ArrayList<ThriftFileInfo>();

            for (String childFilePath: childFilePaths) {
                File childFile = new File(f, childFilePath);
                if (childFile.isDirectory() || childFile.getName().endsWith(Constants.THRIFT_SUFFIX)) {
                    mChildFileInfos.add(new ThriftFileInfo(childFile));
                }
            }
        }
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String path() {
        return mPath;
    }

    public String getName() {
        return mName;
    }

    public long getLength() {
        return mLength;
    }

    public boolean isDirectory() {
        return mIsDirectory;
    }

    public List<ThriftFileInfo> getChildren() {
        return mChildFileInfos;
    }

    public static String getFullPathById(String id) {
        if (!TextUtil.isEmpty(id)) {
            byte[] decodedPath = Base64.decode(id);
            if (decodedPath != null) {
                String relativePath = new String(decodedPath);
                String fullPath = ROOT_FOLDER_PATH  + relativePath;
                return fullPath;
            } else {
                return "";
            }
        }
        return ROOT_FOLDER_PATH;
    }

    public static String getShortPath(String origPath) {
        if (!TextUtil.isEmpty(origPath) && origPath.contains(Constants.ROOT_FOLDER_PATH)) {
            origPath = origPath.replaceAll(ROOT_FOLDER_PATH, "");
            return origPath;
        }
        return origPath;
    }
}
