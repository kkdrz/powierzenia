package pl.edu.pwr.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.pwr.service.dto.EntrustmentDTO;

import java.util.Optional;

/**
 * Service Interface for managing {@link pl.edu.pwr.domain.Entrustment}.
 */
public interface EntrustmentService {

    /**
     * Save a entrustment.
     *
     * @param entrustmentDTO the entity to save.
     * @return the persisted entity.
     */
    EntrustmentDTO save(EntrustmentDTO entrustmentDTO);

    /**
     * Get all the entrustments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EntrustmentDTO> findAll(Pageable pageable);


    /**
     * Get the "id" entrustment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EntrustmentDTO> findOne(Long id);

    /**
     * Delete the "id" entrustment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
