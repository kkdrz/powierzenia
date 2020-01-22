package pl.edu.pwr.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.pwr.service.dto.CourseClassDTO;

import java.util.Optional;

/**
 * Service Interface for managing {@link pl.edu.pwr.domain.CourseClass}.
 */
public interface CourseClassService {

    /**
     * Save a courseClass.
     *
     * @param courseClassDTO the entity to save.
     * @return the persisted entity.
     */
    CourseClassDTO save(CourseClassDTO courseClassDTO);

    /**
     * Get all the courseClasses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CourseClassDTO> findAll(Pageable pageable);


    /**
     * Get the "id" courseClass.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CourseClassDTO> findOne(Long id);

    /**
     * Delete the "id" courseClass.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
