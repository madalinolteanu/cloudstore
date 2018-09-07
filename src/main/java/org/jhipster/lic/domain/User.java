package org.jhipster.lic.domain;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.*;

/**
 * A user.
 */
@Entity
@Table(name = "STORE_USER_TABLE", schema = "LICENCE")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;


    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TOKEN")
    private String token;

    @Id
    @Column(name = "USER_CODE")
    private String userCode;

    @Column(name = "USER_TYPE")
    private String userType;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "ACTIVE")
    private boolean active = false;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @Column(name = "ACTIVATION_KEY")
    private String activationKey;

    @Column(name = "RESET_KEY")
    private String resetKey;

    @Column(name = "RESET_DATE")
    private Instant resetDate = null;

    @Column(name = "CREATION_DATE")
    private Instant creationDate = null;

//    @OneToOne()
//    @JoinTable(name= "USER_CODE")
//    private Settings settings;
//
//    @OneToMany(cascade= CascadeType.ALL)
//    @JoinTable(name= "USER_CODE")
//    private List<Directory> directories;
//
//    @OneToMany(cascade= CascadeType.ALL)
//    @JoinTable(name= "USER_CODE")
//    private List<File> files;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public Instant getResetDate() {
        return resetDate;
    }

    public void setResetDate(Instant resetDate) {
        this.resetDate = resetDate;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

//    public Settings getSettings() {
//        return settings;
//    }
//
//    public void setSettings(Settings settings) {
//        this.settings = settings;
//    }
//
//    public List<Directory> getDirectories() {
//        return directories;
//    }
//
//    public void setDirectories(List<Directory> directories) {
//        this.directories = directories;
//    }
//
//    public List<File> getFiles() {
//        return files;
//    }
//
//    public void setFiles(List<File> files) {
//        this.files = files;
//    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", token='" + token + '\'' +
            ", userCode='" + userCode + '\'' +
            ", userType='" + userType + '\'' +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", active=" + active +
            ", imageUrl='" + imageUrl + '\'' +
            ", activationKey='" + activationKey + '\'' +
            ", resetKey='" + resetKey + '\'' +
            ", resetDate=" + resetDate +
            ", creationDate=" + creationDate ;
//            + ", settings=" + settings +
//            ", directories=" + directories +
//            ", files=" + files +
//            '}';
    }
}
