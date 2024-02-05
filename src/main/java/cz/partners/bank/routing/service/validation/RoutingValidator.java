package cz.partners.bank.routing.service.validation;

import cz.partners.bank.routing.common.exception.ValidationException;
import cz.partners.bank.routing.service.GraphService;
import cz.partners.bank.routing.service.command.RoutingCommand;
import cz.partners.bank.routing.service.vo.Graph;
import cz.partners.bank.routing.service.vo.Node;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Zdenek Benes
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RoutingValidator implements Validator {

    private final MessageSource messageSource;
    private final GraphService graphService;

    @Override
    public boolean supports(Class<?> clazz) {
        return RoutingCommand.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "origin", "origin.empty");
        ValidationUtils.rejectIfEmpty(errors, "destination", "destination.empty");

        RoutingCommand routingCommand = (RoutingCommand) target;
        String origin = routingCommand.getOrigin();
        String destination = routingCommand.getDestination();
        validateInput(errors, "origin", origin);
        validateInput(errors, "destination", destination);

        if (errors.hasErrors()) {
            String errorCode = "routing.input.validation.issue";
            errors.reject(errorCode, messageSource.getMessage(errorCode, new Object[]{}, LocaleContextHolder.getLocale()));
            throw new ValidationException(errors);
        }
    }

    private void validateInput(Errors errors, String field, String fieldValue) {
        if (!StringUtils.isEmpty(fieldValue)) {
            if (fieldValue.length() != 3) {
                String errorCode = "routing.input.validation.size";
                errors.rejectValue(field, errorCode, messageSource.getMessage(errorCode, new Object[]{fieldValue}, LocaleContextHolder.getLocale()));
            } else {
                Graph graph = graphService.initializeGraph();
                Node nodeFromGraph = graph.getNodeFromGraph(fieldValue);
                if (nodeFromGraph == null) {
                    String errorCode = "routing.input.validation.notCountry";
                    errors.rejectValue(field, errorCode, messageSource.getMessage(errorCode, new Object[]{fieldValue}, LocaleContextHolder.getLocale()));
                }
            }
        }
    }

    @Override
    public Errors validateObject(Object target) {
        return Validator.super.validateObject(target);
    }


}
