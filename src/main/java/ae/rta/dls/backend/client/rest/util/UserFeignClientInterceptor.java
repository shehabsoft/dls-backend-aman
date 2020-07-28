package ae.rta.dls.backend.client.rest.util;


import ae.rta.dls.backend.common.errors.SystemException;
import ae.rta.dls.backend.common.util.CommonUtil;
import ae.rta.dls.backend.common.util.NumberUtil;
import ae.rta.dls.backend.common.util.StringUtil;
import ae.rta.dls.backend.domain.enumeration.sys.ConfigurationKey;
import ae.rta.dls.backend.security.util.SecurityUtils;
import ae.rta.dls.backend.service.dto.ws.rest.RestLogDTO;
import ae.rta.dls.backend.service.ws.rest.RestLogService;
import brave.Tracer;
import feign.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;


/**
 * This class is responsible on logging all feign requests and responses.
 */
@Component
public class UserFeignClientInterceptor extends Logger implements RequestInterceptor {

    /** Authorization Header param name */
    private static final String AUTHORIZATION_HEADER_PARAM_NAME = "Authorization";

    /** Bearer param name */
    private static final String BEARER_PARAM_NAME = "Bearer";

    /** Rest log id param name*/
    private static final String REST_LOG_ID_PARAM_NAME = "REST_LOG_ID";

    /** logger */
    private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserFeignClientInterceptor.class);

    /** Tracer*/
    @Autowired
    private Tracer tracer;

    /** Rest Log Service */
    @Autowired
    private RestLogService restLogService;

    /** Common Util */
    @Autowired
    private CommonUtil commonUtil;

    /**
     * Applies feign client request template.
     *
     * @param template : Request Template
     */
    @Override
    public void apply(RequestTemplate template) {
        SecurityUtils.getCurrentUserJWT()
            .ifPresent(s -> template.header(AUTHORIZATION_HEADER_PARAM_NAME,String.format("%s %s", BEARER_PARAM_NAME, s)));

        logRequest(template);
    }

    /**
     * Logs feign Request.
     *
     * @param requestTemplate Http Request
     */
    private void logRequest(RequestTemplate requestTemplate) {

        if (!isLoggingEnabled()) {
            return;
        }

        try {

            if (requestTemplate == null) {
                throw new SystemException("Feign Request template cannot be null");
            }

            if (requestTemplate.request() == null) {
                throw new SystemException("Feign Request cannot be null");
            }

            if (StringUtil.isBlank(requestTemplate.request().url())) {
                throw new SystemException("Feign Request url cannot be null");
            }

            if (StringUtil.isBlank(requestTemplate.request().method())) {
                throw new SystemException("Feign Request method cannot be null");
            }

            if (tracer == null || tracer.currentSpan() == null || tracer.currentSpan().context() == null) {
                throw new SystemException("tracer cannot be null");
            }

            RestLogDTO restLogDTO = new RestLogDTO();
            restLogDTO.setCorrelationId(tracer.currentSpan().context().traceIdString());

            byte[] requestBody = requestTemplate.request().body();
            restLogDTO.setRequestBody(requestBody != null ? new String(requestBody, StandardCharsets.UTF_8) : "");

            restLogDTO.setRequestUrl(requestTemplate.request().url());
            restLogDTO.setHttpMethod(requestTemplate.request().method());
            restLogDTO.setRestMode(RestLogDTO.RestMode.OUT.value());

            // Add Rest Log..
            restLogDTO = restLogService.save(restLogDTO);

            if (restLogDTO == null) {
                throw new SystemException("restLogDTO cannot be null");
            }

            requestTemplate.header(REST_LOG_ID_PARAM_NAME, StringUtil.getString(restLogDTO.getId()));
        } catch(Exception e) {
            log.error("Logging failed in UserFeignClientInterceptor.logRequest because of the following {}", e.getMessage());
            commonUtil.logError(e);
        }
    }

    /**
     * Logs feign response.
     *
     * @param configKey : config Key
     * @param logLevel : log Level
     * @param response : feign response
     * @param elapsedTime
     *
     * @return feign Response
     */
    @Override
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) {

        if (!isLoggingEnabled()) {
            return response;
        }

        try {

            Long restLogId = extractRestLogId(response);
            if (restLogId == null) {
                throw new SystemException("Rest Log Id cannot be null");
            }

            Optional<RestLogDTO> restLog = restLogService.findOne(restLogId);
            if (!restLog.isPresent()) {
                throw new SystemException("Rest log is not exist for ID = " + restLogId);
            }

            RestLogDTO restLogDTO = restLog.get();

            byte[] responseBody = response.body() != null ? Util.toByteArray(response.body().asInputStream()) : null;

            restLogDTO.setResponseBody(responseBody != null ? StringUtil.toEncodedString(responseBody, StandardCharsets.UTF_8) : "");

            restLogDTO.setHttpStatus(response.status());

            // Update Rest Log Response..
            restLogService.asyncSave(restLogDTO);

            return response.toBuilder().body(responseBody).build();
        } catch(Exception e) {
            log.error("Logging failed in UserFeignClientInterceptor.logAndRebufferResponse because of the following {}", e.getMessage());
            commonUtil.logError(e);
        }
        return response;
    }

    /**
     * Extracts rest log id.
     *
     * @param feignResponse : feign Response
     * @return rest log id.
     */
    private Long extractRestLogId(Response feignResponse) {

        if (feignResponse == null) {
            throw new SystemException("feign Response cannot be null");
        }

        if (feignResponse.request() == null) {
            throw new SystemException("feign request cannot be null");
        }

        if (feignResponse.request().headers() == null) {
            throw new SystemException("feign request header cannot be null");
        }

        if (feignResponse.request().headers().get(REST_LOG_ID_PARAM_NAME) == null ||
            feignResponse.request().headers().get(REST_LOG_ID_PARAM_NAME).isEmpty()) {

            throw new SystemException("rest log id param cannot be null in feign response");
        }

        List <String> restLogIdsList = (List<String>)feignResponse.request().headers().get(REST_LOG_ID_PARAM_NAME);
        return NumberUtil.toLong(restLogIdsList.get(0));
    }


    @Override
    protected void log(String configKey, String format, Object... args) {
        // NOT USED >> we logged the request in apply method due to we need to modify the request header.
    }

    /**
     * Is logging Enabled
     *
     * @return true if logging Is Enabled
     */
    private boolean isLoggingEnabled() {

        try {
            String configurationValue =
                commonUtil.getConfigurationValue(ConfigurationKey.ENABLE_REST_LOGGING.value());

            if (!StringUtil.isEmpty(configurationValue) && configurationValue.equalsIgnoreCase(Boolean.FALSE.toString())) {
                return false;
            }

        } catch(Exception e) {
            log.error("Logging failed in UserFeignClientInterceptor.isLoggingEnabled() because of the following {}", e.getMessage());
            return false;
        }

        return true;
    }
}
