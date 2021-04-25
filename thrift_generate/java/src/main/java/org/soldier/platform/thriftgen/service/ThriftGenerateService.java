package org.soldier.platform.thriftgen.service;

import org.soldier.platform.thriftgen.model.GenerateResult;
import org.soldier.platform.thriftgen.model.ThriftFileInfo;

import java.util.List;

public interface ThriftGenerateService {

    List<ThriftFileInfo> getAllFilesInFolder(String folderId);

    List<ThriftFileInfo> searchFiles(String keyword);

    ThriftFileInfo getFile(String id);

    GenerateResult generate(List<String> ids, String lang);

    byte[] downloadResultZipById(String id);

}
