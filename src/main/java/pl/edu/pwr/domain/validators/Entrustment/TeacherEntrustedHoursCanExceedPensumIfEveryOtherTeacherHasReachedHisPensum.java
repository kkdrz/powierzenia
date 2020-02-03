package pl.edu.pwr.domain.validators.Entrustment;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = TeacherEntrustedHoursCanExceedPensumIfEveryOtherTeacherHasReachedHisPensumValidator.class)
@Target({TYPE})
@Retention(RUNTIME)
public @interface TeacherEntrustedHoursCanExceedPensumIfEveryOtherTeacherHasReachedHisPensum {
    String message() default "This teacher cannot exceed his pensum if there are other Academic teachers or Doctorate students that haven't reached their pensum";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
