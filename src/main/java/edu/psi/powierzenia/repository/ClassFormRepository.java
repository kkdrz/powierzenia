package edu.psi.powierzenia.repository;

import edu.psi.powierzenia.domain.ClassForm;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ClassForm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassFormRepository extends JpaRepository<ClassForm, Long> {

}
