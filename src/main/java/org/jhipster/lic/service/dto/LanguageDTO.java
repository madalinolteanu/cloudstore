package org.jhipster.lic.service.dto;

import org.jhipster.lic.domain.Language;

/**
 * Created by Madalin on 9/8/2018.
 */
public class LanguageDTO {
    private Long id;

    private String languageName;

    private String languageCode;

    public LanguageDTO(Language language) {
        this.id = language.getId();
        this.languageName = language.getLanguageName();
        this.languageCode = language.getLanguageCode();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

        LanguageDTO that = (LanguageDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (languageName != null ? !languageName.equals(that.languageName) : that.languageName != null) return false;
        return languageCode != null ? languageCode.equals(that.languageCode) : that.languageCode == null;
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
        return "LanguageDTO{" +
            "id=" + id +
            ", languageName='" + languageName + '\'' +
            ", languageCode='" + languageCode + '\'' +
            '}';
    }
}
