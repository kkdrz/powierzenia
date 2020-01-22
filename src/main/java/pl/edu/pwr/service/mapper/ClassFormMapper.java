package pl.edu.pwr.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.edu.pwr.domain.ClassForm;
import pl.edu.pwr.service.dto.ClassFormDTO;

/**
 * Mapper for the entity {@link ClassForm} and its DTO {@link ClassFormDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClassFormMapper extends EntityMapper<ClassFormDTO, ClassForm> {


    @Mapping(target = "teachersAllowedToTeachThisForms", ignore = true)
    @Mapping(target = "removeTeachersAllowedToTeachThisForm", ignore = true)
    ClassForm toEntity(ClassFormDTO classFormDTO);

    default ClassForm fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClassForm classForm = new ClassForm();
        classForm.setId(id);
        return classForm;
    }
}
