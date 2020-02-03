package pl.edu.pwr.service.dto;

import pl.edu.pwr.domain.enumeration.ClassFormType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link pl.edu.pwr.domain.CourseClass} entity.
 */
public class CourseClassDTO implements Serializable {

    private Long id;

    @NotNull(message = "Hours are required")
    private Integer hours;

    @NotNull(message = "Class form is required")
    private ClassFormType form;

    @NotNull(message = "Course is required")
    private Long courseId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public ClassFormType getForm() {
        return form;
    }

    public void setForm(ClassFormType form) {
        this.form = form;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CourseClassDTO courseClassDTO = (CourseClassDTO) o;
        if (courseClassDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), courseClassDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CourseClassDTO{" +
            "id=" + getId() +
            ", hours=" + getHours() +
            ", form='" + getForm() + "'" +
            ", courseId=" + getCourseId() +
            "}";
    }
}
