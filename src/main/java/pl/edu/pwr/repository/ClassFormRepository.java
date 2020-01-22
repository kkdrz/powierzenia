package pl.edu.pwr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.domain.ClassForm;


/**
 * Spring Data  repository for the ClassForm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassFormRepository extends JpaRepository<ClassForm, Long> {

}
