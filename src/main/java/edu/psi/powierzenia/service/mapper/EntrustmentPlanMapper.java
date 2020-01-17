package edu.psi.powierzenia.service.mapper;

import edu.psi.powierzenia.domain.*;
import edu.psi.powierzenia.service.dto.EntrustmentPlanDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EntrustmentPlan} and its DTO {@link EntrustmentPlanDTO}.
 */
@Mapper(componentModel = "spring", uses = {EducationPlanMapper.class})
public interface EntrustmentPlanMapper extends EntityMapper<EntrustmentPlanDTO, EntrustmentPlan> {

    @Mapping(source = "educationPlan.id", target = "educationPlanId")
    EntrustmentPlanDTO toDto(EntrustmentPlan entrustmentPlan);

    @Mapping(target = "entrustments", ignore = true)
    @Mapping(target = "removeEntrustments", ignore = true)
    @Mapping(source = "educationPlanId", target = "educationPlan")
    EntrustmentPlan toEntity(EntrustmentPlanDTO entrustmentPlanDTO);

    default EntrustmentPlan fromId(Long id) {
        if (id == null) {
            return null;
        }
        EntrustmentPlan entrustmentPlan = new EntrustmentPlan();
        entrustmentPlan.setId(id);
        return entrustmentPlan;
    }
}
