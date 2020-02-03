package pl.edu.pwr.domain.validators.Entrustment;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = TeacherCantExceedHisHourLimitForAcademicYearValidator.class)
@Target({TYPE})
@Retention(RUNTIME)
public @interface TeacherCantExceedHisHourLimitForAcademicYear {
    String message() default "This teacher will exceed his hours limit for this academic year";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
