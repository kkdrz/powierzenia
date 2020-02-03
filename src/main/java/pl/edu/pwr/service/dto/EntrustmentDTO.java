package pl.edu.pwr.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link pl.edu.pwr.domain.Entrustment} entity.
 */
public class EntrustmentDTO implements Serializable {

    private Long id;

    @NotNull(message = "Hours are required")
    private Integer hours;

    @NotNull(message = "Hours multiplier is required")
    private Float hoursMultiplier;


    @NotNull(message = "Entrustment plan is required")
    private Long entrustmentPlanId;

    @NotNull(message = "Course class is required")
    private Long courseClassId;

    @NotNull(message = "Teacher is required")
    private Long teacherId;

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

    public Float getHoursMultiplier() {
        return hoursMultiplier;
    }

    public void setHoursMultiplier(Float hoursMultiplier) {
        this.hoursMultiplier = hoursMultiplier;
    }

    public Long getEntrustmentPlanId() {
        return entrustmentPlanId;
    }

    public void setEntrustmentPlanId(Long entrustmentPlanId) {
        this.entrustmentPlanId = entrustmentPlanId;
    }

    public Long getCourseClassId() {
        return courseClassId;
    }

    public void setCourseClassId(Long courseClassId) {
        this.courseClassId = courseClassId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EntrustmentDTO entrustmentDTO = (EntrustmentDTO) o;
        if (entrustmentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entrustmentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EntrustmentDTO{" +
            "id=" + getId() +
            ", hours=" + getHours() +
            ", hoursMultiplier=" + getHoursMultiplier() +
            ", entrustmentPlanId=" + getEntrustmentPlanId() +
            ", courseClassId=" + getCourseClassId() +
            ", teacherId=" + getTeacherId() +
            "}";
    }
}
