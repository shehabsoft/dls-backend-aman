package ae.rta.dls.backend.client.rest.util;

import ae.rta.dls.backend.common.util.CommonUtil;
import ae.rta.dls.backend.common.util.StringUtil;
import ae.rta.dls.backend.domain.enumeration.sys.ConfigurationKey;
import ae.rta.dls.backend.service.dto.sys.ApplicationConfigurationDTO;
import ae.rta.dls.backend.service.dto.ws.rest.RestLogDTO;
import ae.rta.dls.backend.service.sys.ApplicationConfigurationService;
import ae.rta.dls.backend.service.sys.ErrorLogService;
import ae.rta.dls.backend.service.ws.rest.RestLogService;
import brave.Tracer;
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

public class RestTemplateClientInterceptor implements ClientHttpRequestInterceptor {

    private final Tracer tracer;
    private final RestLogService restLogService;
    private final ErrorLogService errorLogService;
    private final ApplicationConfigurationService applicationConfigurationService;
    private final CommonUtil commonUtil;

    public RestTemplateClientInterceptor(Tracer tracer,
                                         RestLogService restLogService,
                                         ApplicationConfigurationService applicationConfigurationService,
                                         ErrorLogService errorLogService,
                                         CommonUtil commonUtil) {
        this.tracer = tracer;
        this.restLogService = restLogService;
        this.applicationConfigurationService = applicationConfigurationService;
        this.errorLogService = errorLogService;
        this.commonUtil = commonUtil;
    }
    private final Logger log = LoggerFactory.getLogger(RestTemplateClientInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        // Log Request..
        RestLogDTO dto = logRequest(request,body);

        ClientHttpResponse response = execution.execute(request, body);

        if(dto != null && dto.getId() != null) {
            // Log Response..
            logResponse(response,dto);
        }
        return response;
    }

    /**
     * Log Request And Response
     *
     * @param request Http Request
     * @param body Request Body
     * @throws IOException
     */
    private RestLogDTO logRequest(HttpRequest request, byte[] body) {
        try {
            if(!isLoggingEnabled()) {
                return null;
            }
            RestLogDTO dto = new RestLogDTO();
            dto.setCorrelationId(tracer.currentSpan().context().traceIdString());
            dto.setRequestBody(new String(body, StandardCharsets.UTF_8));
            dto.setRequestUrl(request.getURI().toString());
            dto.setHttpMethod(request.getMethod().toString());
            dto.setRestMode(RestLogDTO.RestMode.OUT.value());

            // Add Rest Log..
            dto = restLogService.save(dto);

            return dto;

        } catch(Exception e) {
            log.error("Logging failed in RestTemplateClientInterceptor.logRequest because of the following {}", e.getMessage());
            commonUtil.logError(e);
        }
        return null;
    }

    /**
     * Log Response
     *
     * @param response Client Http Response
     * @throws IOException
     */
    private void logResponse(ClientHttpResponse response,RestLogDTO dto) throws IOException {
        if(!isLoggingEnabled()) {
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
        restLogService.save(dto);
    }

    /**
     * Is logging Enabled
     *
     * @return true if logging Is Enabled
     */
    public boolean isLoggingEnabled() {
        try {
            Optional<ApplicationConfigurationDTO> applicationConfiguration =
                    applicationConfigurationService.getConfiguration(ConfigurationKey.ENABLE_REST_LOGGING.value());

            if(applicationConfiguration.isPresent()
                    && !StringUtil.isEmpty(applicationConfiguration.get().getConfigValue())
                    && applicationConfiguration.get().getConfigValue().equalsIgnoreCase(Boolean.FALSE.toString()))  {
                return false;
            }
        } catch(Exception e) {
            log.error("Logging failed in RestTemplateClientInterceptor.isLoggingEnabled() because of the following {}", e.getMessage());
            return false;
        }
        return true;
    }
}
