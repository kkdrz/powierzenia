package pl.edu.pwr.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edu.pwr.service.TeacherService;
import pl.edu.pwr.service.dto.TeacherDTO;
import pl.edu.pwr.web.rest.errors.BadRequestAlertException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link pl.edu.pwr.domain.Teacher}.
 */
@RestController
@RequestMapping("/api")
public class TeacherResource {

    private final Logger log = LoggerFactory.getLogger(TeacherResource.class);

    private static final String ENTITY_NAME = "teacher";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TeacherService teacherService;

    public TeacherResource(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    /**
     * {@code POST  /teachers} : Create a new teacher.
     *
     * @param teacherDTO the teacherDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new teacherDTO, or with status {@code 400 (Bad Request)} if the teacher has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/teachers")
    public ResponseEntity<TeacherDTO> createTeacher(@RequestBody TeacherDTO teacherDTO) throws URISyntaxException {
        log.debug("REST request to save Teacher : {}", teacherDTO);
        if (teacherDTO.getId() != null) {
            throw new BadRequestAlertException("A new teacher cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TeacherDTO result = teacherService.save(teacherDTO);
        return ResponseEntity.created(new URI("/api/teachers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /teachers} : Updates an existing teacher.
     *
     * @param teacherDTO the teacherDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teacherDTO,
     * or with status {@code 400 (Bad Request)} if the teacherDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the teacherDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/teachers")
    public ResponseEntity<TeacherDTO> updateTeacher(@RequestBody TeacherDTO teacherDTO) throws URISyntaxException {
        log.debug("REST request to update Teacher : {}", teacherDTO);
        if (teacherDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TeacherDTO result = teacherService.save(teacherDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, teacherDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /teachers} : get all the teachers.
     *
     * @param pageable  the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of teachers in body.
     */
    @GetMapping("/teachers")
    public ResponseEntity<List<TeacherDTO>> getAllTeachers(
        Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload,
        @RequestParam Optional<String> userId) {

        if (userId.isPresent()) {
            log.debug("REST request to get Teacher with userId : {}", userId.get());
            Optional<TeacherDTO> teacherDTO = teacherService.findOneByUserId(userId.get());
            return teacherDTO.map(dto -> ResponseEntity.ok().body(Collections.singletonList(dto)))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        }

        log.debug("REST request to get a page of Teachers");
        Page<TeacherDTO> page;
        if (eagerload) {
            page = teacherService.findAllWithEagerRelationships(pageable);
        } else {
            page = teacherService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /teachers/:id} : get the "id" teacher.
     *
     * @param id the id of the teacherDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the teacherDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/teachers/{id}")
    public ResponseEntity<TeacherDTO> getTeacher(@PathVariable Long id) {
        log.debug("REST request to get Teacher : {}", id);
        Optional<TeacherDTO> teacherDTO = teacherService.findOne(id);
        return ResponseUtil.wrapOrNotFound(teacherDTO);
    }

    /**
     * {@code DELETE  /teachers/:id} : delete the "id" teacher.
     *
     * @param id the id of the teacherDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.0
     */
    @DeleteMapping("/teachers/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        log.debug("REST request to delete Teacher : {}", id);
        teacherService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
