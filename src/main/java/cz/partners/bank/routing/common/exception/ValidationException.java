package cz.partners.bank.routing.common.exception;

import lombok.Getter;
import org.springframework.validation.Errors;

/**
 * @author Zdenek Benes
 */
@Getter
public class ValidationException extends RuntimeException {

    private final Errors errors;

    public ValidationException(Errors errors) {
        this.errors = errors;
    }

}
