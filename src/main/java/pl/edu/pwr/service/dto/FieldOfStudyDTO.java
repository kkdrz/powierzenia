package pl.edu.pwr.service.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link pl.edu.pwr.domain.FieldOfStudy} entity.
 */
public class FieldOfStudyDTO implements Serializable {

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FieldOfStudyDTO fieldOfStudyDTO = (FieldOfStudyDTO) o;
        if (fieldOfStudyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fieldOfStudyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FieldOfStudyDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
