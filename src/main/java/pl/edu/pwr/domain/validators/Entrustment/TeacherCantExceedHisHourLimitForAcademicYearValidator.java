package pl.edu.pwr.domain.validators.Entrustment;

import pl.edu.pwr.domain.Entrustment;
import pl.edu.pwr.domain.EntrustmentPlan;
import pl.edu.pwr.domain.Teacher;
import pl.edu.pwr.repository.EntrustmentPlanRepository;
import pl.edu.pwr.repository.TeacherRepository;
import pl.edu.pwr.service.BeanUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class TeacherCantExceedHisHourLimitForAcademicYearValidator implements ConstraintValidator<TeacherCantExceedHisHourLimitForAcademicYear, Entrustment> {

    private TeacherRepository teacherRepository;
    private EntrustmentPlanRepository entrustmentPlanRepository;

    @Override
    public void initialize(TeacherCantExceedHisHourLimitForAcademicYear constraintAnnotation) {
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
            entrustmentPlan = entrustmentPlanRepository.getOne(entrustmentPlan.getId());
        }

        Optional<Integer> sumEntrustedHoursForTheYear = teacher.entrustedHoursByYear(entrustmentPlan.getAcademicYear());
        return sumEntrustedHoursForTheYear.map(integer -> finalTeacher.getHourLimit() >= integer).orElse(true);
    }
}

