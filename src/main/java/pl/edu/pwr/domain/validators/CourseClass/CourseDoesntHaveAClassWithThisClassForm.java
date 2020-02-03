package pl.edu.pwr.domain.validators.CourseClass;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = CourseClassFormDistinctInCourseValidator.class)
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface CourseDoesntHaveAClassWithThisClassForm {
    String message() default "The chosen course already has a class with this class form defined";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
