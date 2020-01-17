package edu.psi.powierzenia.repository;

import edu.psi.powierzenia.domain.FieldOfStudy;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FieldOfStudy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FieldOfStudyRepository extends JpaRepository<FieldOfStudy, Long> {

}
