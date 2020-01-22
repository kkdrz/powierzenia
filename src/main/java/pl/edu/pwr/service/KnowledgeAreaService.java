package pl.edu.pwr.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.pwr.service.dto.KnowledgeAreaDTO;

import java.util.Optional;

/**
 * Service Interface for managing {@link pl.edu.pwr.domain.KnowledgeArea}.
 */
public interface KnowledgeAreaService {

    /**
     * Save a knowledgeArea.
     *
     * @param knowledgeAreaDTO the entity to save.
     * @return the persisted entity.
     */
    KnowledgeAreaDTO save(KnowledgeAreaDTO knowledgeAreaDTO);

    /**
     * Get all the knowledgeAreas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<KnowledgeAreaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" knowledgeArea.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<KnowledgeAreaDTO> findOne(Long id);

    /**
     * Delete the "id" knowledgeArea.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
