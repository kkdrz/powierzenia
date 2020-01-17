package edu.psi.powierzenia.service.mapper;

import edu.psi.powierzenia.domain.*;
import edu.psi.powierzenia.service.dto.CourseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Course} and its DTO {@link CourseDTO}.
 */
@Mapper(componentModel = "spring", uses = {KnowledgeAreaMapper.class, EducationPlanMapper.class})
public interface CourseMapper extends EntityMapper<CourseDTO, Course> {

    @Mapping(source = "educationPlan.id", target = "educationPlanId")
    CourseDTO toDto(Course course);

    @Mapping(target = "classes", ignore = true)
    @Mapping(target = "removeClasses", ignore = true)
    @Mapping(target = "removeTags", ignore = true)
    @Mapping(source = "educationPlanId", target = "educationPlan")
    @Mapping(target = "teachersThatPreferThisCourses", ignore = true)
    @Mapping(target = "removeTeachersThatPreferThisCourse", ignore = true)
    Course toEntity(CourseDTO courseDTO);

    default Course fromId(Long id) {
        if (id == null) {
            return null;
        }
        Course course = new Course();
        course.setId(id);
        return course;
    }
}
