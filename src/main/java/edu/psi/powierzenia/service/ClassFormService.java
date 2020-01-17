package edu.psi.powierzenia.service;

import edu.psi.powierzenia.service.dto.ClassFormDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link edu.psi.powierzenia.domain.ClassForm}.
 */
public interface ClassFormService {

    /**
     * Save a classForm.
     *
     * @param classFormDTO the entity to save.
     * @return the persisted entity.
     */
    ClassFormDTO save(ClassFormDTO classFormDTO);

    /**
     * Get all the classForms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ClassFormDTO> findAll(Pageable pageable);


    /**
     * Get the "id" classForm.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClassFormDTO> findOne(Long id);

    /**
     * Delete the "id" classForm.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
