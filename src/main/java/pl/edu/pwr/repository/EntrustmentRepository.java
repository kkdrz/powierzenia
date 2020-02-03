package pl.edu.pwr.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.domain.Entrustment;


/**
 * Spring Data  repository for the Entrustment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntrustmentRepository extends JpaRepository<Entrustment, Long> {

    Page<Entrustment> findAllByTeacher_Id(Pageable pageable, Long teacherId);

}
