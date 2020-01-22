package pl.edu.pwr.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.domain.Teacher;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Teacher entity.
 */
@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    @Query(value = "select distinct teacher from Teacher teacher left join fetch teacher.allowedClassForms left join fetch teacher.knowledgeAreas left join fetch teacher.preferedCourses",
        countQuery = "select count(distinct teacher) from Teacher teacher")
    Page<Teacher> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct teacher from Teacher teacher left join fetch teacher.allowedClassForms left join fetch teacher.knowledgeAreas left join fetch teacher.preferedCourses")
    List<Teacher> findAllWithEagerRelationships();

    @Query("select teacher from Teacher teacher left join fetch teacher.allowedClassForms left join fetch teacher.knowledgeAreas left join fetch teacher.preferedCourses where teacher.id =:id")
    Optional<Teacher> findOneWithEagerRelationships(@Param("id") Long id);

}
