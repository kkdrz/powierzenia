package pl.edu.pwr.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pwr.domain.Teacher;
import pl.edu.pwr.repository.TeacherRepository;
import pl.edu.pwr.service.TeacherService;
import pl.edu.pwr.service.dto.TeacherDTO;
import pl.edu.pwr.service.mapper.TeacherMapper;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Teacher}.
 */
@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {

    private final Logger log = LoggerFactory.getLogger(TeacherServiceImpl.class);

    private final TeacherRepository teacherRepository;

    private final TeacherMapper teacherMapper;

    public TeacherServiceImpl(TeacherRepository teacherRepository, TeacherMapper teacherMapper) {
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
    }

    /**
     * Save a teacher.
     *
     * @param teacherDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TeacherDTO save(TeacherDTO teacherDTO) {
        log.debug("Request to save Teacher : {}", teacherDTO);
        Teacher teacher = teacherMapper.toEntity(teacherDTO);
        teacher = teacherRepository.save(teacher);
        return teacherMapper.toDto(teacher);
    }

    /**
     * Get all the teachers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TeacherDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Teachers");
        return teacherRepository.findAll(pageable)
            .map(teacherMapper::toDto);
    }

    /**
     * Get all the teachers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TeacherDTO> findAllWithEagerRelationships(Pageable pageable) {
        return teacherRepository.findAllWithEagerRelationships(pageable).map(teacherMapper::toDto);
    }


    /**
     * Get one teacher by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TeacherDTO> findOne(Long id) {
        log.debug("Request to get Teacher : {}", id);
        return teacherRepository.findOneWithEagerRelationships(id)
            .map(teacherMapper::toDto);
    }

    @Override
    public Optional<TeacherDTO> findOneByUserId(String userId) {
        log.debug("Request to get Teacher with userId: {}", userId);
        return teacherRepository.findOneWithUserId(userId)
            .map(teacherMapper::toDto);
    }

    /**
     * Delete the teacher by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Teacher : {}", id);
        teacherRepository.deleteById(id);
    }
}
