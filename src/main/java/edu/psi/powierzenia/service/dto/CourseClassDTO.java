package edu.psi.powierzenia.service.dto;
import java.io.Serializable;
import java.util.Objects;
import edu.psi.powierzenia.domain.enumeration.ClassFormType;

/**
 * A DTO for the {@link edu.psi.powierzenia.domain.CourseClass} entity.
 */
public class CourseClassDTO implements Serializable {

    private Long id;

    private Integer hours;

    private ClassFormType form;


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
