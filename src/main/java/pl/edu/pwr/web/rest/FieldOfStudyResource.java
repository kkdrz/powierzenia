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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edu.pwr.service.FieldOfStudyService;
import pl.edu.pwr.service.dto.FieldOfStudyDTO;
import pl.edu.pwr.web.rest.errors.BadRequestAlertException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link pl.edu.pwr.domain.FieldOfStudy}.
 */
@RestController
@RequestMapping("/api")
public class FieldOfStudyResource {

    private static final String ENTITY_NAME = "fieldOfStudy";
    private final Logger log = LoggerFactory.getLogger(FieldOfStudyResource.class);
    private final FieldOfStudyService fieldOfStudyService;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public FieldOfStudyResource(FieldOfStudyService fieldOfStudyService) {
        this.fieldOfStudyService = fieldOfStudyService;
    }

    /**
     * {@code POST  /field-of-studies} : Create a new fieldOfStudy.
     *
     * @param fieldOfStudyDTO the fieldOfStudyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fieldOfStudyDTO, or with status {@code 400 (Bad Request)} if the fieldOfStudy has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/field-of-studies")
    public ResponseEntity<FieldOfStudyDTO> createFieldOfStudy(@RequestBody FieldOfStudyDTO fieldOfStudyDTO) throws URISyntaxException {
        log.debug("REST request to save FieldOfStudy : {}", fieldOfStudyDTO);
        if (fieldOfStudyDTO.getId() != null) {
            throw new BadRequestAlertException("A new fieldOfStudy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FieldOfStudyDTO result = fieldOfStudyService.save(fieldOfStudyDTO);
        return ResponseEntity.created(new URI("/api/field-of-studies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /field-of-studies} : Updates an existing fieldOfStudy.
     *
     * @param fieldOfStudyDTO the fieldOfStudyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldOfStudyDTO,
     * or with status {@code 400 (Bad Request)} if the fieldOfStudyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fieldOfStudyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/field-of-studies")
    public ResponseEntity<FieldOfStudyDTO> updateFieldOfStudy(@RequestBody FieldOfStudyDTO fieldOfStudyDTO) throws URISyntaxException {
        log.debug("REST request to update FieldOfStudy : {}", fieldOfStudyDTO);
        if (fieldOfStudyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FieldOfStudyDTO result = fieldOfStudyService.save(fieldOfStudyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fieldOfStudyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /field-of-studies} : get all the fieldOfStudies.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fieldOfStudies in body.
     */
    @GetMapping("/field-of-studies")
    public ResponseEntity<List<FieldOfStudyDTO>> getAllFieldOfStudies(Pageable pageable) {
        log.debug("REST request to get a page of FieldOfStudies");
        Page<FieldOfStudyDTO> page = fieldOfStudyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /field-of-studies/:id} : get the "id" fieldOfStudy.
     *
     * @param id the id of the fieldOfStudyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fieldOfStudyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/field-of-studies/{id}")
    public ResponseEntity<FieldOfStudyDTO> getFieldOfStudy(@PathVariable Long id) {
        log.debug("REST request to get FieldOfStudy : {}", id);
        Optional<FieldOfStudyDTO> fieldOfStudyDTO = fieldOfStudyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fieldOfStudyDTO);
    }

    /**
     * {@code DELETE  /field-of-studies/:id} : delete the "id" fieldOfStudy.
     *
     * @param id the id of the fieldOfStudyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/field-of-studies/{id}")
    public ResponseEntity<Void> deleteFieldOfStudy(@PathVariable Long id) {
        log.debug("REST request to delete FieldOfStudy : {}", id);
        fieldOfStudyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
