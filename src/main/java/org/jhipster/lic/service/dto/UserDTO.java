package org.jhipster.lic.service.dto;

import org.jhipster.lic.config.Constants;

import org.jhipster.lic.domain.Settings;
import org.jhipster.lic.domain.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import javax.validation.constraints.*;
import java.time.Instant;

/**
 * A DTO representing a user, with his authorities.
 */
public class UserDTO {

    private Long id;

    @Size(max = 256)
    private String token;

    @Size(max = 128)
    private String userCode;

    @Size(max = 128)
    private String userType;

    @Size(max = 128)
    private String username;

    @NotBlank
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 8, max = 128)
    private String password;

    @Size(max = 128)
    private String firstName;

    @Size(max = 128)
    private String lastName;

    @Email
    @Size(min = 5, max = 128)
    private String email;

    @Size(max = 256)
    private String imageUrl;

    private boolean active = false;

    @Size(max = 128)
    private String activationKey;

    private Instant creationDate;

    private Instant resetDate;

    @Size(max = 128)
    private String resetKey;

    private Settings settings;

    public UserDTO() {
        // Empty constructor needed for Jackson.
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.token = user.getToken();
        this.userCode = null;
        this.userType = null;
        this.username = null;
        this.password = null;
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.active = user.isActive();
        this.activationKey = null;
        this.imageUrl = user.getImageUrl();
        this.creationDate = null;
        this.resetDate = null;
        this.resetKey = null;
//        this.settings = user.getSettings();
    }

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getResetDate() {
        return resetDate;
    }

    public void setResetDate(Instant resetDate) {
        this.resetDate = resetDate;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDTO userDTO = (UserDTO) o;

        if (active != userDTO.active) return false;
        if (id != null ? !id.equals(userDTO.id) : userDTO.id != null) return false;
        if (token != null ? !token.equals(userDTO.token) : userDTO.token != null) return false;
        if (userCode != null ? !userCode.equals(userDTO.userCode) : userDTO.userCode != null) return false;
        if (userType != null ? !userType.equals(userDTO.userType) : userDTO.userType != null) return false;
        if (username != null ? !username.equals(userDTO.username) : userDTO.username != null) return false;
        if (password != null ? !password.equals(userDTO.password) : userDTO.password != null) return false;
        if (firstName != null ? !firstName.equals(userDTO.firstName) : userDTO.firstName != null) return false;
        if (lastName != null ? !lastName.equals(userDTO.lastName) : userDTO.lastName != null) return false;
        if (email != null ? !email.equals(userDTO.email) : userDTO.email != null) return false;
        if (imageUrl != null ? !imageUrl.equals(userDTO.imageUrl) : userDTO.imageUrl != null) return false;
        if (activationKey != null ? !activationKey.equals(userDTO.activationKey) : userDTO.activationKey != null)
            return false;
        if (creationDate != null ? !creationDate.equals(userDTO.creationDate) : userDTO.creationDate != null)
            return false;
        if (resetDate != null ? !resetDate.equals(userDTO.resetDate) : userDTO.resetDate != null) return false;
        if (resetKey != null ? !resetKey.equals(userDTO.resetKey) : userDTO.resetKey != null) return false;
        return settings != null ? settings.equals(userDTO.settings) : userDTO.settings == null;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
            "userId=" + id +
            ", token='" + token + '\'' +
            ", userCode='" + userCode + '\'' +
            ", userType='" + userType + '\'' +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", active=" + active +
            ", activationKey='" + activationKey + '\'' +
            ", creationDate=" + creationDate +
            ", resetDate=" + resetDate +
            ", resetKey='" + resetKey + '\'' +
            '}';
    }
}
