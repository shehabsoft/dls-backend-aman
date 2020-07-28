package ae.rta.dls.backend.web.rest.util;

import ae.rta.dls.backend.common.errors.SystemException;
import ae.rta.dls.backend.common.util.StringUtil;
import ae.rta.dls.backend.config.Constants;
import ae.rta.dls.backend.config.EnvironmentConstants;
import ae.rta.dls.backend.web.rest.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.DefaultProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.violations.ConstraintViolationProblem;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures.
 * The error response follows RFC7807 - Problem Details for HTTP APIs (https://tools.ietf.org/html/rfc7807)
 */
@ControllerAdvice
public class ExceptionTranslator implements ProblemHandling {

    @Autowired(required = false)
    private HttpServletRequest httpRequest;

    @Autowired
    private Environment env;

    @Autowired
    private MessageSource messageSource;

    private static final String PATH_KEY = "path";
    private static final String VIOLATIONS_KEY = "violations";
    private static final String ERROR_CATEGORY = "errorCategory";
    private static final String ERROR_TYPE = "errorType";
    private static final String VALIDATION_TYPE = "validationType";
    private static final String REDIRECT_URI = "redirectUri";


    /**
     * Post-process the Problem payload to add the message key for the front-end if needed
     */
    @Override
    public ResponseEntity<Problem> process(@Nullable ResponseEntity<Problem> entity, NativeWebRequest request) {
        if (entity == null) {
            return entity;
        }
        Problem problem = entity.getBody();

        if (!(problem instanceof ConstraintViolationProblem || problem instanceof DefaultProblem)) {
            return entity;
        }

        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        ProblemBuilder builder = Problem.builder()
            .with(ERROR_CATEGORY, ErrorConstants.CATEGORY_SYSTEM)
            .withStatus(problem.getStatus())
            .with(PATH_KEY, request.getNativeRequest(HttpServletRequest.class).getRequestURI());

        if (httpRequest.getAttribute(Constants.SYSTEM_TRACE_ID) != null) {
            builder
                .with(Constants.SYSTEM_TRACE_ID, httpRequest.getAttribute(Constants.SYSTEM_TRACE_ID));
        }

        builder
            .withCause(((DefaultProblem) problem).getCause())
            .withInstance(problem.getInstance());

        problem.getParameters().forEach(builder::with);

        if (httpRequest.getAttribute(Constants.SYSTEM_TRACE_ID) != null) {
            builder
                .withDetail(activeProfiles.contains(EnvironmentConstants.SPRING_PROFILE_DEVELOPMENT) ? problem.getDetail() :
                    "You can track your problem with support team using system trace reference number: " +
                        httpRequest.getAttribute(Constants.SYSTEM_TRACE_ID));
        } else {
            builder
                .withDetail(problem.getDetail());

            if (!StringUtil.isBlank(problem.getDetail()) && problem.getDetail().contains("java.time.format.DateTimeParseException")
                && problem.getDetail().contains("java.time.LocalDate:")) {
                return handleDateParseException(new DateTimeParseException("", "", 1), request);
            } else if (!StringUtil.isBlank(problem.getDetail()) && problem.getDetail().contains("java.time.format.DateTimeParseException")
                && problem.getDetail().contains("java.time.LocalDateTime:")) {
                return handleDateTimeParseException(new DateTimeParseException("", "", 1), request);
            }
        }

        return new ResponseEntity<>(builder.build(), entity.getHeaders(), entity.getStatusCode());
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleBusinessRuleAlertException(BusinessRuleAlertException ex, NativeWebRequest request) {
        Problem problem = Problem.builder()
            .with(ERROR_CATEGORY, ErrorConstants.CATEGORY_BR)
            .with(VIOLATIONS_KEY, ex.getParameters().values())
            .withStatus(Status.ACCEPTED)
            .build();
        return create(ex, problem, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleValidationException(ValidationException ex, NativeWebRequest request) {

        String messageEn = messageSource.getMessage(ex.getMessageKey(), ex.getParameters(), Locale.ENGLISH);
        String messageAr = messageSource.getMessage(ex.getMessageKey(), ex.getParameters(), new Locale("ar"));

        Problem problem = Problem.builder()
            .with(REDIRECT_URI, ex.getRedirectionUri() != null ? ex.getRedirectionUri() : "")
            .with(ERROR_CATEGORY, ErrorConstants.CATEGORY_VALIDATION)
            .with(ERROR_TYPE, ex.getType())
            .with(VALIDATION_TYPE, ex.getValidationType() != null ? ex.getValidationType().value() : ValidationType.ERROR.value())
            .with("messageEn", messageEn)
            .with("messageAr", messageAr)
            .withStatus(Status.BAD_REQUEST)
            .build();
        return create(ex, problem, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleBadRequestException(BadRequestAlertException ex, NativeWebRequest request) {

        Problem problem = Problem.builder()
            .with(ERROR_CATEGORY, ErrorConstants.CATEGORY_BAD_REQUEST)
            .withStatus(Status.BAD_REQUEST)
            .withDetail(ex.getMessage())
            .build();
        return create(ex, problem, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleAuthenticationException(AuthenticationAlertException ex, NativeWebRequest request) {

        Problem problem = Problem.builder()
            .with(ERROR_CATEGORY, ErrorConstants.CATEGORY_AUTHENTICATION)
            .withStatus(Status.UNAUTHORIZED)
            .build();
        return create(ex, problem, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleConcurrencyFailure(ConcurrencyFailureException ex, NativeWebRequest request) {
        Problem problem = Problem.builder()
            .with(ERROR_CATEGORY, ErrorConstants.CATEGORY_SYSTEM)
            .withStatus(Status.CONFLICT)
            .withDetail("Entity cannot be modified. It has been modified by another party")
            .build();
        return create(ex, problem, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleSystemException(SystemException ex, NativeWebRequest request) {
        Problem problem = Problem.builder()
            .with(ERROR_CATEGORY, ErrorConstants.CATEGORY_SYSTEM)
            .withDetail(ex.getMessage())
            .withStatus(ex.getStatus())
            .build();
        return create(ex, problem, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleAnyException(Exception ex, NativeWebRequest request) {

        Problem problem = Problem.builder()
            .with(ERROR_CATEGORY, ErrorConstants.CATEGORY_SYSTEM)
            .withDetail(ex.getMessage())
            .withStatus(Status.INTERNAL_SERVER_ERROR)
            .build();
        return create(ex, problem, request);
    }

    private ResponseEntity<Problem> handleDateParseException(DateTimeParseException ex, NativeWebRequest request) {
        Problem problem = Problem.builder()
            .with(ERROR_CATEGORY, ErrorConstants.CATEGORY_SYSTEM)
            .withDetail("Invalid date format, a valid date format must be dd-MM-yyyy")
            .build();
        return create(ex, problem, request);
    }

    private ResponseEntity<Problem> handleDateTimeParseException(DateTimeParseException ex, NativeWebRequest request) {
        Problem problem = Problem.builder()
            .with(ERROR_CATEGORY, ErrorConstants.CATEGORY_SYSTEM)
            .withDetail("Invalid DateTime format, a valid DateTime format must be dd-MM-yyyyThh:mm:ss")
            .build();
        return create(ex, problem, request);
    }
}
