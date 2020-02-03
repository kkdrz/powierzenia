package pl.edu.pwr.domain.validators.Entrustment;

import pl.edu.pwr.domain.Entrustment;
import pl.edu.pwr.domain.EntrustmentPlan;
import pl.edu.pwr.domain.Teacher;
import pl.edu.pwr.domain.enumeration.TeacherType;
import pl.edu.pwr.repository.EntrustmentPlanRepository;
import pl.edu.pwr.repository.TeacherRepository;
import pl.edu.pwr.service.BeanUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class TeacherEntrustedHoursCanExceedPensumIfEveryOtherTeacherHasReachedHisPensumValidator
    implements ConstraintValidator<TeacherEntrustedHoursCanExceedPensumIfEveryOtherTeacherHasReachedHisPensum, Entrustment> {

    private TeacherRepository teacherRepository;
    private EntrustmentPlanRepository entrustmentPlanRepository;

    @Override
    public void initialize(TeacherEntrustedHoursCanExceedPensumIfEveryOtherTeacherHasReachedHisPensum constraintAnnotation) {
        teacherRepository = BeanUtil.getBean(TeacherRepository.class);
        entrustmentPlanRepository = BeanUtil.getBean(EntrustmentPlanRepository.class);
    }

    @Override
    public boolean isValid(Entrustment entrustment, ConstraintValidatorContext context) {
        Teacher teacher = entrustment.getTeacher();
        if (teacher.getEntrustments() == null || teacher.getEntrustments().isEmpty()) {
            teacher = teacherRepository.getOne(teacher.getId());
        }
        Teacher finalTeacher = teacher;

        EntrustmentPlan entrustmentPlan = entrustment.getEntrustmentPlan();
        if (entrustmentPlan.getAcademicYear() == null) {
            entrustmentPlan = entrustmentPlanRepository.getOne(entrustment.getEntrustmentPlan().getId());
        }
        EntrustmentPlan finalEntrustmentPlan = entrustmentPlan;

        Optional<Integer> selectedTeacherEntrustedHours = teacher.entrustedHoursByYear(entrustmentPlan.getAcademicYear());
        if (selectedTeacherEntrustedHours.isPresent() && selectedTeacherEntrustedHours.get() > teacher.getPensum()) {
            return teacherRepository.findAll().stream()
                .filter(t -> !t.equals(finalTeacher))
                .filter(t -> t.getType().equals(TeacherType.ACADEMIC_TEACHER) || t.getType().equals(TeacherType.DOCTORATE_STUDENT))
                .allMatch(t -> {
                    Optional<Integer> entrustedHours = t.entrustedHoursByYear(finalEntrustmentPlan.getAcademicYear());
                    return entrustedHours.isPresent() && t.getPensum() <= entrustedHours.get();
                });
        } else return true;
    }
}

