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
import pl.edu.pwr.service.KnowledgeAreaService;
import pl.edu.pwr.service.dto.KnowledgeAreaDTO;
import pl.edu.pwr.web.rest.errors.BadRequestAlertException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link pl.edu.pwr.domain.KnowledgeArea}.
 */
@RestController
@RequestMapping("/api")
public class KnowledgeAreaResource {

    private static final String ENTITY_NAME = "knowledgeArea";
    private final Logger log = LoggerFactory.getLogger(KnowledgeAreaResource.class);
    private final KnowledgeAreaService knowledgeAreaService;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public KnowledgeAreaResource(KnowledgeAreaService knowledgeAreaService) {
        this.knowledgeAreaService = knowledgeAreaService;
    }

    /**
     * {@code POST  /knowledge-areas} : Create a new knowledgeArea.
     *
     * @param knowledgeAreaDTO the knowledgeAreaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new knowledgeAreaDTO, or with status {@code 400 (Bad Request)} if the knowledgeArea has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/knowledge-areas")
    public ResponseEntity<KnowledgeAreaDTO> createKnowledgeArea(@RequestBody @Valid KnowledgeAreaDTO knowledgeAreaDTO, BindingResult bindingResult) throws URISyntaxException {
        log.debug("REST request to save KnowledgeArea : {}", knowledgeAreaDTO);
        if (bindingResult.hasErrors()) {
            throw new BadRequestAlertException(
                bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).reduce("", (s, s2) -> s + "\n" + s2),
                ENTITY_NAME,
                "fields_invalid"
            );
        }
        if (knowledgeAreaDTO.getId() != null) {
            throw new BadRequestAlertException("A new knowledgeArea cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KnowledgeAreaDTO result = knowledgeAreaService.save(knowledgeAreaDTO);
        return ResponseEntity.created(new URI("/api/knowledge-areas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /knowledge-areas} : Updates an existing knowledgeArea.
     *
     * @param knowledgeAreaDTO the knowledgeAreaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated knowledgeAreaDTO,
     * or with status {@code 400 (Bad Request)} if the knowledgeAreaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the knowledgeAreaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/knowledge-areas")
    public ResponseEntity<KnowledgeAreaDTO> updateKnowledgeArea(@RequestBody @Valid KnowledgeAreaDTO knowledgeAreaDTO, BindingResult bindingResult) throws URISyntaxException {
        log.debug("REST request to update KnowledgeArea : {}", knowledgeAreaDTO);
        if (bindingResult.hasErrors()) {
            throw new BadRequestAlertException(
                bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).reduce("", (s, s2) -> s + "\n" + s2),
                ENTITY_NAME,
                "fields_invalid"
            );
        }
        if (knowledgeAreaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        KnowledgeAreaDTO result = knowledgeAreaService.save(knowledgeAreaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, knowledgeAreaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /knowledge-areas} : get all the knowledgeAreas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of knowledgeAreas in body.
     */
    @GetMapping("/knowledge-areas")
    public ResponseEntity<List<KnowledgeAreaDTO>> getAllKnowledgeAreas(Pageable pageable) {
        log.debug("REST request to get a page of KnowledgeAreas");
        Page<KnowledgeAreaDTO> page = knowledgeAreaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /knowledge-areas/:id} : get the "id" knowledgeArea.
     *
     * @param id the id of the knowledgeAreaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the knowledgeAreaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/knowledge-areas/{id}")
    public ResponseEntity<KnowledgeAreaDTO> getKnowledgeArea(@PathVariable Long id) {
        log.debug("REST request to get KnowledgeArea : {}", id);
        Optional<KnowledgeAreaDTO> knowledgeAreaDTO = knowledgeAreaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(knowledgeAreaDTO);
    }

    /**
     * {@code DELETE  /knowledge-areas/:id} : delete the "id" knowledgeArea.
     *
     * @param id the id of the knowledgeAreaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/knowledge-areas/{id}")
    public ResponseEntity<Void> deleteKnowledgeArea(@PathVariable Long id) {
        log.debug("REST request to delete KnowledgeArea : {}", id);
        knowledgeAreaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
