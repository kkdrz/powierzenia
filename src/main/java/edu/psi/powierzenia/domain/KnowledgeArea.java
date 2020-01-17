package edu.psi.powierzenia.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A KnowledgeArea.
 */
@Entity
@Table(name = "knowledge_area")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class KnowledgeArea implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "type")
    private String type;

    @ManyToMany(mappedBy = "knowledgeAreas")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Teacher> teachersWithThisKnowledgeAreas = new HashSet<>();

    @ManyToMany(mappedBy = "tags")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Course> coursesWithThisKnowledgeAreas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public KnowledgeArea type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Teacher> getTeachersWithThisKnowledgeAreas() {
        return teachersWithThisKnowledgeAreas;
    }

    public KnowledgeArea teachersWithThisKnowledgeAreas(Set<Teacher> teachers) {
        this.teachersWithThisKnowledgeAreas = teachers;
        return this;
    }

    public KnowledgeArea addTeachersWithThisKnowledgeArea(Teacher teacher) {
        this.teachersWithThisKnowledgeAreas.add(teacher);
        teacher.getKnowledgeAreas().add(this);
        return this;
    }

    public KnowledgeArea removeTeachersWithThisKnowledgeArea(Teacher teacher) {
        this.teachersWithThisKnowledgeAreas.remove(teacher);
        teacher.getKnowledgeAreas().remove(this);
        return this;
    }

    public void setTeachersWithThisKnowledgeAreas(Set<Teacher> teachers) {
        this.teachersWithThisKnowledgeAreas = teachers;
    }

    public Set<Course> getCoursesWithThisKnowledgeAreas() {
        return coursesWithThisKnowledgeAreas;
    }

    public KnowledgeArea coursesWithThisKnowledgeAreas(Set<Course> courses) {
        this.coursesWithThisKnowledgeAreas = courses;
        return this;
    }

    public KnowledgeArea addCoursesWithThisKnowledgeArea(Course course) {
        this.coursesWithThisKnowledgeAreas.add(course);
        course.getTags().add(this);
        return this;
    }

    public KnowledgeArea removeCoursesWithThisKnowledgeArea(Course course) {
        this.coursesWithThisKnowledgeAreas.remove(course);
        course.getTags().remove(this);
        return this;
    }

    public void setCoursesWithThisKnowledgeAreas(Set<Course> courses) {
        this.coursesWithThisKnowledgeAreas = courses;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KnowledgeArea)) {
            return false;
        }
        return id != null && id.equals(((KnowledgeArea) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "KnowledgeArea{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            "}";
    }
}
