package pl.edu.pwr.domain.validators.Entrustment;

import pl.edu.pwr.domain.CourseClass;
import pl.edu.pwr.domain.Entrustment;
import pl.edu.pwr.domain.Teacher;
import pl.edu.pwr.repository.CourseClassRepository;
import pl.edu.pwr.repository.TeacherRepository;
import pl.edu.pwr.service.BeanUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CanEntrustClassToTeacherOnlyOnceValidator implements ConstraintValidator<CanEntrustClassToTeacherOnlyOnce, Entrustment> {

    private CourseClassRepository courseClassRepository;
    private TeacherRepository teacherRepository;

    @Override
    public void initialize(CanEntrustClassToTeacherOnlyOnce constraintAnnotation) {
        teacherRepository = BeanUtil.getBean(TeacherRepository.class);
        courseClassRepository = BeanUtil.getBean(CourseClassRepository.class);

    }

    @Override
    public boolean isValid(Entrustment entrustment, ConstraintValidatorContext context) {
        CourseClass courseClass = entrustment.getCourseClass();
        if (courseClass.getEntrustments() == null || courseClass.getEntrustments().isEmpty()) {
            courseClass = courseClassRepository.getOne(courseClass.getId());
        }

        Teacher teacher = entrustment.getTeacher();
        if (teacher.getEntrustments() == null || teacher.getEntrustments().isEmpty()) {
            teacher = teacherRepository.getOne(teacher.getId());
        }

        Teacher finalTeacher = teacher;

        if (courseClass.getEntrustments().contains(entrustment)) {
            return courseClass.getEntrustments().stream().map(Entrustment::getTeacher).filter(teacher1 -> teacher1.equals(finalTeacher)).count() == 1;
        } else {
            return courseClass.getEntrustments().stream().map(Entrustment::getTeacher).noneMatch(teacher1 -> teacher1.equals(finalTeacher));
        }
    }
}



