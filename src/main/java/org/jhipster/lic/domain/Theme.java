package org.jhipster.lic.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Madalin on 9/3/2018.
 */
@Entity
@Table(name = "STORE_THEME_TABLE", schema = "LICENCE")
public class Theme implements Serializable {


    @Id
    @Column(name = "THEME_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "THEME_NAME")
    private String themeName;

    @Column(name = "THEME_CODE")
    private String themeCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getThemeCode() {
        return themeCode;
    }

    public void setThemeCode(String themeCode) {
        this.themeCode = themeCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Theme theme = (Theme) o;

        if (id != null ? !id.equals(theme.id) : theme.id != null) return false;
        if (themeName != null ? !themeName.equals(theme.themeName) : theme.themeName != null) return false;
        return themeCode != null ? themeCode.equals(theme.themeCode) : theme.themeCode == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (themeName != null ? themeName.hashCode() : 0);
        result = 31 * result + (themeCode != null ? themeCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Theme{" +
            "themeId=" + id +
            ", themeName='" + themeName + '\'' +
            ", themeCode=" + themeCode +
            '}';
    }
}
