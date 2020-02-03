package pl.edu.pwr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pl.edu.pwr.domain.validators.Entrustment.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A Entrustment.
 */
@Entity
@Table(name = "entrustment")
@TeacherCantExceedHisHourLimitForAcademicYear
@AcademicOrDoctorantCantExceedHisPensumIfNotAgreedTo
@TeacherEntrustedHoursCanExceedPensumIfEveryOtherTeacherHasReachedHisPensum
@CanEntrustClassToTeacherOnlyOnce
@SumEntrustmentsHoursCantExceedCourseClassHours
public class Entrustment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "hours")
    @NotNull(message = "Hours are required")
    @Min(value = 0, message = "Hours have to be a positive number")
    private Integer hours;

    @Column(name = "hours_multiplier")
    @NotNull(message = "Hours multiplier is required")
    @Min(value = 0, message = "Hours multipliter has to be a positive number")
    private Float hoursMultiplier;

    @ManyToOne
    @JsonIgnoreProperties("entrustments")
    @NotNull(message = "Entrustment plan is required")
    private EntrustmentPlan entrustmentPlan;

    @ManyToOne
    @JsonIgnoreProperties("entrustments")
    @NotNull(message = "Course class is required")
    private CourseClass courseClass;

    @ManyToOne
    @JsonIgnoreProperties("entrustments")
    @NotNull(message = "Teacher is required")
    private Teacher teacher;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
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

    public Entrustment hours(Integer hours) {
        this.hours = hours;
        return this;
    }

    public Float getHoursMultiplier() {
        return hoursMultiplier;
    }

    public void setHoursMultiplier(Float hoursMultiplier) {
        this.hoursMultiplier = hoursMultiplier;
    }

    public Entrustment hoursMultiplier(Float hoursMultiplier) {
        this.hoursMultiplier = hoursMultiplier;
        return this;
    }

    public EntrustmentPlan getEntrustmentPlan() {
        return entrustmentPlan;
    }

    public void setEntrustmentPlan(EntrustmentPlan entrustmentPlan) {
        this.entrustmentPlan = entrustmentPlan;
    }

    public Entrustment entrustmentPlan(EntrustmentPlan entrustmentPlan) {
        this.entrustmentPlan = entrustmentPlan;
        return this;
    }

    public CourseClass getCourseClass() {
        return courseClass;
    }

    public void setCourseClass(CourseClass courseClass) {
        this.courseClass = courseClass;
    }

    public Entrustment courseClass(CourseClass courseClass) {
        this.courseClass = courseClass;
        return this;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Entrustment teacher(Teacher teacher) {
        this.teacher = teacher;
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Entrustment)) {
            return false;
        }
        return id != null && id.equals(((Entrustment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Entrustment{" +
            "id=" + getId() +
            ", hours=" + getHours() +
            ", hoursMultiplier=" + getHoursMultiplier() +
            "}";
    }
}
