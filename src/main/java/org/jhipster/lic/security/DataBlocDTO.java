package org.jhipster.lic.security;

import org.jhipster.lic.domain.Directory;
import org.jhipster.lic.domain.File;
import org.jhipster.lic.domain.User;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class DataBlocDTO {

    private List<Directory> directories;

    private List<File> files;

    public List<Directory> getDirectories() {
        return directories;
    }

    public void setDirectories(List<Directory> directories) {
        this.directories = directories;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }
}
