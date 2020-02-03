package pl.edu.pwr.domain.validators.Entrustment;

import pl.edu.pwr.domain.CourseClass;
import pl.edu.pwr.domain.Entrustment;
import pl.edu.pwr.repository.CourseClassRepository;
import pl.edu.pwr.service.BeanUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class SumEntrustmentsHoursCantExceedCourseClassHoursValidator implements ConstraintValidator<SumEntrustmentsHoursCantExceedCourseClassHours, Entrustment> {

    private CourseClassRepository courseClassRepository;

    @Override
    public void initialize(SumEntrustmentsHoursCantExceedCourseClassHours constraintAnnotation) {
        courseClassRepository = BeanUtil.getBean(CourseClassRepository.class);
    }

    @Override
    public boolean isValid(Entrustment entrustment, ConstraintValidatorContext context) {
        CourseClass courseClass = entrustment.getCourseClass();
        if (courseClass.getEntrustments() == null || courseClass.getEntrustments().isEmpty() || courseClass.getHours() == null) {
            courseClass = courseClassRepository.getOne(courseClass.getId());
        }

        if (courseClass.getEntrustments().stream().anyMatch(entrustment1 -> entrustment1.getId() == entrustment.getId())) {
            Optional<Integer> b = courseClass.getEntrustments().stream().map(Entrustment::getHours).reduce(Integer::sum);
            return !b.isPresent() || courseClass.getHours() >= b.get();
        } else {
            Optional<Integer> b = courseClass.getEntrustments().stream().map(Entrustment::getHours).reduce(Integer::sum);
            return !b.isPresent() || courseClass.getHours() >= (b.get() + entrustment.getHours());
        }
    }
}
