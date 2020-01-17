package edu.psi.powierzenia.service;

import edu.psi.powierzenia.service.dto.EntrustmentPlanDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link edu.psi.powierzenia.domain.EntrustmentPlan}.
 */
public interface EntrustmentPlanService {

    /**
     * Save a entrustmentPlan.
     *
     * @param entrustmentPlanDTO the entity to save.
     * @return the persisted entity.
     */
    EntrustmentPlanDTO save(EntrustmentPlanDTO entrustmentPlanDTO);

    /**
     * Get all the entrustmentPlans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EntrustmentPlanDTO> findAll(Pageable pageable);


    /**
     * Get the "id" entrustmentPlan.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EntrustmentPlanDTO> findOne(Long id);

    /**
     * Delete the "id" entrustmentPlan.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
