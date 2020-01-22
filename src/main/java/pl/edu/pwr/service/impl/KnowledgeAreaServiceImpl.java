package pl.edu.pwr.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pwr.domain.KnowledgeArea;
import pl.edu.pwr.repository.KnowledgeAreaRepository;
import pl.edu.pwr.service.KnowledgeAreaService;
import pl.edu.pwr.service.dto.KnowledgeAreaDTO;
import pl.edu.pwr.service.mapper.KnowledgeAreaMapper;

import java.util.Optional;

/**
 * Service Implementation for managing {@link KnowledgeArea}.
 */
@Service
@Transactional
public class KnowledgeAreaServiceImpl implements KnowledgeAreaService {

    private final Logger log = LoggerFactory.getLogger(KnowledgeAreaServiceImpl.class);

    private final KnowledgeAreaRepository knowledgeAreaRepository;

    private final KnowledgeAreaMapper knowledgeAreaMapper;

    public KnowledgeAreaServiceImpl(KnowledgeAreaRepository knowledgeAreaRepository, KnowledgeAreaMapper knowledgeAreaMapper) {
        this.knowledgeAreaRepository = knowledgeAreaRepository;
        this.knowledgeAreaMapper = knowledgeAreaMapper;
    }

    /**
     * Save a knowledgeArea.
     *
     * @param knowledgeAreaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public KnowledgeAreaDTO save(KnowledgeAreaDTO knowledgeAreaDTO) {
        log.debug("Request to save KnowledgeArea : {}", knowledgeAreaDTO);
        KnowledgeArea knowledgeArea = knowledgeAreaMapper.toEntity(knowledgeAreaDTO);
        knowledgeArea = knowledgeAreaRepository.save(knowledgeArea);
        return knowledgeAreaMapper.toDto(knowledgeArea);
    }

    /**
     * Get all the knowledgeAreas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<KnowledgeAreaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all KnowledgeAreas");
        return knowledgeAreaRepository.findAll(pageable)
            .map(knowledgeAreaMapper::toDto);
    }


    /**
     * Get one knowledgeArea by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<KnowledgeAreaDTO> findOne(Long id) {
        log.debug("Request to get KnowledgeArea : {}", id);
        return knowledgeAreaRepository.findById(id)
            .map(knowledgeAreaMapper::toDto);
    }

    /**
     * Delete the knowledgeArea by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete KnowledgeArea : {}", id);
        knowledgeAreaRepository.deleteById(id);
    }
}
