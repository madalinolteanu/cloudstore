package org.jhipster.lic.domain;

import javax.persistence.*;

/**
 * Created by Madalin on 9/3/2018.
 */

@Entity
@Table(name = "STORE_SETTINGS_TABLE", schema = "LICENCE")
public class Settings {
    private static final long serialVersionUID = 1L;


    @Column(name = "SETTING_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Id
    @Column(name = "USER_CODE")
    private String userCode;

    @OneToOne
    @JoinColumn(name = "LANGUAGE")
    private Language language;

    @OneToOne
    @JoinColumn(name = "FONT_TYPE")
    private Theme theme;

    @Column(name = "DATE_FORMAT")
    private String dateFormat;

    @OneToOne
    @JoinTable(name= "FONT_CODE")
    private Font fontType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public Font getFontType() {
        return fontType;
    }

    public void setFontType(Font fontType) {
        this.fontType = fontType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Settings settings = (Settings) o;

        if (id != null ? !id.equals(settings.id) : settings.id != null) return false;
        if (userCode != null ? !userCode.equals(settings.userCode) : settings.userCode != null) return false;
        if (language != null ? !language.equals(settings.language) : settings.language != null) return false;
        if (theme != null ? !theme.equals(settings.theme) : settings.theme != null) return false;
        if (dateFormat != null ? !dateFormat.equals(settings.dateFormat) : settings.dateFormat != null) return false;
        return fontType != null ? fontType.equals(settings.fontType) : settings.fontType == null;
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
        return "Settings{" +
            "settingId=" + id +
            ", userCode='" + userCode + '\'' +
            ", language='" + language + '\'' +
            ", theme='" + theme + '\'' +
            ", dateFormat=" + dateFormat +
            ", fontType=" + fontType +
            '}';
    }
}
