package pl.edu.pwr.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.edu.pwr.domain.Entrustment;
import pl.edu.pwr.service.dto.EntrustmentDTO;

/**
 * Mapper for the entity {@link Entrustment} and its DTO {@link EntrustmentDTO}.
 */
@Mapper(componentModel = "spring", uses = {EntrustmentPlanMapper.class, CourseClassMapper.class, TeacherMapper.class})
public interface EntrustmentMapper extends EntityMapper<EntrustmentDTO, Entrustment> {

    @Mapping(source = "entrustmentPlan.id", target = "entrustmentPlanId")
    @Mapping(source = "courseClass.id", target = "courseClassId")
    @Mapping(source = "teacher.id", target = "teacherId")
    EntrustmentDTO toDto(Entrustment entrustment);

    @Mapping(source = "entrustmentPlanId", target = "entrustmentPlan")
    @Mapping(source = "courseClassId", target = "courseClass")
    @Mapping(source = "teacherId", target = "teacher")
    Entrustment toEntity(EntrustmentDTO entrustmentDTO);

    default Entrustment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Entrustment entrustment = new Entrustment();
        entrustment.setId(id);
        return entrustment;
    }
}
