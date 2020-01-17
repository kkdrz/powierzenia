package edu.psi.powierzenia.repository;

import edu.psi.powierzenia.domain.EducationPlan;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EducationPlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EducationPlanRepository extends JpaRepository<EducationPlan, Long> {

}
