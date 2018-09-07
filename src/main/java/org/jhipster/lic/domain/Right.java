package org.jhipster.lic.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "STORE_RIGHT_TABLE", schema = "LICENCE")
public class Right implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "RIGHT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "RIGHT_NAME")
    private String rightName;

    @Column(name = "RIGHT_CODE")
    private Integer rightCode;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRightName() {
        return rightName;
    }

    public Integer getRightCode() {
        return rightCode;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    public void setRightCode(Integer rightCode) {
        this.rightCode = rightCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Right right = (Right) o;
        return !(right.getId() == null || getId() == null) && Objects.equals(getId(), right.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Right{" +
            "rightId=" + id +
            ", rightName='" + rightName + '\'' +
            ", rightCode=" + rightCode +
            '}';
    }
}
