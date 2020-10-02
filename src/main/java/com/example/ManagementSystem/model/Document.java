package com.example.ManagementSystem.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
@ConfigurationProperties(prefix = "file")
@Table
public class Document {
    @PrimaryKey
    private UUID documentId;
    private UUID userid;
    private String fileName;
    private String documentType;
    private String documentFormat;
    private String uploadDir;

    public Document() {
    }

    public Document(UUID documentId, UUID userid, String fileName, String documentType, String documentFormat, String uploadDir) {
        this.documentId = documentId;
        this.userid = userid;
        this.fileName = fileName;
        this.documentType = documentType;
        this.documentFormat = documentFormat;
        this.uploadDir = uploadDir;
    }

    public UUID getDocumentId() {
        return documentId;
    }

    public void setDocumentId(UUID documentId) {
        this.documentId = documentId;
    }

    public UUID getUserid() {
        return userid;
    }

    public void setUserid(UUID userid) {
        this.userid = userid;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentFormat() {
        return documentFormat;
    }

    public void setDocumentFormat(String documentFormat) {
        this.documentFormat = documentFormat;
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    @Override
    public String toString() {
        return "Document{" +
                "documentId=" + documentId +
                ", userid=" + userid +
                ", fileName='" + fileName + '\'' +
                ", documentType='" + documentType + '\'' +
                ", documentFormat='" + documentFormat + '\'' +
                ", uploadDir='" + uploadDir + '\'' +
                '}';
    }
}
