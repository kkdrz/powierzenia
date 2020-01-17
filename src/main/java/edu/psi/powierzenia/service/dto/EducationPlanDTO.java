package edu.psi.powierzenia.service.dto;
import java.io.Serializable;
import java.util.Objects;
import edu.psi.powierzenia.domain.enumeration.Specialization;
import edu.psi.powierzenia.domain.enumeration.StudiesLevel;
import edu.psi.powierzenia.domain.enumeration.StudiesType;

/**
 * A DTO for the {@link edu.psi.powierzenia.domain.EducationPlan} entity.
 */
public class EducationPlanDTO implements Serializable {

    private Long id;

    private Integer startAcademicYear;

    private Specialization specialization;

    private StudiesLevel studiesLevel;

    private StudiesType studiesType;


    private Long fieldOfStudyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStartAcademicYear() {
        return startAcademicYear;
    }

    public void setStartAcademicYear(Integer startAcademicYear) {
        this.startAcademicYear = startAcademicYear;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public StudiesLevel getStudiesLevel() {
        return studiesLevel;
    }

    public void setStudiesLevel(StudiesLevel studiesLevel) {
        this.studiesLevel = studiesLevel;
    }

    public StudiesType getStudiesType() {
        return studiesType;
    }

    public void setStudiesType(StudiesType studiesType) {
        this.studiesType = studiesType;
    }

    public Long getFieldOfStudyId() {
        return fieldOfStudyId;
    }

    public void setFieldOfStudyId(Long fieldOfStudyId) {
        this.fieldOfStudyId = fieldOfStudyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EducationPlanDTO educationPlanDTO = (EducationPlanDTO) o;
        if (educationPlanDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), educationPlanDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EducationPlanDTO{" +
            "id=" + getId() +
            ", startAcademicYear=" + getStartAcademicYear() +
            ", specialization='" + getSpecialization() + "'" +
            ", studiesLevel='" + getStudiesLevel() + "'" +
            ", studiesType='" + getStudiesType() + "'" +
            ", fieldOfStudyId=" + getFieldOfStudyId() +
            "}";
    }
}
