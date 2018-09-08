package org.jhipster.lic.service.dto;

import org.jhipster.lic.domain.Right;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Madalin on 9/3/2018.
 */
public class RightDTO {
    private Long id;

    private String rightName;

    private String rightCode;

    public RightDTO(Right right) {
        this.id = right.getId();
        this.rightName = right.getRightName();
        this.rightCode = right.getRightCode();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRightName() {
        return rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    public String getRightCode() {
        return rightCode;
    }

    public void setRightCode(String rightCode) {
        this.rightCode = rightCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RightDTO rightDTO = (RightDTO) o;

        if (id != null ? !id.equals(rightDTO.id) : rightDTO.id != null) return false;
        if (rightName != null ? !rightName.equals(rightDTO.rightName) : rightDTO.rightName != null) return false;
        return rightCode != null ? rightCode.equals(rightDTO.rightCode) : rightDTO.rightCode == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (rightName != null ? rightName.hashCode() : 0);
        result = 31 * result + (rightCode != null ? rightCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RightDTO{" +
            "id=" + id +
            ", rightName='" + rightName + '\'' +
            ", rightCode=" + rightCode +
            '}';
    }
}
