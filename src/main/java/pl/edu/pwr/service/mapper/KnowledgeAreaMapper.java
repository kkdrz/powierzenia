package pl.edu.pwr.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.edu.pwr.domain.KnowledgeArea;
import pl.edu.pwr.service.dto.KnowledgeAreaDTO;

/**
 * Mapper for the entity {@link KnowledgeArea} and its DTO {@link KnowledgeAreaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface KnowledgeAreaMapper extends EntityMapper<KnowledgeAreaDTO, KnowledgeArea> {


    @Mapping(target = "teachersWithThisKnowledgeAreas", ignore = true)
    @Mapping(target = "removeTeachersWithThisKnowledgeArea", ignore = true)
    @Mapping(target = "coursesWithThisKnowledgeAreas", ignore = true)
    @Mapping(target = "removeCoursesWithThisKnowledgeArea", ignore = true)
    KnowledgeArea toEntity(KnowledgeAreaDTO knowledgeAreaDTO);

    default KnowledgeArea fromId(Long id) {
        if (id == null) {
            return null;
        }
        KnowledgeArea knowledgeArea = new KnowledgeArea();
        knowledgeArea.setId(id);
        return knowledgeArea;
    }
}
