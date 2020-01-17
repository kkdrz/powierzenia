package edu.psi.powierzenia.service.impl;

import edu.psi.powierzenia.service.ClassFormService;
import edu.psi.powierzenia.domain.ClassForm;
import edu.psi.powierzenia.repository.ClassFormRepository;
import edu.psi.powierzenia.service.dto.ClassFormDTO;
import edu.psi.powierzenia.service.mapper.ClassFormMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ClassForm}.
 */
@Service
@Transactional
public class ClassFormServiceImpl implements ClassFormService {

    private final Logger log = LoggerFactory.getLogger(ClassFormServiceImpl.class);

    private final ClassFormRepository classFormRepository;

    private final ClassFormMapper classFormMapper;

    public ClassFormServiceImpl(ClassFormRepository classFormRepository, ClassFormMapper classFormMapper) {
        this.classFormRepository = classFormRepository;
        this.classFormMapper = classFormMapper;
    }

    /**
     * Save a classForm.
     *
     * @param classFormDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ClassFormDTO save(ClassFormDTO classFormDTO) {
        log.debug("Request to save ClassForm : {}", classFormDTO);
        ClassForm classForm = classFormMapper.toEntity(classFormDTO);
        classForm = classFormRepository.save(classForm);
        return classFormMapper.toDto(classForm);
    }

    /**
     * Get all the classForms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClassFormDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClassForms");
        return classFormRepository.findAll(pageable)
            .map(classFormMapper::toDto);
    }


    /**
     * Get one classForm by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ClassFormDTO> findOne(Long id) {
        log.debug("Request to get ClassForm : {}", id);
        return classFormRepository.findById(id)
            .map(classFormMapper::toDto);
    }

    /**
     * Delete the classForm by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClassForm : {}", id);
        classFormRepository.deleteById(id);
    }
}
