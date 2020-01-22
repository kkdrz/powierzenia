package pl.edu.pwr.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.edu.pwr.domain.CourseClass;
import pl.edu.pwr.service.dto.CourseClassDTO;

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
