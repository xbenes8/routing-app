package cz.partners.bank.routing.common.exception;

import lombok.Getter;

/**
 * @author Zdenek Benes
 */
@Getter
public class NotFoundException extends RuntimeException {

    private final String message;

    public NotFoundException(String message) {
        super(message);
        this.message = message;
    }

}
