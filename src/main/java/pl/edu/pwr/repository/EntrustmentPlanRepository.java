package pl.edu.pwr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.domain.EntrustmentPlan;


/**
 * Spring Data  repository for the EntrustmentPlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntrustmentPlanRepository extends JpaRepository<EntrustmentPlan, Long> {

}
