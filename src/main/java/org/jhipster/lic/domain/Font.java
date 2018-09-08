package org.jhipster.lic.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Madalin on 9/3/2018.
 */

@Entity
@Table(name = "STORE_FONT_TABLE", schema = "LICENCE")
public class Font implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "FONT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fontId;

    @Column(name = "FONT_NAME")
    private String fontName;

    @Column(name = "FONT_CODE")
    private String fontCode;

    public Long getFontId() {
        return fontId;
    }

    public void setFontId(Long fontId) {
        this.fontId = fontId;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public String getFontCode() {
        return fontCode;
    }

    public void setFontCode(String fontCode) {
        this.fontCode = fontCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Font font = (Font) o;

        if (fontId != null ? !fontId.equals(font.fontId) : font.fontId != null) return false;
        if (fontName != null ? !fontName.equals(font.fontName) : font.fontName != null) return false;
        return fontCode != null ? fontCode.equals(font.fontCode) : font.fontCode == null;
    }

    @Override
    public int hashCode() {
        int result = fontId != null ? fontId.hashCode() : 0;
        result = 31 * result + (fontName != null ? fontName.hashCode() : 0);
        result = 31 * result + (fontCode != null ? fontCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Font{" +
            "fontId=" + fontId +
            ", fontName='" + fontName + '\'' +
            ", fontCode=" + fontCode +
            '}';
    }
}
