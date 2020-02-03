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
import pl.edu.pwr.security.AuthoritiesConstants;
import pl.edu.pwr.security.SecurityUtils;
import pl.edu.pwr.service.EntrustmentService;
import pl.edu.pwr.service.TeacherService;
import pl.edu.pwr.service.dto.EntrustmentDTO;
import pl.edu.pwr.service.dto.TeacherDTO;
import pl.edu.pwr.web.rest.errors.BadRequestAlertException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link pl.edu.pwr.domain.Entrustment}.
 */
@RestController
@RequestMapping("/api")
public class EntrustmentResource {

    private static final String ENTITY_NAME = "entrustment";
    private final Logger log = LoggerFactory.getLogger(EntrustmentResource.class);
    private final EntrustmentService entrustmentService;
    private final TeacherService teacherService;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public EntrustmentResource(EntrustmentService entrustmentService, TeacherService teacherService) {
        this.entrustmentService = entrustmentService;
        this.teacherService = teacherService;
    }

    /**
     * {@code POST  /entrustments} : Create a new entrustment.
     *
     * @param entrustmentDTO the entrustmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entrustmentDTO, or with status {@code 400 (Bad Request)} if the entrustment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/entrustments")
    public ResponseEntity<EntrustmentDTO> createEntrustment(@RequestBody @Valid EntrustmentDTO entrustmentDTO, BindingResult bindingResult) throws URISyntaxException {
        log.debug("REST request to save Entrustment : {}", entrustmentDTO);
        if (bindingResult.hasErrors()) {
            throw new BadRequestAlertException(
                bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).reduce("", (s, s2) -> s + "\n" + s2),
                ENTITY_NAME,
                "fields_invalid"
            );
        }
        if (entrustmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new entrustment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntrustmentDTO result = entrustmentService.save(entrustmentDTO);
        return ResponseEntity.created(new URI("/api/entrustments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /entrustments} : Updates an existing entrustment.
     *
     * @param entrustmentDTO the entrustmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entrustmentDTO,
     * or with status {@code 400 (Bad Request)} if the entrustmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entrustmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/entrustments")
    public ResponseEntity<EntrustmentDTO> updateEntrustment(@RequestBody @Valid EntrustmentDTO entrustmentDTO, BindingResult bindingResult) throws URISyntaxException {
        log.debug("REST request to update Entrustment : {}", entrustmentDTO);
        if (bindingResult.hasErrors()) {
            throw new BadRequestAlertException(
                bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).reduce("", (s, s2) -> s + "\n" + s2),
                ENTITY_NAME,
                "fields_invalid"
            );
        }
        if (entrustmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EntrustmentDTO result = entrustmentService.save(entrustmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, entrustmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /entrustments} : get all the entrustments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entrustments in body.
     */
    @GetMapping("/entrustments")
    public ResponseEntity<List<EntrustmentDTO>> getAllEntrustments(Pageable pageable) {
        log.debug("REST request to get a page of Entrustments");

        Page<EntrustmentDTO> page;
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.TEACHER)) {
            String uid = SecurityUtils.getCurrentUserId();
            Optional<TeacherDTO> teacher = teacherService.findOneByUserId(uid);
            if (teacher.isPresent()) {
                page = entrustmentService.findAll(pageable, teacher.get());
            } else {
                throw new BadRequestAlertException("Teacher not found. uid: " + uid, ENTITY_NAME, "teacherNotFound");
            }
        } else {
            page = entrustmentService.findAll(pageable);
        }

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /entrustments/:id} : get the "id" entrustment.
     *
     * @param id the id of the entrustmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entrustmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entrustments/{id}")
    public ResponseEntity<EntrustmentDTO> getEntrustment(@PathVariable Long id) {
        log.debug("REST request to get Entrustment : {}", id);
        Optional<EntrustmentDTO> entrustmentDTO = entrustmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entrustmentDTO);
    }

    /**
     * {@code DELETE  /entrustments/:id} : delete the "id" entrustment.
     *
     * @param id the id of the entrustmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/entrustments/{id}")
    public ResponseEntity<Void> deleteEntrustment(@PathVariable Long id) {
        log.debug("REST request to delete Entrustment : {}", id);
        entrustmentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
