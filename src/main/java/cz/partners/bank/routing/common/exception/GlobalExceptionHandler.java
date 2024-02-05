package cz.partners.bank.routing.common.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Zdenek Benes
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail notFoundException(NotFoundException exception) {
        String message = exception.getMessage();
        log.error(message);
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle(message);
        problemDetail.setDetail(message);
        return problemDetail;
    }

    @ExceptionHandler(ValidationException.class)
    public ProblemDetail validationException(ValidationException exception) {
        Errors errors = exception.getErrors();
        String validationException = "Validation exception";
        ObjectError globalError = errors.getGlobalError();
        if (globalError != null) {
            validationException = globalError.getDefaultMessage();
            log.error(validationException);
        }
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        Map<String, Object> properties = new HashMap<>();
        if (errors.hasFieldErrors()) {
            properties = errors.getFieldErrors().stream()
                .collect(Collectors.toMap(
                    FieldError::getField, fieldError -> {
                        String defaultMessage = fieldError.getDefaultMessage();
                        return Objects.requireNonNullElse(defaultMessage, "");
                    }
                ));
        }
        problemDetail.setTitle(validationException);
        problemDetail.setProperties(properties);
        return problemDetail;
    }
}
