package edu.psi.powierzenia.service.impl;

import edu.psi.powierzenia.service.CourseClassService;
import edu.psi.powierzenia.domain.CourseClass;
import edu.psi.powierzenia.repository.CourseClassRepository;
import edu.psi.powierzenia.service.dto.CourseClassDTO;
import edu.psi.powierzenia.service.mapper.CourseClassMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CourseClass}.
 */
@Service
@Transactional
public class CourseClassServiceImpl implements CourseClassService {

    private final Logger log = LoggerFactory.getLogger(CourseClassServiceImpl.class);

    private final CourseClassRepository courseClassRepository;

    private final CourseClassMapper courseClassMapper;

    public CourseClassServiceImpl(CourseClassRepository courseClassRepository, CourseClassMapper courseClassMapper) {
        this.courseClassRepository = courseClassRepository;
        this.courseClassMapper = courseClassMapper;
    }

    /**
     * Save a courseClass.
     *
     * @param courseClassDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CourseClassDTO save(CourseClassDTO courseClassDTO) {
        log.debug("Request to save CourseClass : {}", courseClassDTO);
        CourseClass courseClass = courseClassMapper.toEntity(courseClassDTO);
        courseClass = courseClassRepository.save(courseClass);
        return courseClassMapper.toDto(courseClass);
    }

    /**
     * Get all the courseClasses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CourseClassDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CourseClasses");
        return courseClassRepository.findAll(pageable)
            .map(courseClassMapper::toDto);
    }


    /**
     * Get one courseClass by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CourseClassDTO> findOne(Long id) {
        log.debug("Request to get CourseClass : {}", id);
        return courseClassRepository.findById(id)
            .map(courseClassMapper::toDto);
    }

    /**
     * Delete the courseClass by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CourseClass : {}", id);
        courseClassRepository.deleteById(id);
    }
}
