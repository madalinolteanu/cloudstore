package org.jhipster.lic.service.dto;

import afu.org.checkerframework.checker.igj.qual.I;
import org.jhipster.lic.domain.Directory;

/**
 * Created by Madalin on 9/3/2018.
 */
public class DirectoryDTO {

    private Long id;

    private String directoryName;

    private String directoryUrl;

    private String userCode;

    private Long directoryParent;

    public DirectoryDTO(Directory directory) {
        this.id = directory.getId();
        this.directoryName = directory.getDirectoryName();
        this.directoryParent = directory.getDirectoryParent();
        this.userCode = directory.getUserCode();
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
}
