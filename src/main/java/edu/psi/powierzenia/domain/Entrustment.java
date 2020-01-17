package edu.psi.powierzenia.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Entrustment.
 */
@Entity
@Table(name = "entrustment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Entrustment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "hours")
    private Integer hours;

    @Column(name = "hours_multiplier")
    private Float hoursMultiplier;

    @ManyToOne
    @JsonIgnoreProperties("entrustments")
    private EntrustmentPlan entrustmentPlan;

    @ManyToOne
    @JsonIgnoreProperties("entrustments")
    private CourseClass courseClass;

    @ManyToOne
    @JsonIgnoreProperties("entrustments")
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

    public Entrustment hours(Integer hours) {
        this.hours = hours;
        return this;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Float getHoursMultiplier() {
        return hoursMultiplier;
    }

    public Entrustment hoursMultiplier(Float hoursMultiplier) {
        this.hoursMultiplier = hoursMultiplier;
        return this;
    }

    public void setHoursMultiplier(Float hoursMultiplier) {
        this.hoursMultiplier = hoursMultiplier;
    }

    public EntrustmentPlan getEntrustmentPlan() {
        return entrustmentPlan;
    }

    public Entrustment entrustmentPlan(EntrustmentPlan entrustmentPlan) {
        this.entrustmentPlan = entrustmentPlan;
        return this;
    }

    public void setEntrustmentPlan(EntrustmentPlan entrustmentPlan) {
        this.entrustmentPlan = entrustmentPlan;
    }

    public CourseClass getCourseClass() {
        return courseClass;
    }

    public Entrustment courseClass(CourseClass courseClass) {
        this.courseClass = courseClass;
        return this;
    }

    public void setCourseClass(CourseClass courseClass) {
        this.courseClass = courseClass;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Entrustment teacher(Teacher teacher) {
        this.teacher = teacher;
        return this;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
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
