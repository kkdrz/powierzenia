package pl.edu.pwr.domain.validators.Entrustment;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = CanEntrustClassToTeacherOnlyOnceValidator.class)
@Target({TYPE})
@Retention(RUNTIME)
public @interface CanEntrustClassToTeacherOnlyOnce {
    String message() default "This class has been entrusted to this teacher already";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
