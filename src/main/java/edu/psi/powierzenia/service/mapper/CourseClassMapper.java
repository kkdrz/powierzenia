package edu.psi.powierzenia.service.mapper;

import edu.psi.powierzenia.domain.*;
import edu.psi.powierzenia.service.dto.CourseClassDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CourseClass} and its DTO {@link CourseClassDTO}.
 */
@Mapper(componentModel = "spring", uses = {CourseMapper.class})
public interface CourseClassMapper extends EntityMapper<CourseClassDTO, CourseClass> {

    @Mapping(source = "course.id", target = "courseId")
    CourseClassDTO toDto(CourseClass courseClass);

    @Mapping(target = "entrustments", ignore = true)
    @Mapping(target = "removeEntrustments", ignore = true)
    @Mapping(source = "courseId", target = "course")
    CourseClass toEntity(CourseClassDTO courseClassDTO);

    default CourseClass fromId(Long id) {
        if (id == null) {
            return null;
        }
        CourseClass courseClass = new CourseClass();
        courseClass.setId(id);
        return courseClass;
    }
}
