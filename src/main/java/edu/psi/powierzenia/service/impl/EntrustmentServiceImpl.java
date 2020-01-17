package edu.psi.powierzenia.service.impl;

import edu.psi.powierzenia.service.EntrustmentService;
import edu.psi.powierzenia.domain.Entrustment;
import edu.psi.powierzenia.repository.EntrustmentRepository;
import edu.psi.powierzenia.service.dto.EntrustmentDTO;
import edu.psi.powierzenia.service.mapper.EntrustmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Entrustment}.
 */
@Service
@Transactional
public class EntrustmentServiceImpl implements EntrustmentService {

    private final Logger log = LoggerFactory.getLogger(EntrustmentServiceImpl.class);

    private final EntrustmentRepository entrustmentRepository;

    private final EntrustmentMapper entrustmentMapper;

    public EntrustmentServiceImpl(EntrustmentRepository entrustmentRepository, EntrustmentMapper entrustmentMapper) {
        this.entrustmentRepository = entrustmentRepository;
        this.entrustmentMapper = entrustmentMapper;
    }

    /**
     * Save a entrustment.
     *
     * @param entrustmentDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EntrustmentDTO save(EntrustmentDTO entrustmentDTO) {
        log.debug("Request to save Entrustment : {}", entrustmentDTO);
        Entrustment entrustment = entrustmentMapper.toEntity(entrustmentDTO);
        entrustment = entrustmentRepository.save(entrustment);
        return entrustmentMapper.toDto(entrustment);
    }

    /**
     * Get all the entrustments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EntrustmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Entrustments");
        return entrustmentRepository.findAll(pageable)
            .map(entrustmentMapper::toDto);
    }


    /**
     * Get one entrustment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EntrustmentDTO> findOne(Long id) {
        log.debug("Request to get Entrustment : {}", id);
        return entrustmentRepository.findById(id)
            .map(entrustmentMapper::toDto);
    }

    /**
     * Delete the entrustment by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Entrustment : {}", id);
        entrustmentRepository.deleteById(id);
    }
}
