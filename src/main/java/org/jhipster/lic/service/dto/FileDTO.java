package org.jhipster.lic.service.dto;

import org.jhipster.lic.domain.File;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;
import java.time.Instant;

/**
 * Created by Madalin on 9/3/2018.
 */
public class FileDTO {

    private Long id;

    private String fileName;

    private String fileType;

    private String userCode;

    private String fileUrl;

    private Long directoryId;

    private Instant creationDate;

    private MultipartFile data;

    public FileDTO(){}

    public FileDTO(File file) {
        this.id = file.getId();
        this.fileName = file.getFileName();
        this.fileType = file.getFileType();
        this.userCode = file.getUserCode();
        this.fileUrl = file.getFileURL();
        this.directoryId = file.getDirectoryID();
        this.creationDate = file.getCreationDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Long getDirectoryId() {
        return directoryId;
    }

    public void setDirectoryId(Long directoryId) {
        this.directoryId = directoryId;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public MultipartFile getData() {
        return data;
    }

    public void setData(MultipartFile data) {
        this.data = data;
    }
}
