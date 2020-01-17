package edu.psi.powierzenia.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import edu.psi.powierzenia.domain.enumeration.Specialization;

import edu.psi.powierzenia.domain.enumeration.StudiesLevel;

import edu.psi.powierzenia.domain.enumeration.StudiesType;

/**
 * A EducationPlan.
 */
@Entity
@Table(name = "education_plan")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EducationPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "start_academic_year")
    private Integer startAcademicYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "specialization")
    private Specialization specialization;

    @Enumerated(EnumType.STRING)
    @Column(name = "studies_level")
    private StudiesLevel studiesLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "studies_type")
    private StudiesType studiesType;

    @OneToMany(mappedBy = "educationPlan")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EntrustmentPlan> entrustmentPlans = new HashSet<>();

    @OneToMany(mappedBy = "educationPlan")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Course> courses = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("educationPlans")
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

    public EducationPlan startAcademicYear(Integer startAcademicYear) {
        this.startAcademicYear = startAcademicYear;
        return this;
    }

    public void setStartAcademicYear(Integer startAcademicYear) {
        this.startAcademicYear = startAcademicYear;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public EducationPlan specialization(Specialization specialization) {
        this.specialization = specialization;
        return this;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public StudiesLevel getStudiesLevel() {
        return studiesLevel;
    }

    public EducationPlan studiesLevel(StudiesLevel studiesLevel) {
        this.studiesLevel = studiesLevel;
        return this;
    }

    public void setStudiesLevel(StudiesLevel studiesLevel) {
        this.studiesLevel = studiesLevel;
    }

    public StudiesType getStudiesType() {
        return studiesType;
    }

    public EducationPlan studiesType(StudiesType studiesType) {
        this.studiesType = studiesType;
        return this;
    }

    public void setStudiesType(StudiesType studiesType) {
        this.studiesType = studiesType;
    }

    public Set<EntrustmentPlan> getEntrustmentPlans() {
        return entrustmentPlans;
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

    public void setEntrustmentPlans(Set<EntrustmentPlan> entrustmentPlans) {
        this.entrustmentPlans = entrustmentPlans;
    }

    public Set<Course> getCourses() {
        return courses;
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

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public FieldOfStudy getFieldOfStudy() {
        return fieldOfStudy;
    }

    public EducationPlan fieldOfStudy(FieldOfStudy fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
        return this;
    }

    public void setFieldOfStudy(FieldOfStudy fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
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
