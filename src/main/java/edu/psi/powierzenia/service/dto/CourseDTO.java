package edu.psi.powierzenia.service.dto;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link edu.psi.powierzenia.domain.Course} entity.
 */
public class CourseDTO implements Serializable {

    private Long id;

    private String name;

    private String code;


    private Set<KnowledgeAreaDTO> tags = new HashSet<>();

    private Long educationPlanId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<KnowledgeAreaDTO> getTags() {
        return tags;
    }

    public void setTags(Set<KnowledgeAreaDTO> knowledgeAreas) {
        this.tags = knowledgeAreas;
    }

    public Long getEducationPlanId() {
        return educationPlanId;
    }

    public void setEducationPlanId(Long educationPlanId) {
        this.educationPlanId = educationPlanId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CourseDTO courseDTO = (CourseDTO) o;
        if (courseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), courseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CourseDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", educationPlanId=" + getEducationPlanId() +
            "}";
    }
}
