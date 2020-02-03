package pl.edu.pwr.domain.validators.Entrustment;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = AcademicOrDoctorantCantExceedHisPensumIfNotAgreedToValidator.class)
@Target({TYPE})
@Retention(RUNTIME)
public @interface AcademicOrDoctorantCantExceedHisPensumIfNotAgreedTo {
    String message() default "This teacher will exceed his allowed additional pensum for this academic year without agreeing to it";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
