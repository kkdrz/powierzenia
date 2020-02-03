package pl.edu.pwr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pl.edu.pwr.domain.enumeration.Specialization;
import pl.edu.pwr.domain.enumeration.StudiesLevel;
import pl.edu.pwr.domain.enumeration.StudiesType;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A EducationPlan.
 */
@Entity
@Table(name = "education_plan")
public class EducationPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "start_academic_year")
    @NotNull(message = "Start academic year is required")
    @Min(value = 0, message = "Start academic year has to be a positive number")
    private Integer startAcademicYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "specialization")
    @NotNull(message = "Specialization is required")
    private Specialization specialization;

    @Enumerated(EnumType.STRING)
    @Column(name = "studies_level")
    @NotNull(message = "Studies level is required")
    private StudiesLevel studiesLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "studies_type")
    @NotNull(message = "Studies type is required")
    private StudiesType studiesType;

    @OneToMany(mappedBy = "educationPlan")
    private Set<EntrustmentPlan> entrustmentPlans = new HashSet<>();

    @OneToMany(mappedBy = "educationPlan")
    private Set<Course> courses = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("educationPlans")
    @NotNull(message = "Field of study is required")
    private FieldOfStudy fieldOfStudy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
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

    public EducationPlan startAcademicYear(Integer startAcademicYear) {
        this.startAcademicYear = startAcademicYear;
        return this;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public EducationPlan specialization(Specialization specialization) {
        this.specialization = specialization;
        return this;
    }

    public StudiesLevel getStudiesLevel() {
        return studiesLevel;
    }

    public void setStudiesLevel(StudiesLevel studiesLevel) {
        this.studiesLevel = studiesLevel;
    }

    public EducationPlan studiesLevel(StudiesLevel studiesLevel) {
        this.studiesLevel = studiesLevel;
        return this;
    }

    public StudiesType getStudiesType() {
        return studiesType;
    }

    public void setStudiesType(StudiesType studiesType) {
        this.studiesType = studiesType;
    }

    public EducationPlan studiesType(StudiesType studiesType) {
        this.studiesType = studiesType;
        return this;
    }

    public Set<EntrustmentPlan> getEntrustmentPlans() {
        return entrustmentPlans;
    }

    public void setEntrustmentPlans(Set<EntrustmentPlan> entrustmentPlans) {
        this.entrustmentPlans = entrustmentPlans;
    }

    public EducationPlan entrustmentPlans(Set<EntrustmentPlan> entrustmentPlans) {
        this.entrustmentPlans = entrustmentPlans;
        return this;
    }

    public EducationPlan addEntrustmentPlans(EntrustmentPlan entrustmentPlan) {
        this.entrustmentPlans.add(entrustmentPlan);
        entrustmentPlan.setEducationPlan(this);
        return this;
    }

    public EducationPlan removeEntrustmentPlans(EntrustmentPlan entrustmentPlan) {
        this.entrustmentPlans.remove(entrustmentPlan);
        entrustmentPlan.setEducationPlan(null);
        return this;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public EducationPlan courses(Set<Course> courses) {
        this.courses = courses;
        return this;
    }

    public EducationPlan addCourses(Course course) {
        this.courses.add(course);
        course.setEducationPlan(this);
        return this;
    }

    public EducationPlan removeCourses(Course course) {
        this.courses.remove(course);
        course.setEducationPlan(null);
        return this;
    }

    public FieldOfStudy getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(FieldOfStudy fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public EducationPlan fieldOfStudy(FieldOfStudy fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EducationPlan)) {
            return false;
        }
        return id != null && id.equals(((EducationPlan) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EducationPlan{" +
            "id=" + getId() +
            ", startAcademicYear=" + getStartAcademicYear() +
            ", specialization='" + getSpecialization() + "'" +
            ", studiesLevel='" + getStudiesLevel() + "'" +
            ", studiesType='" + getStudiesType() + "'" +
            "}";
    }
}
