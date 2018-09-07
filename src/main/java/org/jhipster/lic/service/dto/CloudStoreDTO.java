package org.jhipster.lic.service.dto;

import java.util.List;

/**
 * Created by Madalin on 9/4/2018.
 */
public class CloudStoreDTO {
    private String errorMessage;
    private String successMessage;
    private Integer errorCode;
    private Integer successCode;
    private UserDTO userDTO;
    private List<DirectoryDTO> directories;
    private List<FileDTO> files;

    public CloudStoreDTO(){}

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public Integer getSuccessCode() {
        return successCode;
    }

    public void setSuccessCode(Integer successCode) {
        this.successCode = successCode;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public List<DirectoryDTO> getDirectories() {
        return directories;
    }

    public void setDirectories(List<DirectoryDTO> directories) {
        this.directories = directories;
    }

    public List<FileDTO> getFiles() {
        return files;
    }

    public void setFiles(List<FileDTO> files) {
        this.files = files;
    }
}
