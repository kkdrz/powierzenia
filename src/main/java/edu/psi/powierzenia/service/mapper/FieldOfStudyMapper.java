package edu.psi.powierzenia.service.mapper;

import edu.psi.powierzenia.domain.*;
import edu.psi.powierzenia.service.dto.FieldOfStudyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FieldOfStudy} and its DTO {@link FieldOfStudyDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FieldOfStudyMapper extends EntityMapper<FieldOfStudyDTO, FieldOfStudy> {


    @Mapping(target = "educationPlans", ignore = true)
    @Mapping(target = "removeEducationPlans", ignore = true)
    FieldOfStudy toEntity(FieldOfStudyDTO fieldOfStudyDTO);

    default FieldOfStudy fromId(Long id) {
        if (id == null) {
            return null;
        }
        FieldOfStudy fieldOfStudy = new FieldOfStudy();
        fieldOfStudy.setId(id);
        return fieldOfStudy;
    }
}
