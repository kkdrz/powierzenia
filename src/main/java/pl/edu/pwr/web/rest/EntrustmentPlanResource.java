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
import pl.edu.pwr.service.EntrustmentPlanService;
import pl.edu.pwr.service.dto.EntrustmentPlanDTO;
import pl.edu.pwr.web.rest.errors.BadRequestAlertException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link pl.edu.pwr.domain.EntrustmentPlan}.
 */
@RestController
@RequestMapping("/api")
public class EntrustmentPlanResource {

    private static final String ENTITY_NAME = "entrustmentPlan";
    private final Logger log = LoggerFactory.getLogger(EntrustmentPlanResource.class);
    private final EntrustmentPlanService entrustmentPlanService;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public EntrustmentPlanResource(EntrustmentPlanService entrustmentPlanService) {
        this.entrustmentPlanService = entrustmentPlanService;
    }

    /**
     * {@code POST  /entrustment-plans} : Create a new entrustmentPlan.
     *
     * @param entrustmentPlanDTO the entrustmentPlanDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entrustmentPlanDTO, or with status {@code 400 (Bad Request)} if the entrustmentPlan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/entrustment-plans")
    public ResponseEntity<EntrustmentPlanDTO> createEntrustmentPlan(@RequestBody EntrustmentPlanDTO entrustmentPlanDTO) throws URISyntaxException {
        log.debug("REST request to save EntrustmentPlan : {}", entrustmentPlanDTO);
        if (entrustmentPlanDTO.getId() != null) {
            throw new BadRequestAlertException("A new entrustmentPlan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntrustmentPlanDTO result = entrustmentPlanService.save(entrustmentPlanDTO);
        return ResponseEntity.created(new URI("/api/entrustment-plans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /entrustment-plans} : Updates an existing entrustmentPlan.
     *
     * @param entrustmentPlanDTO the entrustmentPlanDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entrustmentPlanDTO,
     * or with status {@code 400 (Bad Request)} if the entrustmentPlanDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entrustmentPlanDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/entrustment-plans")
    public ResponseEntity<EntrustmentPlanDTO> updateEntrustmentPlan(@RequestBody EntrustmentPlanDTO entrustmentPlanDTO) throws URISyntaxException {
        log.debug("REST request to update EntrustmentPlan : {}", entrustmentPlanDTO);
        if (entrustmentPlanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EntrustmentPlanDTO result = entrustmentPlanService.save(entrustmentPlanDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, entrustmentPlanDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /entrustment-plans} : get all the entrustmentPlans.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entrustmentPlans in body.
     */
    @GetMapping("/entrustment-plans")
    public ResponseEntity<List<EntrustmentPlanDTO>> getAllEntrustmentPlans(Pageable pageable) {
        log.debug("REST request to get a page of EntrustmentPlans");
        Page<EntrustmentPlanDTO> page = entrustmentPlanService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /entrustment-plans/:id} : get the "id" entrustmentPlan.
     *
     * @param id the id of the entrustmentPlanDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entrustmentPlanDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entrustment-plans/{id}")
    public ResponseEntity<EntrustmentPlanDTO> getEntrustmentPlan(@PathVariable Long id) {
        log.debug("REST request to get EntrustmentPlan : {}", id);
        Optional<EntrustmentPlanDTO> entrustmentPlanDTO = entrustmentPlanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entrustmentPlanDTO);
    }

    /**
     * {@code DELETE  /entrustment-plans/:id} : delete the "id" entrustmentPlan.
     *
     * @param id the id of the entrustmentPlanDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/entrustment-plans/{id}")
    public ResponseEntity<Void> deleteEntrustmentPlan(@PathVariable Long id) {
        log.debug("REST request to delete EntrustmentPlan : {}", id);
        entrustmentPlanService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
