package pl.edu.pwr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.domain.FieldOfStudy;


/**
 * Spring Data  repository for the FieldOfStudy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FieldOfStudyRepository extends JpaRepository<FieldOfStudy, Long> {

}
