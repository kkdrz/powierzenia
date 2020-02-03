package pl.edu.pwr.domain.validators.CourseClass;

import pl.edu.pwr.domain.Course;
import pl.edu.pwr.domain.CourseClass;
import pl.edu.pwr.domain.enumeration.ClassFormType;
import pl.edu.pwr.repository.CourseRepository;
import pl.edu.pwr.service.BeanUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CourseClassFormDistinctInCourseValidator implements ConstraintValidator<CourseDoesntHaveAClassWithThisClassForm, CourseClass> {

    private CourseRepository courseRepository;

    @Override
    public void initialize(CourseDoesntHaveAClassWithThisClassForm constraintAnnotation) {
        courseRepository = BeanUtil.getBean(CourseRepository.class);
    }

    @Override
    public boolean isValid(CourseClass dto, ConstraintValidatorContext context) {
        Course course = dto.getCourse();
        Set<CourseClass> classes = course.getClasses();
        if (classes.isEmpty()) {
            classes = courseRepository.findOneWithEagerRelationships(dto.getCourse().getId()).map(Course::getClasses).orElse(null);
        }
        if (classes == null || classes.isEmpty()) {
            return true;
        }

        List<ClassFormType> forms = classes.stream().map(CourseClass::getForm).collect(Collectors.toList());

        if (dto.getId() == null || classes.stream().noneMatch(courseClass -> courseClass.getId().equals(dto.getId()))) {
            forms.add(dto.getForm());
        }

        return forms.stream().filter(classFormType -> classFormType.equals(dto.getForm())).count() == 1;
    }
}

