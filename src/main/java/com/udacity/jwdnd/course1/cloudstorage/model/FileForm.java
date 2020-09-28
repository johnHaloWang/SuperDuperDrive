package com.udacity.jwdnd.course1.cloudstorage.model;

import org.springframework.web.multipart.MultipartFile;

public class FileForm {
    private MultipartFile fileEntity;
    private String fileId;
    public FileForm(MultipartFile fileEntity) {
        this.fileEntity = fileEntity;
    }

    public FileForm(MultipartFile fileEntity, String fileId) {
        this.fileEntity = fileEntity;
        this.fileId = fileId;
    }

    public FileForm() {
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public MultipartFile getFileEntity() {
        return fileEntity;
    }

    public void setFileEntity(MultipartFile fileEntity) {
        this.fileEntity = fileEntity;
    }
}
