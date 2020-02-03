package pl.edu.pwr.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.edu.pwr.domain.enumeration.ClassFormType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ClassForm.
 */
@Entity
@Table(name = "class_form")
public class ClassForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    @NotNull(message = "Class form type is required")
    private ClassFormType type;

    @ManyToMany(mappedBy = "allowedClassForms")
    @JsonIgnore
    private Set<Teacher> teachersAllowedToTeachThisForms = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClassFormType getType() {
        return type;
    }

    public void setType(ClassFormType type) {
        this.type = type;
    }

    public ClassForm type(ClassFormType type) {
        this.type = type;
        return this;
    }

    public Set<Teacher> getTeachersAllowedToTeachThisForms() {
        return teachersAllowedToTeachThisForms;
    }

    public void setTeachersAllowedToTeachThisForms(Set<Teacher> teachers) {
        this.teachersAllowedToTeachThisForms = teachers;
    }

    public ClassForm teachersAllowedToTeachThisForms(Set<Teacher> teachers) {
        this.teachersAllowedToTeachThisForms = teachers;
        return this;
    }

    public ClassForm addTeachersAllowedToTeachThisForm(Teacher teacher) {
        this.teachersAllowedToTeachThisForms.add(teacher);
        teacher.getAllowedClassForms().add(this);
        return this;
    }

    public ClassForm removeTeachersAllowedToTeachThisForm(Teacher teacher) {
        this.teachersAllowedToTeachThisForms.remove(teacher);
        teacher.getAllowedClassForms().remove(this);
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClassForm)) {
            return false;
        }
        return id != null && id.equals(((ClassForm) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ClassForm{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            "}";
    }
}
