package org.soldier.platform.thriftgen.model;

public class GenerateResult {

    private boolean success;
    private String zipFileId;
    private String error;

    public GenerateResult(boolean success, String zipFileId, String error) {
        this.success = success;
        this.zipFileId = zipFileId;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getZipFileId() {
        return zipFileId;
    }

    public String getError() {
        return error;
    }
}
