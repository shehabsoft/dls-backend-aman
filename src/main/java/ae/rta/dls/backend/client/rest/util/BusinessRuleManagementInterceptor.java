package ae.rta.dls.backend.client.rest.util;

import ae.rta.dls.backend.common.util.StringUtil;
import ae.rta.dls.backend.domain.enumeration.sys.ConfigurationKey;
import ae.rta.dls.backend.service.dto.sys.ErrorLogDTO;
import ae.rta.dls.backend.service.ws.rest.BrmRestLogService;
import ae.rta.dls.backend.service.dto.ws.rest.BrmRestLogDTO;
import ae.rta.dls.backend.service.dto.sys.ApplicationConfigurationDTO;
import ae.rta.dls.backend.service.sys.ApplicationConfigurationService;
import ae.rta.dls.backend.service.sys.ErrorLogService;
import ae.rta.dls.backend.web.rest.errors.BadRequestAlertException;
import brave.Tracer;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class BusinessRuleManagementInterceptor implements ClientHttpRequestInterceptor {

    private final Tracer tracer;
    private final BrmRestLogService brmRestLogService;
    private final ErrorLogService errorLogService;
    private final ApplicationConfigurationService applicationConfigurationService;

    private final String brmContextRoot;

    public BusinessRuleManagementInterceptor(String brmContextRoot,
                                             Tracer tracer,
                                             BrmRestLogService brmRestLogService,
                                             ApplicationConfigurationService applicationConfigurationService,
                                             ErrorLogService errorLogService) {
        this.brmContextRoot = brmContextRoot;
        this.tracer = tracer;
        this.brmRestLogService = brmRestLogService;
        this.applicationConfigurationService = applicationConfigurationService;
        this.errorLogService = errorLogService;
    }

    private final Logger log = LoggerFactory.getLogger(BusinessRuleManagementInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        BrmRestLogDTO dto = null;
        try {
            if(!StringUtil.isEmpty(brmContextRoot) && !request.getURI().toString().startsWith(brmContextRoot)) {
                throw new BadRequestAlertException("This client only for BRM Clients !!","","");
            }
            // Log Request..
            dto = logRequest(request,body);

            ClientHttpResponse response = execution.execute(request, body);

            if(dto != null && dto.getId() != null) {
                // Log Response..
                logResponse(response,dto);
            }
            return response;
        } catch(Exception e) {
            logError(e);
            if(dto != null && dto.getId() != null) {
                // Log Response..
                logErrorResponse(e,dto);
            }
            throw e;
        }
    }

    /**
     * Log Request And Response
     *
     * @param request Http Request
     * @param body Request Body
     * @throws IOException
     */
    private BrmRestLogDTO logRequest(HttpRequest request, byte[] body) {
        try {
            if(!isBrmLoggingEnabled()) {
                return null;
            }
            BrmRestLogDTO dto = new BrmRestLogDTO();
            dto.setCorrelationId(tracer.currentSpan().context().traceIdString());
            dto.setRequestBody(new String(body, StandardCharsets.UTF_8));
            dto.setRequestUrl(request.getURI().toString());
            dto.setHttpMethod(request.getMethod().toString());

            // Add Rest Log..
            dto = brmRestLogService.asyncSave(dto);

            return dto;

        } catch(Exception e) {
            log.error("Logging failed in BusinessRuleManagementInterceptor.logRequest because of the following {}", e.getMessage());
            logError(e);
        }
        return null;
    }

    /**
     * Log Response
     *
     * @param response Client Http Response
     * @throws IOException
     */
    private void logResponse(ClientHttpResponse response,BrmRestLogDTO dto) {
        try {
            if(!isBrmLoggingEnabled()) {
                return;
            }

            StringBuilder inputStringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), StandardCharsets.UTF_8));
            String line = bufferedReader.readLine();
            while (line != null) {
                inputStringBuilder.append(line);
                inputStringBuilder.append('\n');
                line = bufferedReader.readLine();
            }

            dto.setResponseBody(inputStringBuilder.toString());
            dto.setHttpStatus(response.getStatusCode().value());

            // Add Rest Log..
            brmRestLogService.asyncSave(dto);

        } catch(Exception e) {
            log.error("Logging failed in BusinessRuleManagementInterceptor.logResponse because of the following {}", e.getMessage());
            logError(e);
        }
    }

    /**
     * Log Error Response..
     *
     * @param ex Exception
     * @param dto Business Rule Management Rest Log DTO
     *
     * @throws IOException
     */
    private void logErrorResponse(Exception ex,BrmRestLogDTO dto) {
        try {
            if(!isBrmLoggingEnabled()) {
                return;
            }
            dto.setResponseBody(ExceptionUtils.getStackTrace(ex));
            dto.setHttpStatus(500);

            // Add Rest Log..
            brmRestLogService.asyncSave(dto);

        } catch(Exception e) {
            log.error("Logging failed in BusinessRuleManagementInterceptor.logResponse because of the following {}", e.getMessage());
            logError(e);
        }
    }

    /**
     * Is logging Enabled
     *
     * @return true if logging Is Enabled
     */
    public boolean isBrmLoggingEnabled() {
        try {
            Optional<ApplicationConfigurationDTO> applicationConfiguration =
                    applicationConfigurationService.getConfiguration(ConfigurationKey.ENABLE_BRM_REST_LOGGING.value());

            if(applicationConfiguration.isPresent()
                    && !StringUtil.isEmpty(applicationConfiguration.get().getConfigValue())
                    && applicationConfiguration.get().getConfigValue().equalsIgnoreCase(Boolean.FALSE.toString()))  {
                return false;
            }
        } catch(Exception e) {
            log.error("Logging failed in BusinessRuleManagementInterceptor.isBrmLoggingEnabled() because of the following {}", e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Log methods throwing exceptions into database.
     *
     * @param e exception
     */
    private Long logError (Throwable e) {
        try {
            ErrorLogDTO errorLogDTO = new ErrorLogDTO();
            errorLogDTO.setMessage(ExceptionUtils.getStackTrace(e));
            errorLogDTO.setCause(e.getMessage());
            errorLogDTO.setSource(e.getCause() != null ? e.getCause().getMessage() : e.getMessage());

            errorLogDTO = errorLogService.save(errorLogDTO);
            Optional<ErrorLogDTO> optionalErrorLog = Optional.ofNullable(errorLogDTO);
            Long errorLogId = null;
            if (optionalErrorLog.isPresent()) {
                errorLogId = optionalErrorLog.get().getId();
            }
            return errorLogId;
        } catch (Exception exp) {
            log.error("Error while adding error log in BusinessRuleManagementInterceptor logError because of the following {}", exp.getMessage());
            return null;
        }
    }
}
