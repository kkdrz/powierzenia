package pl.edu.pwr.web.rest;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.edu.pwr.web.rest.errors.BadRequestAlertException;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({RollbackException.class, BadRequestAlertException.class})
    protected ResponseEntity<Object> handleConflict(Exception ex, WebRequest request) {
        if (ex instanceof TransactionSystemException && ex.getCause() instanceof RollbackException && ex.getCause().getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException cvex = (ConstraintViolationException) ex.getCause().getCause();
            StringBuilder responseBuilder = new StringBuilder();

            for (ConstraintViolation<?> violation :
                cvex.getConstraintViolations()) {
                responseBuilder.append(violation.getMessage()).append("\n");
            }

            return handleExceptionInternal(ex, responseBuilder.toString(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
        } else if (ex instanceof RollbackException && ex.getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException cvex = (ConstraintViolationException) ex.getCause();

            StringBuilder responseBuilder = new StringBuilder();

            for (ConstraintViolation<?> violation :
                cvex.getConstraintViolations()) {
                responseBuilder.append(violation.getMessage()).append("\n");
            }

            return handleExceptionInternal(ex, responseBuilder.toString(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
        } else if (ex instanceof BadRequestAlertException) {
            String response = ex.getMessage();
            return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
        } else {
            return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
        }
    }
}
