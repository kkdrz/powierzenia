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
import pl.edu.pwr.service.ClassFormService;
import pl.edu.pwr.service.dto.ClassFormDTO;
import pl.edu.pwr.web.rest.errors.BadRequestAlertException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link pl.edu.pwr.domain.ClassForm}.
 */
@RestController
@RequestMapping("/api")
public class ClassFormResource {

    private static final String ENTITY_NAME = "classForm";
    private final Logger log = LoggerFactory.getLogger(ClassFormResource.class);
    private final ClassFormService classFormService;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public ClassFormResource(ClassFormService classFormService) {
        this.classFormService = classFormService;
    }

    /**
     * {@code POST  /class-forms} : Create a new classForm.
     *
     * @param classFormDTO the classFormDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new classFormDTO, or with status {@code 400 (Bad Request)} if the classForm has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/class-forms")
    public ResponseEntity<ClassFormDTO> createClassForm(@RequestBody @Valid ClassFormDTO classFormDTO, BindingResult bindingResult) throws URISyntaxException {
        log.debug("REST request to save ClassForm : {}", classFormDTO);
        if (bindingResult.hasErrors()) {
            throw new BadRequestAlertException(
                bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).reduce("", (s, s2) -> s + "\n" + s2),
                ENTITY_NAME,
                "fields_invalid"
            );
        }
        if (classFormDTO.getId() != null) {
            throw new BadRequestAlertException("A new classForm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClassFormDTO result = classFormService.save(classFormDTO);
        return ResponseEntity.created(new URI("/api/class-forms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /class-forms} : Updates an existing classForm.
     *
     * @param classFormDTO the classFormDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classFormDTO,
     * or with status {@code 400 (Bad Request)} if the classFormDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the classFormDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/class-forms")
    public ResponseEntity<ClassFormDTO> updateClassForm(@RequestBody @Valid ClassFormDTO classFormDTO, BindingResult bindingResult) throws URISyntaxException {
        log.debug("REST request to update ClassForm : {}", classFormDTO);
        if (bindingResult.hasErrors()) {
            throw new BadRequestAlertException(
                bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).reduce("", (s, s2) -> s + "\n" + s2),
                ENTITY_NAME,
                "fields_invalid"
            );
        }
        if (classFormDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClassFormDTO result = classFormService.save(classFormDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, classFormDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /class-forms} : get all the classForms.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classForms in body.
     */
    @GetMapping("/class-forms")
    public ResponseEntity<List<ClassFormDTO>> getAllClassForms(Pageable pageable) {
        log.debug("REST request to get a page of ClassForms");
        Page<ClassFormDTO> page = classFormService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /class-forms/:id} : get the "id" classForm.
     *
     * @param id the id of the classFormDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classFormDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/class-forms/{id}")
    public ResponseEntity<ClassFormDTO> getClassForm(@PathVariable Long id) {
        log.debug("REST request to get ClassForm : {}", id);
        Optional<ClassFormDTO> classFormDTO = classFormService.findOne(id);
        return ResponseUtil.wrapOrNotFound(classFormDTO);
    }

    /**
     * {@code DELETE  /class-forms/:id} : delete the "id" classForm.
     *
     * @param id the id of the classFormDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/class-forms/{id}")
    public ResponseEntity<Void> deleteClassForm(@PathVariable Long id) {
        log.debug("REST request to delete ClassForm : {}", id);
        classFormService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
