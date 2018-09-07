package org.jhipster.lic.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "STORE_DIRECTORY_TABLE", schema = "LICENCE")
public class Directory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "DIRECTORY_ID")
    private Long id;

    @Column(name = "USER_CODE")
    private String userCode;

    @Column(name = "DIRECTORY_NAME")
    private String directoryName;

    @Column(name = "DIRECTORY_URL")
    private String directoryUrl;

    @Column(name = "DIRECTORY_PARENT")
    private Long directoryParent;

    @Column(name = "CREATION_DATE")
    private Instant creationDate;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getDirectoryParent() {
        return directoryParent;
    }

    public void setDirectoryParent(Long directoryParent) {
        this.directoryParent = directoryParent;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Directory directory = (Directory) o;
        return !(directory.getId() == null || getId() == null) && Objects.equals(getId(), directory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Directory{" +
            "id=" + id +
            ", userCode='" + userCode + '\'' +
            ", directoryName='" + directoryName + '\'' +
            ", directoryUrl='" + directoryUrl + '\'' +
            ", directoryParent=" + directoryParent +
            '}';
    }
}
