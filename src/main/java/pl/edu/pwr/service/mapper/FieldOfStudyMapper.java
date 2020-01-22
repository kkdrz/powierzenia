package pl.edu.pwr.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.edu.pwr.domain.FieldOfStudy;
import pl.edu.pwr.service.dto.FieldOfStudyDTO;

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
