package org.soldier.platform.thriftgen.service;

import org.apache.commons.io.FileUtils;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.thriftgen.Constants;
import org.soldier.platform.thriftgen.model.GenerateResult;
import org.soldier.platform.thriftgen.model.ThriftFileInfo;
import org.soldier.platform.thriftgen.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ThriftGenerateServiceImpl implements ThriftGenerateService {

    public List<ThriftFileInfo> getAllFilesInFolder(String folderId) {
        String fullPath = ThriftFileInfo.getFullPathById(folderId);
        if (FileUtil.fileExist(fullPath)) {
            return getAllFilesInFolder(fullPath, "");
        }
        return null;
    }

    public List<ThriftFileInfo> searchFiles(String keyword) {
        if (!TextUtil.isEmpty(keyword)) {
            List<ThriftFileInfo> childInfos = getAllFilesInFolder(Constants.ROOT_FOLDER_PATH, "");
            List<ThriftFileInfo> resultInfos = new ArrayList<ThriftFileInfo>();
            if (childInfos != null && childInfos.size() > 0) {
                for (ThriftFileInfo info: childInfos) {
                    if (!info.isDirectory()) {
                        if (info.getName().contains(keyword)) {
                            resultInfos.add(info);
                        }
                    } else {
                        resultInfos.addAll(getAllFilesInFolder(info.path(), keyword));
                    }
                }
            }
            return resultInfos;
        }
        return null;
    }

    public ThriftFileInfo getFile(String id) {
        String path = ThriftFileInfo.getFullPathById(id);
        if (!TextUtil.isEmpty(path)) {
            File f = new File(path);
            if (f.exists()) {
                return new ThriftFileInfo(f);
            }
        }
        return null;
    }

    public GenerateResult generate(List<String> ids, String lang) {
        StringBuffer error = new StringBuffer();
        if (ids != null && ids.size() > 0) {
            String fileId = UUIDGenerator.get();
            String outputFolderPath = Constants.GENERATE_FILE_OUTPUT + fileId;
            new File(outputFolderPath).mkdir();

            for (String id : ids) {
                String path = ThriftFileInfo.getFullPathById(id);
                String cmd;
                if (lang.equals(Constants.JAVA_WITH_PYTEST)) {
                    cmd = String.format(Constants.JAVA_WITH_PYTEST_CMD, path, outputFolderPath);
                } else {
                    cmd = String.format(Constants.GENERATE_CMD, outputFolderPath, lang, path);
                }

                ShellExecutor.ExecResult result = ShellExecutor.execCmd(cmd);
                if (result == null || !result.isSuccess()) {
                    if (result != null) {
                        error.append(result.getRetString());
                    }
                    return new GenerateResult(false, "", "thrift generate failed: " + error.toString());
                }
            }
            try {
                String zipFolderPath = Constants.GENERATE_ZIP_OUTPUT;
                if (!FileUtil.fileExist(zipFolderPath)) {
                    new File(zipFolderPath).mkdirs();
                }

                String outputFilePath = zipFolderPath + fileId + Constants.ZIP_SUFFIX;
                ZipUtil.zipFolder(outputFolderPath, outputFilePath);
                FileUtil.deleteDirectory(outputFolderPath);
                return new GenerateResult(true, fileId, "");
            } catch (IOException e) {
                error.append("thrift generate failed: " + e);
            }
        }
        return new GenerateResult(false, "", error.toString());
    }

    public byte[] downloadResultZipById(String id) {
        if (!TextUtil.isEmpty(id)) {
            File zipFile = new File(Constants.GENERATE_ZIP_OUTPUT + id + Constants.ZIP_SUFFIX);
            if (zipFile.exists()) {
                try {
                    byte[] fileBytes =  FileUtils.readFileToByteArray(zipFile);
                    FileUtil.deleteFile(zipFile.getAbsolutePath());
                    return fileBytes;
                } catch (IOException e) {
                    AppLog.e(e);
                }
            }
        }
        return null;
    }

    private List<ThriftFileInfo> getAllFilesInFolder(String folderPath, String filter) {
        File rootFolder = new File(folderPath);
        List<ThriftFileInfo> childInfos = new ThriftFileInfo(rootFolder).getChildren();
        List<ThriftFileInfo> resultInfos = new ArrayList<ThriftFileInfo>();
        if (childInfos != null && childInfos.size() > 0) {
            for (ThriftFileInfo info: childInfos) {
                if (TextUtil.isEmpty(filter)) {
                    resultInfos.add(info);
                } else {
                    if (!info.isDirectory() && info.getName().contains(filter)) {
                        resultInfos.add(info);
                    }
                }
            }
        }
        return resultInfos;
    }
}
