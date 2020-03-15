package com.indracompany.refis.contracts.model.vo;

import java.io.File;

public class DocumentFileVo {

    private String originalFileName;
    private long size;
    private String contentType;
    private File tempFile;

    public DocumentFileVo() {
    }

    public DocumentFileVo(String originalFileName, long size, String contentType, File tempFile) {
        this.originalFileName = originalFileName;
        this.size = size;
        this.contentType = contentType;
        this.tempFile = tempFile;
    }

    public String getOriginalFileName() {
        return this.originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public File getTempFile() {
        return this.tempFile;
    }

    public void setTempFile(File tempFile) {
        this.tempFile = tempFile;
    }
}
