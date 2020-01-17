package edu.psi.powierzenia.repository;

import edu.psi.powierzenia.domain.CourseClass;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CourseClass entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseClassRepository extends JpaRepository<CourseClass, Long> {

}
