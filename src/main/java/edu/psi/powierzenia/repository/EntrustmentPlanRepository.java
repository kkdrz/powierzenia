package edu.psi.powierzenia.repository;

import edu.psi.powierzenia.domain.EntrustmentPlan;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EntrustmentPlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntrustmentPlanRepository extends JpaRepository<EntrustmentPlan, Long> {

}
