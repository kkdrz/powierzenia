package pl.edu.pwr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.domain.CourseClass;


/**
 * Spring Data  repository for the CourseClass entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseClassRepository extends JpaRepository<CourseClass, Long> {

}
