package pl.edu.pwr.service.dto;

import pl.edu.pwr.domain.enumeration.SemesterType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link pl.edu.pwr.domain.EntrustmentPlan} entity.
 */
public class EntrustmentPlanDTO implements Serializable {

    private Long id;

    @NotNull(message = "Academic year is required")
    private Integer academicYear;

    @NotNull(message = "Semester type is required")
    private SemesterType semesterType;

    @NotNull(message = "Education plan is required")
    private Long educationPlanId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(Integer academicYear) {
        this.academicYear = academicYear;
    }

    public SemesterType getSemesterType() {
        return semesterType;
    }

    public void setSemesterType(SemesterType semesterType) {
        this.semesterType = semesterType;
    }

    public Long getEducationPlanId() {
        return educationPlanId;
    }

    public void setEducationPlanId(Long educationPlanId) {
        this.educationPlanId = educationPlanId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EntrustmentPlanDTO entrustmentPlanDTO = (EntrustmentPlanDTO) o;
        if (entrustmentPlanDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entrustmentPlanDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EntrustmentPlanDTO{" +
            "id=" + getId() +
            ", academicYear=" + getAcademicYear() +
            ", semesterType='" + getSemesterType() + "'" +
            ", educationPlanId=" + getEducationPlanId() +
            "}";
    }
}
