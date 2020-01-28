package pl.edu.pwr.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.edu.pwr.domain.Teacher;
import pl.edu.pwr.service.dto.TeacherDTO;

/**
 * Mapper for the entity {@link Teacher} and its DTO {@link TeacherDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ClassFormMapper.class, KnowledgeAreaMapper.class, CourseMapper.class})
public interface TeacherMapper extends EntityMapper<TeacherDTO, Teacher> {

    @Mapping(source = "user.id", target = "userId")
    TeacherDTO toDto(Teacher teacher);

    @Mapping(source = "userId", target = "user")
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
