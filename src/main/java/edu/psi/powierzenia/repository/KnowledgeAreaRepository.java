package edu.psi.powierzenia.repository;

import edu.psi.powierzenia.domain.KnowledgeArea;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the KnowledgeArea entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KnowledgeAreaRepository extends JpaRepository<KnowledgeArea, Long> {

}
