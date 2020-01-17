package edu.psi.powierzenia.repository;

import edu.psi.powierzenia.domain.Entrustment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Entrustment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntrustmentRepository extends JpaRepository<Entrustment, Long> {

}
