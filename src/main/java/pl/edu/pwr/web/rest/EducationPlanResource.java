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
import pl.edu.pwr.service.EducationPlanService;
import pl.edu.pwr.service.dto.EducationPlanDTO;
import pl.edu.pwr.web.rest.errors.BadRequestAlertException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link pl.edu.pwr.domain.EducationPlan}.
 */
@RestController
@RequestMapping("/api")
public class EducationPlanResource {

    private static final String ENTITY_NAME = "educationPlan";
    private final Logger log = LoggerFactory.getLogger(EducationPlanResource.class);
    private final EducationPlanService educationPlanService;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public EducationPlanResource(EducationPlanService educationPlanService) {
        this.educationPlanService = educationPlanService;
    }

    /**
     * {@code POST  /education-plans} : Create a new educationPlan.
     *
     * @param educationPlanDTO the educationPlanDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new educationPlanDTO, or with status {@code 400 (Bad Request)} if the educationPlan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/education-plans")
    public ResponseEntity<EducationPlanDTO> createEducationPlan(@RequestBody @Valid EducationPlanDTO educationPlanDTO, BindingResult bindingResult) throws URISyntaxException {
        log.debug("REST request to save EducationPlan : {}", educationPlanDTO);
        if (bindingResult.hasErrors()) {
            throw new BadRequestAlertException(
                bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).reduce("", (s, s2) -> s + "\n" + s2),
                ENTITY_NAME,
                "fields_invalid"
            );
        }
        if (educationPlanDTO.getId() != null) {
            throw new BadRequestAlertException("A new educationPlan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EducationPlanDTO result = educationPlanService.save(educationPlanDTO);
        return ResponseEntity.created(new URI("/api/education-plans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /education-plans} : Updates an existing educationPlan.
     *
     * @param educationPlanDTO the educationPlanDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated educationPlanDTO,
     * or with status {@code 400 (Bad Request)} if the educationPlanDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the educationPlanDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/education-plans")
    public ResponseEntity<EducationPlanDTO> updateEducationPlan(@RequestBody @Valid EducationPlanDTO educationPlanDTO, BindingResult bindingResult) throws URISyntaxException {
        log.debug("REST request to update EducationPlan : {}", educationPlanDTO);
        if (bindingResult.hasErrors()) {
            throw new BadRequestAlertException(
                bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).reduce("", (s, s2) -> s + "\n" + s2),
                ENTITY_NAME,
                "fields_invalid"
            );
        }
        if (educationPlanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EducationPlanDTO result = educationPlanService.save(educationPlanDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, educationPlanDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /education-plans} : get all the educationPlans.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of educationPlans in body.
     */
    @GetMapping("/education-plans")
    public ResponseEntity<List<EducationPlanDTO>> getAllEducationPlans(Pageable pageable) {
        log.debug("REST request to get a page of EducationPlans");
        Page<EducationPlanDTO> page = educationPlanService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /education-plans/:id} : get the "id" educationPlan.
     *
     * @param id the id of the educationPlanDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the educationPlanDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/education-plans/{id}")
    public ResponseEntity<EducationPlanDTO> getEducationPlan(@PathVariable Long id) {
        log.debug("REST request to get EducationPlan : {}", id);
        Optional<EducationPlanDTO> educationPlanDTO = educationPlanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(educationPlanDTO);
    }

    /**
     * {@code DELETE  /education-plans/:id} : delete the "id" educationPlan.
     *
     * @param id the id of the educationPlanDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/education-plans/{id}")
    public ResponseEntity<Void> deleteEducationPlan(@PathVariable Long id) {
        log.debug("REST request to delete EducationPlan : {}", id);
        educationPlanService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
