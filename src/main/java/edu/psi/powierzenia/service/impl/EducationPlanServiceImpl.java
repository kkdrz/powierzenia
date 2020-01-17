package edu.psi.powierzenia.service.impl;

import edu.psi.powierzenia.service.EducationPlanService;
import edu.psi.powierzenia.domain.EducationPlan;
import edu.psi.powierzenia.repository.EducationPlanRepository;
import edu.psi.powierzenia.service.dto.EducationPlanDTO;
import edu.psi.powierzenia.service.mapper.EducationPlanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EducationPlan}.
 */
@Service
@Transactional
public class EducationPlanServiceImpl implements EducationPlanService {

    private final Logger log = LoggerFactory.getLogger(EducationPlanServiceImpl.class);

    private final EducationPlanRepository educationPlanRepository;

    private final EducationPlanMapper educationPlanMapper;

    public EducationPlanServiceImpl(EducationPlanRepository educationPlanRepository, EducationPlanMapper educationPlanMapper) {
        this.educationPlanRepository = educationPlanRepository;
        this.educationPlanMapper = educationPlanMapper;
    }

    /**
     * Save a educationPlan.
     *
     * @param educationPlanDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EducationPlanDTO save(EducationPlanDTO educationPlanDTO) {
        log.debug("Request to save EducationPlan : {}", educationPlanDTO);
        EducationPlan educationPlan = educationPlanMapper.toEntity(educationPlanDTO);
        educationPlan = educationPlanRepository.save(educationPlan);
        return educationPlanMapper.toDto(educationPlan);
    }

    /**
     * Get all the educationPlans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EducationPlanDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EducationPlans");
        return educationPlanRepository.findAll(pageable)
            .map(educationPlanMapper::toDto);
    }


    /**
     * Get one educationPlan by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EducationPlanDTO> findOne(Long id) {
        log.debug("Request to get EducationPlan : {}", id);
        return educationPlanRepository.findById(id)
            .map(educationPlanMapper::toDto);
    }

    /**
     * Delete the educationPlan by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EducationPlan : {}", id);
        educationPlanRepository.deleteById(id);
    }
}
