package pl.edu.pwr.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.pwr.service.dto.EducationPlanDTO;

import java.util.Optional;

/**
 * Service Interface for managing {@link pl.edu.pwr.domain.EducationPlan}.
 */
public interface EducationPlanService {

    /**
     * Save a educationPlan.
     *
     * @param educationPlanDTO the entity to save.
     * @return the persisted entity.
     */
    EducationPlanDTO save(EducationPlanDTO educationPlanDTO);

    /**
     * Get all the educationPlans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EducationPlanDTO> findAll(Pageable pageable);


    /**
     * Get the "id" educationPlan.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EducationPlanDTO> findOne(Long id);

    /**
     * Delete the "id" educationPlan.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
