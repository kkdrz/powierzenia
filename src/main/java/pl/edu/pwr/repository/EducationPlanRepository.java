package pl.edu.pwr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.domain.EducationPlan;


/**
 * Spring Data  repository for the EducationPlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EducationPlanRepository extends JpaRepository<EducationPlan, Long> {

}
