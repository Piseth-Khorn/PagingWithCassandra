package com.example.ManagementSystem.core;

public class UploadFileResponse {
    private String fileName;
    private String fileDownloadUrl;
    private String fileType;
    private Long size;

    public UploadFileResponse(String fileName, String fileDownloadUrl, String fileType, Long size) {
        this.fileName = fileName;
        this.fileDownloadUrl = fileDownloadUrl;
        this.fileType = fileType;
        this.size = size;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDownloadUrl() {
        return fileDownloadUrl;
    }

    public void setFileDownloadUrl(String fileDownloadUrl) {
        this.fileDownloadUrl = fileDownloadUrl;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "UploadFileResponse{" +
                "fileName='" + fileName + '\'' +
                ", fileDownloadUrl='" + fileDownloadUrl + '\'' +
                ", fileType='" + fileType + '\'' +
                ", size=" + size +
                '}';
    }
}
