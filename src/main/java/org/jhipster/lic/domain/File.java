package org.jhipster.lic.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "STORE_FILE_TABLE", schema = "LICENCE")
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "FILE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_CODE")
    private String userCode;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "FILE_URL")
    private String fileURL;

    @Column(name = "FILE_TYPE")
    private String fileType;

    @Column(name = "DIRECTORY_ID")
    private Long directoryID;

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

    public String getFileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Long getDirectoryID() {
        return directoryID;
    }

    public void setDirectoryID(Long directoryID) {
        this.directoryID = directoryID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        File file = (File) o;
        return !(file.getId() == null || getId() == null) && Objects.equals(getId(), file.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FileRepository{" +
            "fileName='" + fileName + '\'' +
            "}";
    }
}
