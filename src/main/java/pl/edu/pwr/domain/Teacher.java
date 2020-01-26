package pl.edu.pwr.domain;

import pl.edu.pwr.domain.enumeration.TeacherType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Teacher.
 */
@Entity
@Table(name = "teacher")
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "external_user_id")
    private String externalUserId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "hour_limit")
    private Integer hourLimit;

    @Column(name = "pensum")
    private Integer pensum;

    @Column(name = "agreed_to_additional_pensum")
    private Boolean agreedToAdditionalPensum;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TeacherType type;

    @OneToMany(mappedBy = "teacher")
    private Set<Entrustment> entrustments = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "teacher_allowed_class_forms",
        joinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "allowed_class_forms_id", referencedColumnName = "id"))
    private Set<ClassForm> allowedClassForms = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "teacher_knowledge_areas",
        joinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "knowledge_areas_id", referencedColumnName = "id"))
    private Set<KnowledgeArea> knowledgeAreas = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "teacher_prefered_courses",
        joinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "prefered_courses_id", referencedColumnName = "id"))
    private Set<Course> preferedCourses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExternalUserId() {
        return externalUserId;
    }

    public void setExternalUserId(String externalUserId) {
        this.externalUserId = externalUserId;
    }

    public Teacher externalUserId(String externalUserId) {
        this.externalUserId = externalUserId;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Teacher firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Teacher lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public Teacher email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getHourLimit() {
        return hourLimit;
    }

    public Teacher hourLimit(Integer hourLimit) {
        this.hourLimit = hourLimit;
        return this;
    }

    public void setHourLimit(Integer hourLimit) {
        this.hourLimit = hourLimit;
    }

    public Integer getPensum() {
        return pensum;
    }

    public Teacher pensum(Integer pensum) {
        this.pensum = pensum;
        return this;
    }

    public void setPensum(Integer pensum) {
        this.pensum = pensum;
    }

    public Boolean isAgreedToAdditionalPensum() {
        return agreedToAdditionalPensum;
    }

    public Teacher agreedToAdditionalPensum(Boolean agreedToAdditionalPensum) {
        this.agreedToAdditionalPensum = agreedToAdditionalPensum;
        return this;
    }

    public void setAgreedToAdditionalPensum(Boolean agreedToAdditionalPensum) {
        this.agreedToAdditionalPensum = agreedToAdditionalPensum;
    }

    public TeacherType getType() {
        return type;
    }

    public Teacher type(TeacherType type) {
        this.type = type;
        return this;
    }

    public void setType(TeacherType type) {
        this.type = type;
    }

    public Set<Entrustment> getEntrustments() {
        return entrustments;
    }

    public Teacher entrustments(Set<Entrustment> entrustments) {
        this.entrustments = entrustments;
        return this;
    }

    public Teacher addEntrustments(Entrustment entrustment) {
        this.entrustments.add(entrustment);
        entrustment.setTeacher(this);
        return this;
    }

    public Teacher removeEntrustments(Entrustment entrustment) {
        this.entrustments.remove(entrustment);
        entrustment.setTeacher(null);
        return this;
    }

    public void setEntrustments(Set<Entrustment> entrustments) {
        this.entrustments = entrustments;
    }

    public Set<ClassForm> getAllowedClassForms() {
        return allowedClassForms;
    }

    public Teacher allowedClassForms(Set<ClassForm> classForms) {
        this.allowedClassForms = classForms;
        return this;
    }

    public Teacher addAllowedClassForms(ClassForm classForm) {
        this.allowedClassForms.add(classForm);
        classForm.getTeachersAllowedToTeachThisForms().add(this);
        return this;
    }

    public Teacher removeAllowedClassForms(ClassForm classForm) {
        this.allowedClassForms.remove(classForm);
        classForm.getTeachersAllowedToTeachThisForms().remove(this);
        return this;
    }

    public void setAllowedClassForms(Set<ClassForm> classForms) {
        this.allowedClassForms = classForms;
    }

    public Set<KnowledgeArea> getKnowledgeAreas() {
        return knowledgeAreas;
    }

    public Teacher knowledgeAreas(Set<KnowledgeArea> knowledgeAreas) {
        this.knowledgeAreas = knowledgeAreas;
        return this;
    }

    public Teacher addKnowledgeAreas(KnowledgeArea knowledgeArea) {
        this.knowledgeAreas.add(knowledgeArea);
        knowledgeArea.getTeachersWithThisKnowledgeAreas().add(this);
        return this;
    }

    public Teacher removeKnowledgeAreas(KnowledgeArea knowledgeArea) {
        this.knowledgeAreas.remove(knowledgeArea);
        knowledgeArea.getTeachersWithThisKnowledgeAreas().remove(this);
        return this;
    }

    public void setKnowledgeAreas(Set<KnowledgeArea> knowledgeAreas) {
        this.knowledgeAreas = knowledgeAreas;
    }

    public Set<Course> getPreferedCourses() {
        return preferedCourses;
    }

    public Teacher preferedCourses(Set<Course> courses) {
        this.preferedCourses = courses;
        return this;
    }

    public Teacher addPreferedCourses(Course course) {
        this.preferedCourses.add(course);
        course.getTeachersThatPreferThisCourses().add(this);
        return this;
    }

    public Teacher removePreferedCourses(Course course) {
        this.preferedCourses.remove(course);
        course.getTeachersThatPreferThisCourses().remove(this);
        return this;
    }

    public void setPreferedCourses(Set<Course> courses) {
        this.preferedCourses = courses;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Teacher)) {
            return false;
        }
        return id != null && id.equals(((Teacher) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Teacher{" +
            "id=" + getId() +
            ", externalUserId='" + getExternalUserId() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", hourLimit=" + getHourLimit() +
            ", pensum=" + getPensum() +
            ", agreedToAdditionalPensum='" + isAgreedToAdditionalPensum() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
