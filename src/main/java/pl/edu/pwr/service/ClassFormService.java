package pl.edu.pwr.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.pwr.service.dto.ClassFormDTO;

import java.util.Optional;

/**
 * Service Interface for managing {@link pl.edu.pwr.domain.ClassForm}.
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
