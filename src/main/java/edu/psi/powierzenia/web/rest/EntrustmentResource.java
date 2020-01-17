package edu.psi.powierzenia.web.rest;

import edu.psi.powierzenia.service.EntrustmentService;
import edu.psi.powierzenia.web.rest.errors.BadRequestAlertException;
import edu.psi.powierzenia.service.dto.EntrustmentDTO;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link edu.psi.powierzenia.domain.Entrustment}.
 */
@RestController
@RequestMapping("/api")
public class EntrustmentResource {

    private final Logger log = LoggerFactory.getLogger(EntrustmentResource.class);

    private static final String ENTITY_NAME = "entrustment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntrustmentService entrustmentService;

    public EntrustmentResource(EntrustmentService entrustmentService) {
        this.entrustmentService = entrustmentService;
    }

    /**
     * {@code POST  /entrustments} : Create a new entrustment.
     *
     * @param entrustmentDTO the entrustmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entrustmentDTO, or with status {@code 400 (Bad Request)} if the entrustment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/entrustments")
    public ResponseEntity<EntrustmentDTO> createEntrustment(@RequestBody EntrustmentDTO entrustmentDTO) throws URISyntaxException {
        log.debug("REST request to save Entrustment : {}", entrustmentDTO);
        if (entrustmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new entrustment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntrustmentDTO result = entrustmentService.save(entrustmentDTO);
        return ResponseEntity.created(new URI("/api/entrustments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
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
    public ResponseEntity<EntrustmentDTO> updateEntrustment(@RequestBody EntrustmentDTO entrustmentDTO) throws URISyntaxException {
        log.debug("REST request to update Entrustment : {}", entrustmentDTO);
        if (entrustmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EntrustmentDTO result = entrustmentService.save(entrustmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entrustmentDTO.getId().toString()))
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
        Page<EntrustmentDTO> page = entrustmentService.findAll(pageable);
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
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
