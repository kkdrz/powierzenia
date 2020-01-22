package pl.edu.pwr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pwr.domain.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
