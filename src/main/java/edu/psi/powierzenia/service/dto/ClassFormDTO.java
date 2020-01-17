package edu.psi.powierzenia.service.dto;
import java.io.Serializable;
import java.util.Objects;
import edu.psi.powierzenia.domain.enumeration.ClassFormType;

/**
 * A DTO for the {@link edu.psi.powierzenia.domain.ClassForm} entity.
 */
public class ClassFormDTO implements Serializable {

    private Long id;

    private ClassFormType type;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClassFormType getType() {
        return type;
    }

    public void setType(ClassFormType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClassFormDTO classFormDTO = (ClassFormDTO) o;
        if (classFormDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), classFormDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClassFormDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            "}";
    }
}
