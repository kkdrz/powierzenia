package pl.edu.pwr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pl.edu.pwr.domain.enumeration.SemesterType;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A EntrustmentPlan.
 */
@Entity
@Table(name = "entrustment_plan")
public class EntrustmentPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "academic_year")
    @NotNull(message = "Academic year is required")
    @Min(value = 0, message = "Academic year has to be a positive number")
    private Integer academicYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "semester_type")
    @NotNull(message = "Semester type is required")
    private SemesterType semesterType;

    @OneToMany(mappedBy = "entrustmentPlan")
    private Set<Entrustment> entrustments = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("entrustmentPlans")
    @NotNull(message = "Education plan is required")
    private EducationPlan educationPlan;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
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

    public EntrustmentPlan academicYear(Integer academicYear) {
        this.academicYear = academicYear;
        return this;
    }

    public SemesterType getSemesterType() {
        return semesterType;
    }

    public void setSemesterType(SemesterType semesterType) {
        this.semesterType = semesterType;
    }

    public EntrustmentPlan semesterType(SemesterType semesterType) {
        this.semesterType = semesterType;
        return this;
    }

    public Set<Entrustment> getEntrustments() {
        return entrustments;
    }

    public void setEntrustments(Set<Entrustment> entrustments) {
        this.entrustments = entrustments;
    }

    public EntrustmentPlan entrustments(Set<Entrustment> entrustments) {
        this.entrustments = entrustments;
        return this;
    }

    public EntrustmentPlan addEntrustments(Entrustment entrustment) {
        this.entrustments.add(entrustment);
        entrustment.setEntrustmentPlan(this);
        return this;
    }

    public EntrustmentPlan removeEntrustments(Entrustment entrustment) {
        this.entrustments.remove(entrustment);
        entrustment.setEntrustmentPlan(null);
        return this;
    }

    public EducationPlan getEducationPlan() {
        return educationPlan;
    }

    public void setEducationPlan(EducationPlan educationPlan) {
        this.educationPlan = educationPlan;
    }

    public EntrustmentPlan educationPlan(EducationPlan educationPlan) {
        this.educationPlan = educationPlan;
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EntrustmentPlan)) {
            return false;
        }
        return id != null && id.equals(((EntrustmentPlan) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EntrustmentPlan{" +
            "id=" + getId() +
            ", academicYear=" + getAcademicYear() +
            ", semesterType='" + getSemesterType() + "'" +
            "}";
    }
}
