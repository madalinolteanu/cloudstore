package org.jhipster.lic.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Madalin on 9/3/2018.
 */
@Entity
@Table(name = "STORE_LANGUAGE_TABLE", schema = "LICENCE")
public class Language implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "LANGUAGE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "LANGUAGE_NAME")
    private String languageName;

    @Column(name = "LANGUAGE_CODE")
    private String languageCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Language language = (Language) o;

        if (id != null ? !id.equals(language.id) : language.id != null) return false;
        if (languageName != null ? !languageName.equals(language.languageName) : language.languageName != null)
            return false;
        return languageCode != null ? languageCode.equals(language.languageCode) : language.languageCode == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (languageName != null ? languageName.hashCode() : 0);
        result = 31 * result + (languageCode != null ? languageCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Language{" +
            "languageId=" + id +
            ", languageName='" + languageName + '\'' +
            ", languageCode=" + languageCode +
            '}';
    }
}
