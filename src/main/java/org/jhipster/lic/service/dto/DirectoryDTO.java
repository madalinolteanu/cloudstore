package org.jhipster.lic.service.dto;

import afu.org.checkerframework.checker.igj.qual.I;
import org.jhipster.lic.domain.Directory;

import java.time.Instant;

/**
 * Created by Madalin on 9/3/2018.
 */
public class DirectoryDTO {

    private Long id;

    private String directoryName;

    private String directoryUrl;

    private String userCode;

    private Long directoryParent;

    private Instant creationDate;

    public DirectoryDTO(){}

    public DirectoryDTO(Directory directory) {
        this.id = directory.getId();
        this.directoryName = directory.getDirectoryName();
        this.directoryParent = directory.getDirectoryParent();
        this.userCode = directory.getUserCode();
        this.directoryUrl = directory.getDirectoryUrl();
        this.creationDate = directory.getCreationDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Long getDirectoryParent() {
        return directoryParent;
    }

    public void setDirectoryParent(Long directoryParent) {
        this.directoryParent = directoryParent;
    }

    public String getDirectoryUrl() {
        return directoryUrl;
    }

    public void setDirectoryUrl(String directoryUrl) {
        this.directoryUrl = directoryUrl;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }
}
