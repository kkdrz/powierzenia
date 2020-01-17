package edu.psi.powierzenia.service.mapper;

import edu.psi.powierzenia.domain.*;
import edu.psi.powierzenia.service.dto.ClassFormDTO;

import org.mapstruct.*;

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
