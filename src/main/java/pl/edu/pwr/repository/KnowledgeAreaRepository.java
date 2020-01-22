package pl.edu.pwr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.domain.KnowledgeArea;


/**
 * Spring Data  repository for the KnowledgeArea entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KnowledgeAreaRepository extends JpaRepository<KnowledgeArea, Long> {

}
