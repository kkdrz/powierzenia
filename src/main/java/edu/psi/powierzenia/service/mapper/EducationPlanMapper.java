package edu.psi.powierzenia.service.mapper;

import edu.psi.powierzenia.domain.*;
import edu.psi.powierzenia.service.dto.EducationPlanDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EducationPlan} and its DTO {@link EducationPlanDTO}.
 */
@Mapper(componentModel = "spring", uses = {FieldOfStudyMapper.class})
public interface EducationPlanMapper extends EntityMapper<EducationPlanDTO, EducationPlan> {

    @Mapping(source = "fieldOfStudy.id", target = "fieldOfStudyId")
    EducationPlanDTO toDto(EducationPlan educationPlan);

    @Mapping(target = "entrustmentPlans", ignore = true)
    @Mapping(target = "removeEntrustmentPlans", ignore = true)
    @Mapping(target = "courses", ignore = true)
    @Mapping(target = "removeCourses", ignore = true)
    @Mapping(source = "fieldOfStudyId", target = "fieldOfStudy")
    EducationPlan toEntity(EducationPlanDTO educationPlanDTO);

    default EducationPlan fromId(Long id) {
        if (id == null) {
            return null;
        }
        EducationPlan educationPlan = new EducationPlan();
        educationPlan.setId(id);
        return educationPlan;
    }
}
