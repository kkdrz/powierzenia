package edu.psi.powierzenia.service.mapper;

import edu.psi.powierzenia.domain.*;
import edu.psi.powierzenia.service.dto.TeacherDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Teacher} and its DTO {@link TeacherDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClassFormMapper.class, KnowledgeAreaMapper.class, CourseMapper.class})
public interface TeacherMapper extends EntityMapper<TeacherDTO, Teacher> {


    @Mapping(target = "entrustments", ignore = true)
    @Mapping(target = "removeEntrustments", ignore = true)
    @Mapping(target = "removeAllowedClassForms", ignore = true)
    @Mapping(target = "removeKnowledgeAreas", ignore = true)
    @Mapping(target = "removePreferedCourses", ignore = true)
    Teacher toEntity(TeacherDTO teacherDTO);

    default Teacher fromId(Long id) {
        if (id == null) {
            return null;
        }
        Teacher teacher = new Teacher();
        teacher.setId(id);
        return teacher;
    }
}
