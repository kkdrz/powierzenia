package edu.psi.powierzenia.service.impl;

import edu.psi.powierzenia.service.EntrustmentPlanService;
import edu.psi.powierzenia.domain.EntrustmentPlan;
import edu.psi.powierzenia.repository.EntrustmentPlanRepository;
import edu.psi.powierzenia.service.dto.EntrustmentPlanDTO;
import edu.psi.powierzenia.service.mapper.EntrustmentPlanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EntrustmentPlan}.
 */
@Service
@Transactional
public class EntrustmentPlanServiceImpl implements EntrustmentPlanService {

    private final Logger log = LoggerFactory.getLogger(EntrustmentPlanServiceImpl.class);

    private final EntrustmentPlanRepository entrustmentPlanRepository;

    private final EntrustmentPlanMapper entrustmentPlanMapper;

    public EntrustmentPlanServiceImpl(EntrustmentPlanRepository entrustmentPlanRepository, EntrustmentPlanMapper entrustmentPlanMapper) {
        this.entrustmentPlanRepository = entrustmentPlanRepository;
        this.entrustmentPlanMapper = entrustmentPlanMapper;
    }

    /**
     * Save a entrustmentPlan.
     *
     * @param entrustmentPlanDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EntrustmentPlanDTO save(EntrustmentPlanDTO entrustmentPlanDTO) {
        log.debug("Request to save EntrustmentPlan : {}", entrustmentPlanDTO);
        EntrustmentPlan entrustmentPlan = entrustmentPlanMapper.toEntity(entrustmentPlanDTO);
        entrustmentPlan = entrustmentPlanRepository.save(entrustmentPlan);
        return entrustmentPlanMapper.toDto(entrustmentPlan);
    }

    /**
     * Get all the entrustmentPlans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EntrustmentPlanDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EntrustmentPlans");
        return entrustmentPlanRepository.findAll(pageable)
            .map(entrustmentPlanMapper::toDto);
    }


    /**
     * Get one entrustmentPlan by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EntrustmentPlanDTO> findOne(Long id) {
        log.debug("Request to get EntrustmentPlan : {}", id);
        return entrustmentPlanRepository.findById(id)
            .map(entrustmentPlanMapper::toDto);
    }

    /**
     * Delete the entrustmentPlan by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EntrustmentPlan : {}", id);
        entrustmentPlanRepository.deleteById(id);
    }
}
