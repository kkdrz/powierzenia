package edu.psi.powierzenia.service.mapper;

import edu.psi.powierzenia.domain.*;
import edu.psi.powierzenia.service.dto.KnowledgeAreaDTO;

import org.mapstruct.*;

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
