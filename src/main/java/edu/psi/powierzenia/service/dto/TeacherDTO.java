package edu.psi.powierzenia.service.dto;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import edu.psi.powierzenia.domain.enumeration.TeacherType;

/**
 * A DTO for the {@link edu.psi.powierzenia.domain.Teacher} entity.
 */
public class TeacherDTO implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private Integer hourLimit;

    private Integer pensum;

    private Boolean agreedToAdditionalPensum;

    private TeacherType type;


    private Set<ClassFormDTO> allowedClassForms = new HashSet<>();

    private Set<KnowledgeAreaDTO> knowledgeAreas = new HashSet<>();

    private Set<CourseDTO> preferedCourses = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getHourLimit() {
        return hourLimit;
    }

    public void setHourLimit(Integer hourLimit) {
        this.hourLimit = hourLimit;
    }

    public Integer getPensum() {
        return pensum;
    }

    public void setPensum(Integer pensum) {
        this.pensum = pensum;
    }

    public Boolean isAgreedToAdditionalPensum() {
        return agreedToAdditionalPensum;
    }

    public void setAgreedToAdditionalPensum(Boolean agreedToAdditionalPensum) {
        this.agreedToAdditionalPensum = agreedToAdditionalPensum;
    }

    public TeacherType getType() {
        return type;
    }

    public void setType(TeacherType type) {
        this.type = type;
    }

    public Set<ClassFormDTO> getAllowedClassForms() {
        return allowedClassForms;
    }

    public void setAllowedClassForms(Set<ClassFormDTO> classForms) {
        this.allowedClassForms = classForms;
    }

    public Set<KnowledgeAreaDTO> getKnowledgeAreas() {
        return knowledgeAreas;
    }

    public void setKnowledgeAreas(Set<KnowledgeAreaDTO> knowledgeAreas) {
        this.knowledgeAreas = knowledgeAreas;
    }

    public Set<CourseDTO> getPreferedCourses() {
        return preferedCourses;
    }

    public void setPreferedCourses(Set<CourseDTO> courses) {
        this.preferedCourses = courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TeacherDTO teacherDTO = (TeacherDTO) o;
        if (teacherDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), teacherDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TeacherDTO{" +
            "id=" + getId() +
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
