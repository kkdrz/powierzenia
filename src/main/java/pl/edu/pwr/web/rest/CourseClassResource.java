package pl.edu.pwr.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edu.pwr.service.CourseClassService;
import pl.edu.pwr.service.dto.CourseClassDTO;
import pl.edu.pwr.web.rest.errors.BadRequestAlertException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link pl.edu.pwr.domain.CourseClass}.
 */
@RestController
@RequestMapping("/api")
public class CourseClassResource {

    private static final String ENTITY_NAME = "courseClass";
    private final Logger log = LoggerFactory.getLogger(CourseClassResource.class);
    private final CourseClassService courseClassService;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public CourseClassResource(CourseClassService courseClassService) {
        this.courseClassService = courseClassService;
    }

    /**
     * {@code POST  /course-classes} : Create a new courseClass.
     *
     * @param courseClassDTO the courseClassDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new courseClassDTO, or with status {@code 400 (Bad Request)} if the courseClass has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/course-classes")
    public ResponseEntity<CourseClassDTO> createCourseClass(@RequestBody @Valid CourseClassDTO courseClassDTO, BindingResult bindingResult) throws URISyntaxException {
        log.debug("REST request to save CourseClass : {}", courseClassDTO);
        if (bindingResult.hasErrors()) {
            throw new BadRequestAlertException(
                bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).reduce("", (s, s2) -> s + "\n" + s2),
                ENTITY_NAME,
                "fields_invalid"
            );
        }
        if (courseClassDTO.getId() != null) {
            throw new BadRequestAlertException("A new courseClass cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CourseClassDTO result = courseClassService.save(courseClassDTO);
        return ResponseEntity.created(new URI("/api/course-classes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /course-classes} : Updates an existing courseClass.
     *
     * @param courseClassDTO the courseClassDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseClassDTO,
     * or with status {@code 400 (Bad Request)} if the courseClassDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the courseClassDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/course-classes")
    public ResponseEntity<CourseClassDTO> updateCourseClass(@RequestBody @Valid CourseClassDTO courseClassDTO, BindingResult bindingResult) throws URISyntaxException {
        log.debug("REST request to update CourseClass : {}", courseClassDTO);
        if (bindingResult.hasErrors()) {
            throw new BadRequestAlertException(
                bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).reduce("", (s, s2) -> s + "\n" + s2),
                ENTITY_NAME,
                "fields_invalid"
            );
        }

        if (courseClassDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CourseClassDTO result = courseClassService.save(courseClassDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, courseClassDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /course-classes} : get all the courseClasses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courseClasses in body.
     */
    @GetMapping("/course-classes")
    public ResponseEntity<List<CourseClassDTO>> getAllCourseClasses(Pageable pageable) {
        log.debug("REST request to get a page of CourseClasses");
        Page<CourseClassDTO> page = courseClassService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /course-classes/:id} : get the "id" courseClass.
     *
     * @param id the id of the courseClassDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the courseClassDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/course-classes/{id}")
    public ResponseEntity<CourseClassDTO> getCourseClass(@PathVariable Long id) {
        log.debug("REST request to get CourseClass : {}", id);
        Optional<CourseClassDTO> courseClassDTO = courseClassService.findOne(id);
        return ResponseUtil.wrapOrNotFound(courseClassDTO);
    }

    /**
     * {@code DELETE  /course-classes/:id} : delete the "id" courseClass.
     *
     * @param id the id of the courseClassDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/course-classes/{id}")
    public ResponseEntity<Void> deleteCourseClass(@PathVariable Long id) {
        log.debug("REST request to delete CourseClass : {}", id);
        courseClassService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
