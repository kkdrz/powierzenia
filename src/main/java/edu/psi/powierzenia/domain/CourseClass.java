package edu.psi.powierzenia.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import edu.psi.powierzenia.domain.enumeration.ClassFormType;

/**
 * A CourseClass.
 */
@Entity
@Table(name = "course_class")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CourseClass implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "hours")
    private Integer hours;

    @Enumerated(EnumType.STRING)
    @Column(name = "form")
    private ClassFormType form;

    @OneToMany(mappedBy = "courseClass")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Entrustment> entrustments = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("classes")
    private Course course;

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

    public CourseClass hours(Integer hours) {
        this.hours = hours;
        return this;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public ClassFormType getForm() {
        return form;
    }

    public CourseClass form(ClassFormType form) {
        this.form = form;
        return this;
    }

    public void setForm(ClassFormType form) {
        this.form = form;
    }

    public Set<Entrustment> getEntrustments() {
        return entrustments;
    }

    public CourseClass entrustments(Set<Entrustment> entrustments) {
        this.entrustments = entrustments;
        return this;
    }

    public CourseClass addEntrustments(Entrustment entrustment) {
        this.entrustments.add(entrustment);
        entrustment.setCourseClass(this);
        return this;
    }

    public CourseClass removeEntrustments(Entrustment entrustment) {
        this.entrustments.remove(entrustment);
        entrustment.setCourseClass(null);
        return this;
    }

    public void setEntrustments(Set<Entrustment> entrustments) {
        this.entrustments = entrustments;
    }

    public Course getCourse() {
        return course;
    }

    public CourseClass course(Course course) {
        this.course = course;
        return this;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseClass)) {
            return false;
        }
        return id != null && id.equals(((CourseClass) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CourseClass{" +
            "id=" + getId() +
            ", hours=" + getHours() +
            ", form='" + getForm() + "'" +
            "}";
    }
}
