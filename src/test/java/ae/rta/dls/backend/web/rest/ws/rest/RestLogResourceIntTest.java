package ae.rta.dls.backend.web.rest.ws.rest;

import ae.rta.dls.backend.DlsBackendApp;
import ae.rta.dls.backend.domain.ws.rest.RestLog;
import ae.rta.dls.backend.repository.ws.rest.RestLogRepository;
import ae.rta.dls.backend.service.dto.ws.rest.RestLogDTO;
import ae.rta.dls.backend.service.mapper.ws.rest.RestLogMapper;
import ae.rta.dls.backend.service.ws.rest.RestLogService;
import ae.rta.dls.backend.web.rest.TestUtil;
import ae.rta.dls.backend.web.rest.util.ExceptionTranslator;
import ae.rta.dls.backend.web.rest.ws.RestLogResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static ae.rta.dls.backend.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RestLogResource REST controller.
 *
 * @see RestLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DlsBackendApp.class)
public class RestLogResourceIntTest {

    private static final String DEFAULT_CORRELATION_ID = "AAAAAAAAAA";
    private static final String UPDATED_CORRELATION_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_REST_MODE = 1;
    private static final Integer UPDATED_REST_MODE = 2;

    private static final String DEFAULT_HTTP_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_HTTP_METHOD = "BBBBBBBBBB";

    private static final Integer DEFAULT_HTTP_STATUS = 1;
    private static final Integer UPDATED_HTTP_STATUS = 2;

    private static final String DEFAULT_REQUEST_URL = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST_URL = "BBBBBBBBBB";

    private static final String DEFAULT_REQUEST_BODY = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST_BODY = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSE_BODY = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSE_BODY = "BBBBBBBBBB";

    @Autowired
    private RestLogRepository restLogRepository;

    @Autowired
    private RestLogMapper restLogMapper;

    @Autowired
    private RestLogService restLogService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restRestLogMockMvc;

    private RestLog restLog;

    private static final String WS_REST_LOGS_API = "/api/ws/rest-logs";

    private static final String WS_REST_LOGS_BY_ID_API = WS_REST_LOGS_API + "/{id}";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RestLogResource restLogResource = new RestLogResource(restLogService);
        this.restRestLogMockMvc = MockMvcBuilders.standaloneSetup(restLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RestLog createEntity(EntityManager em) {
        return new RestLog()
            .correlationId(DEFAULT_CORRELATION_ID)
            .restMode(DEFAULT_REST_MODE)
            .httpMethod(DEFAULT_HTTP_METHOD)
            .httpStatus(DEFAULT_HTTP_STATUS)
            .requestUrl(DEFAULT_REQUEST_URL)
            .requestBody(DEFAULT_REQUEST_BODY)
            .responseBody(DEFAULT_RESPONSE_BODY);
    }

    @Before
    public void initTest() {
        restLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createRestLog() throws Exception {
        int databaseSizeBeforeCreate = restLogRepository.findAll().size();

        // Create the RestLog
        RestLogDTO restLogDTO = restLogMapper.toDto(restLog);
        restRestLogMockMvc.perform(post(WS_REST_LOGS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restLogDTO)))
            .andExpect(status().isCreated());

        // Validate the RestLog in the database
        List<RestLog> restLogList = restLogRepository.findAll();
        assertThat(restLogList).hasSize(databaseSizeBeforeCreate + 1);
        RestLog testRestLog = restLogList.get(restLogList.size() - 1);
        assertThat(testRestLog.getCorrelationId()).isEqualTo(DEFAULT_CORRELATION_ID);
        assertThat(testRestLog.getRestMode()).isEqualTo(DEFAULT_REST_MODE);
        assertThat(testRestLog.getHttpMethod()).isEqualTo(DEFAULT_HTTP_METHOD);
        assertThat(testRestLog.getHttpStatus()).isEqualTo(DEFAULT_HTTP_STATUS);
        assertThat(testRestLog.getRequestUrl()).isEqualTo(DEFAULT_REQUEST_URL);
        assertThat(testRestLog.getRequestBody()).isEqualTo(DEFAULT_REQUEST_BODY);
        assertThat(testRestLog.getResponseBody()).isEqualTo(DEFAULT_RESPONSE_BODY);
    }

    @Test
    @Transactional
    public void createRestLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = restLogRepository.findAll().size();

        // Create the RestLog with an existing ID
        restLog.setId(1L);
        RestLogDTO restLogDTO = restLogMapper.toDto(restLog);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRestLogMockMvc.perform(post(WS_REST_LOGS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RestLog in the database
        List<RestLog> restLogList = restLogRepository.findAll();
        assertThat(restLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkHttpMethodIsRequired() throws Exception {
        int databaseSizeBeforeTest = restLogRepository.findAll().size();
        // set the field null
        restLog.setHttpMethod(null);

        // Create the RestLog, which fails.
        RestLogDTO restLogDTO = restLogMapper.toDto(restLog);

        restRestLogMockMvc.perform(post(WS_REST_LOGS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restLogDTO)))
            .andExpect(status().isBadRequest());

        List<RestLog> restLogList = restLogRepository.findAll();
        assertThat(restLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRequestUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = restLogRepository.findAll().size();
        // set the field null
        restLog.setRequestUrl(null);

        // Create the RestLog, which fails.
        RestLogDTO restLogDTO = restLogMapper.toDto(restLog);

        restRestLogMockMvc.perform(post(WS_REST_LOGS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restLogDTO)))
            .andExpect(status().isBadRequest());

        List<RestLog> restLogList = restLogRepository.findAll();
        assertThat(restLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRestLogs() throws Exception {
        // Initialize the database
        restLogRepository.saveAndFlush(restLog);

        // Get all the restLogList
        restRestLogMockMvc.perform(get("/api/ws/rest-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(restLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].correlationId").value(hasItem(DEFAULT_CORRELATION_ID)))
            .andExpect(jsonPath("$.[*].restMode").value(hasItem(DEFAULT_REST_MODE.toString())))
            .andExpect(jsonPath("$.[*].httpMethod").value(hasItem(DEFAULT_HTTP_METHOD)))
            .andExpect(jsonPath("$.[*].httpStatus").value(hasItem(DEFAULT_HTTP_STATUS)))
            .andExpect(jsonPath("$.[*].requestUrl").value(hasItem(DEFAULT_REQUEST_URL)))
            .andExpect(jsonPath("$.[*].requestBody").value(hasItem(DEFAULT_REQUEST_BODY)))
            .andExpect(jsonPath("$.[*].responseBody").value(hasItem(DEFAULT_RESPONSE_BODY)));
    }

    @Test
    @Transactional
    public void getRestLog() throws Exception {
        // Initialize the database
        restLogRepository.saveAndFlush(restLog);

        // Get the restLog
        restRestLogMockMvc.perform(get(WS_REST_LOGS_BY_ID_API, restLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(restLog.getId().intValue()))
            .andExpect(jsonPath("$.correlationId").value(DEFAULT_CORRELATION_ID))
            .andExpect(jsonPath("$.restMode").value(DEFAULT_REST_MODE.toString()))
            .andExpect(jsonPath("$.httpMethod").value(DEFAULT_HTTP_METHOD))
            .andExpect(jsonPath("$.httpStatus").value(DEFAULT_HTTP_STATUS))
            .andExpect(jsonPath("$.requestUrl").value(DEFAULT_REQUEST_URL))
            .andExpect(jsonPath("$.requestBody").value(DEFAULT_REQUEST_BODY))
            .andExpect(jsonPath("$.responseBody").value(DEFAULT_RESPONSE_BODY));
    }

    @Test
    @Transactional
    public void getNonExistingRestLog() throws Exception {
        // Get the restLog
        restRestLogMockMvc.perform(get(WS_REST_LOGS_BY_ID_API, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRestLog() throws Exception {
        // Initialize the database
        restLogRepository.saveAndFlush(restLog);

        int databaseSizeBeforeUpdate = restLogRepository.findAll().size();

        // Update the restLog
        Optional<RestLog> optionalRestLog = restLogRepository.findById(restLog.getId());
        if (! optionalRestLog.isPresent()) {
            return;
        }

        RestLog updatedRestLog = optionalRestLog.get();
        // Disconnect from session so that the updates on updatedRestLog are not directly saved in db
        em.detach(updatedRestLog);
        updatedRestLog
            .correlationId(UPDATED_CORRELATION_ID)
            .restMode(UPDATED_REST_MODE)
            .httpMethod(UPDATED_HTTP_METHOD)
            .httpStatus(UPDATED_HTTP_STATUS)
            .requestUrl(UPDATED_REQUEST_URL)
            .requestBody(UPDATED_REQUEST_BODY)
            .responseBody(UPDATED_RESPONSE_BODY);
        RestLogDTO restLogDTO = restLogMapper.toDto(updatedRestLog);

        restRestLogMockMvc.perform(put(WS_REST_LOGS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restLogDTO)))
            .andExpect(status().isOk());

        // Validate the RestLog in the database
        List<RestLog> restLogList = restLogRepository.findAll();
        assertThat(restLogList).hasSize(databaseSizeBeforeUpdate);
        RestLog testRestLog = restLogList.get(restLogList.size() - 1);
        assertThat(testRestLog.getCorrelationId()).isEqualTo(UPDATED_CORRELATION_ID);
        assertThat(testRestLog.getRestMode()).isEqualTo(UPDATED_REST_MODE);
        assertThat(testRestLog.getHttpMethod()).isEqualTo(UPDATED_HTTP_METHOD);
        assertThat(testRestLog.getHttpStatus()).isEqualTo(UPDATED_HTTP_STATUS);
        assertThat(testRestLog.getRequestUrl()).isEqualTo(UPDATED_REQUEST_URL);
        assertThat(testRestLog.getRequestBody()).isEqualTo(UPDATED_REQUEST_BODY);
        assertThat(testRestLog.getResponseBody()).isEqualTo(UPDATED_RESPONSE_BODY);
    }

    @Test
    @Transactional
    public void updateNonExistingRestLog() throws Exception {
        int databaseSizeBeforeUpdate = restLogRepository.findAll().size();

        // Create the RestLog
        RestLogDTO restLogDTO = restLogMapper.toDto(restLog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRestLogMockMvc.perform(put(WS_REST_LOGS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RestLog in the database
        List<RestLog> restLogList = restLogRepository.findAll();
        assertThat(restLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRestLog() throws Exception {
        // Initialize the database
        restLogRepository.saveAndFlush(restLog);

        int databaseSizeBeforeDelete = restLogRepository.findAll().size();

        // Get the restLog
        restRestLogMockMvc.perform(delete(WS_REST_LOGS_BY_ID_API, restLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RestLog> restLogList = restLogRepository.findAll();
        assertThat(restLogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RestLog.class);
        RestLog restLog1 = new RestLog();
        restLog1.setId(1L);
        RestLog restLog2 = new RestLog();
        restLog2.setId(restLog1.getId());
        assertThat(restLog1).isEqualTo(restLog2);
        restLog2.setId(2L);
        assertThat(restLog1).isNotEqualTo(restLog2);
        restLog1.setId(null);
        assertThat(restLog1).isNotEqualTo(restLog2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RestLogDTO.class);
        RestLogDTO restLogDTO1 = new RestLogDTO();
        restLogDTO1.setId(1L);
        RestLogDTO restLogDTO2 = new RestLogDTO();
        assertThat(restLogDTO1).isNotEqualTo(restLogDTO2);
        restLogDTO2.setId(restLogDTO1.getId());
        assertThat(restLogDTO1).isEqualTo(restLogDTO2);
        restLogDTO2.setId(2L);
        assertThat(restLogDTO1).isNotEqualTo(restLogDTO2);
        restLogDTO1.setId(null);
        assertThat(restLogDTO1).isNotEqualTo(restLogDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(restLogMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(restLogMapper.fromId(null)).isNull();
    }
}
