package pl.edu.pwr.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pwr.domain.FieldOfStudy;
import pl.edu.pwr.repository.FieldOfStudyRepository;
import pl.edu.pwr.service.FieldOfStudyService;
import pl.edu.pwr.service.dto.FieldOfStudyDTO;
import pl.edu.pwr.service.mapper.FieldOfStudyMapper;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FieldOfStudy}.
 */
@Service
@Transactional
public class FieldOfStudyServiceImpl implements FieldOfStudyService {

    private final Logger log = LoggerFactory.getLogger(FieldOfStudyServiceImpl.class);

    private final FieldOfStudyRepository fieldOfStudyRepository;

    private final FieldOfStudyMapper fieldOfStudyMapper;

    public FieldOfStudyServiceImpl(FieldOfStudyRepository fieldOfStudyRepository, FieldOfStudyMapper fieldOfStudyMapper) {
        this.fieldOfStudyRepository = fieldOfStudyRepository;
        this.fieldOfStudyMapper = fieldOfStudyMapper;
    }

    /**
     * Save a fieldOfStudy.
     *
     * @param fieldOfStudyDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FieldOfStudyDTO save(FieldOfStudyDTO fieldOfStudyDTO) {
        log.debug("Request to save FieldOfStudy : {}", fieldOfStudyDTO);
        FieldOfStudy fieldOfStudy = fieldOfStudyMapper.toEntity(fieldOfStudyDTO);
        fieldOfStudy = fieldOfStudyRepository.save(fieldOfStudy);
        return fieldOfStudyMapper.toDto(fieldOfStudy);
    }

    /**
     * Get all the fieldOfStudies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FieldOfStudyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FieldOfStudies");
        return fieldOfStudyRepository.findAll(pageable)
            .map(fieldOfStudyMapper::toDto);
    }


    /**
     * Get one fieldOfStudy by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FieldOfStudyDTO> findOne(Long id) {
        log.debug("Request to get FieldOfStudy : {}", id);
        return fieldOfStudyRepository.findById(id)
            .map(fieldOfStudyMapper::toDto);
    }

    /**
     * Delete the fieldOfStudy by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FieldOfStudy : {}", id);
        fieldOfStudyRepository.deleteById(id);
    }
}
