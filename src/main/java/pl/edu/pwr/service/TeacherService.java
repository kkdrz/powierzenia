package pl.edu.pwr.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.pwr.service.dto.TeacherDTO;

import java.util.Optional;

/**
 * Service Interface for managing {@link pl.edu.pwr.domain.Teacher}.
 */
public interface TeacherService {

    /**
     * Save a teacher.
     *
     * @param teacherDTO the entity to save.
     * @return the persisted entity.
     */
    TeacherDTO save(TeacherDTO teacherDTO);

    /**
     * Get all the teachers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TeacherDTO> findAll(Pageable pageable);

    /**
     * Get all the teachers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<TeacherDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" teacher.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TeacherDTO> findOne(Long id);

    /**
     * Get the "id" teacher.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TeacherDTO> findOneByUserId(String userId);

    /**
     * Delete the "id" teacher.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
