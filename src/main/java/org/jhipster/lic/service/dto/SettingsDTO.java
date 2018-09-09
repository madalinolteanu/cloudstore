package org.jhipster.lic.service.dto;

import org.jhipster.lic.domain.Font;
import org.jhipster.lic.domain.Language;
import org.jhipster.lic.domain.Settings;
import org.jhipster.lic.domain.Theme;

import javax.persistence.*;

/**
 * Created by Madalin on 9/3/2018.
 */
public class SettingsDTO {
    private Long id;

    private String userCode;

    private String language;

    private String theme;

    private String dateFormat;

    private String fontType;

    public SettingsDTO(){}

    public SettingsDTO(Settings settings) {
        this.id = settings.getId();
        this.userCode = settings.getUserCode();
        this.language = settings.getLanguage();
        this.theme = settings.getTheme();
        this.dateFormat = settings.getDateFormat();
        this.fontType = settings.getFontType();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getFontType() {
        return fontType;
    }

    public void setFontType(String fontType) {
        this.fontType = fontType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SettingsDTO that = (SettingsDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (userCode != null ? !userCode.equals(that.userCode) : that.userCode != null) return false;
        if (language != null ? !language.equals(that.language) : that.language != null) return false;
        if (theme != null ? !theme.equals(that.theme) : that.theme != null) return false;
        if (dateFormat != null ? !dateFormat.equals(that.dateFormat) : that.dateFormat != null) return false;
        return fontType != null ? fontType.equals(that.fontType) : that.fontType == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userCode != null ? userCode.hashCode() : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (theme != null ? theme.hashCode() : 0);
        result = 31 * result + (dateFormat != null ? dateFormat.hashCode() : 0);
        result = 31 * result + (fontType != null ? fontType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SettingsDTO{" +
            "id=" + id +
            ", userCode='" + userCode + '\'' +
            ", language=" + language +
            ", theme=" + theme +
            ", dateFormat='" + dateFormat + '\'' +
            ", fontType=" + fontType +
            '}';
    }
}
