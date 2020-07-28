package ae.rta.dls.backend.web.rest.ws.rest;

import ae.rta.dls.backend.DlsBackendApp;

import ae.rta.dls.backend.domain.ws.rest.BrmRestLog;
import ae.rta.dls.backend.repository.ws.rest.BrmRestLogRepository;
import ae.rta.dls.backend.service.dto.ws.rest.BrmRestLogDTO;
import ae.rta.dls.backend.service.mapper.ws.rest.BrmRestLogMapper;
import ae.rta.dls.backend.service.ws.rest.BrmRestLogService;
import ae.rta.dls.backend.web.rest.TestUtil;
import ae.rta.dls.backend.web.rest.ws.BrmRestLogResource;
import ae.rta.dls.backend.web.rest.util.ExceptionTranslator;

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
 * Test class for the BrmRestLogResource REST controller.
 *
 * @see BrmRestLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DlsBackendApp.class)
public class BrmRestLogResourceIntTest {

    private static final String DEFAULT_CORRELATION_ID = "AAAAAAAAAA";
    private static final String UPDATED_CORRELATION_ID = "BBBBBBBBBB";

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
    private BrmRestLogRepository brmRestLogRepository;

    @Autowired
    private BrmRestLogMapper brmRestLogMapper;

    @Autowired
    private BrmRestLogService brmRestLogService;

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

    private MockMvc restBrmRestLogMockMvc;

    private BrmRestLog brmRestLog;

    private static final String WS_BRM_REST_LOGS_API = "/api/ws/brm-rest-logs";

    private static final String WS_BRM_REST_LOGS_BY_ID_API = WS_BRM_REST_LOGS_API + "/{id}";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BrmRestLogResource brmRestLogResource = new BrmRestLogResource(brmRestLogService);
        this.restBrmRestLogMockMvc = MockMvcBuilders.standaloneSetup(brmRestLogResource)
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
    public static BrmRestLog createEntity(EntityManager em) {
        return new BrmRestLog()
            .correlationId(DEFAULT_CORRELATION_ID)
            .httpMethod(DEFAULT_HTTP_METHOD)
            .httpStatus(DEFAULT_HTTP_STATUS)
            .requestUrl(DEFAULT_REQUEST_URL)
            .requestBody(DEFAULT_REQUEST_BODY)
            .responseBody(DEFAULT_RESPONSE_BODY);
    }

    @Before
    public void initTest() {
        brmRestLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createBrmRestLog() throws Exception {
        int databaseSizeBeforeCreate = brmRestLogRepository.findAll().size();

        // Create the BrmRestLog
        BrmRestLogDTO brmRestLogDTO = brmRestLogMapper.toDto(brmRestLog);
        restBrmRestLogMockMvc.perform(post(WS_BRM_REST_LOGS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brmRestLogDTO)))
            .andExpect(status().isCreated());

        // Validate the BrmRestLog in the database
        List<BrmRestLog> brmRestLogList = brmRestLogRepository.findAll();
        assertThat(brmRestLogList).hasSize(databaseSizeBeforeCreate + 1);
        BrmRestLog testBrmRestLog = brmRestLogList.get(brmRestLogList.size() - 1);
        assertThat(testBrmRestLog.getCorrelationId()).isEqualTo(DEFAULT_CORRELATION_ID);
        assertThat(testBrmRestLog.getHttpMethod()).isEqualTo(DEFAULT_HTTP_METHOD);
        assertThat(testBrmRestLog.getHttpStatus()).isEqualTo(DEFAULT_HTTP_STATUS);
        assertThat(testBrmRestLog.getRequestUrl()).isEqualTo(DEFAULT_REQUEST_URL);
        assertThat(testBrmRestLog.getRequestBody()).isEqualTo(DEFAULT_REQUEST_BODY);
        assertThat(testBrmRestLog.getResponseBody()).isEqualTo(DEFAULT_RESPONSE_BODY);
    }

    @Test
    @Transactional
    public void createBrmRestLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = brmRestLogRepository.findAll().size();

        // Create the BrmRestLog with an existing ID
        brmRestLog.setId(1L);
        BrmRestLogDTO brmRestLogDTO = brmRestLogMapper.toDto(brmRestLog);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBrmRestLogMockMvc.perform(post(WS_BRM_REST_LOGS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brmRestLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BrmRestLog in the database
        List<BrmRestLog> brmRestLogList = brmRestLogRepository.findAll();
        assertThat(brmRestLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkHttpMethodIsRequired() throws Exception {
        int databaseSizeBeforeTest = brmRestLogRepository.findAll().size();
        // set the field null
        brmRestLog.setHttpMethod(null);

        // Create the BrmRestLog, which fails.
        BrmRestLogDTO brmRestLogDTO = brmRestLogMapper.toDto(brmRestLog);

        restBrmRestLogMockMvc.perform(post(WS_BRM_REST_LOGS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brmRestLogDTO)))
            .andExpect(status().isBadRequest());

        List<BrmRestLog> brmRestLogList = brmRestLogRepository.findAll();
        assertThat(brmRestLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRequestUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = brmRestLogRepository.findAll().size();
        // set the field null
        brmRestLog.setRequestUrl(null);

        // Create the BrmRestLog, which fails.
        BrmRestLogDTO brmRestLogDTO = brmRestLogMapper.toDto(brmRestLog);

        restBrmRestLogMockMvc.perform(post(WS_BRM_REST_LOGS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brmRestLogDTO)))
            .andExpect(status().isBadRequest());

        List<BrmRestLog> brmRestLogList = brmRestLogRepository.findAll();
        assertThat(brmRestLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBrmRestLogs() throws Exception {
        // Initialize the database
        brmRestLogRepository.saveAndFlush(brmRestLog);

        // Get all the brmRestLogList
        restBrmRestLogMockMvc.perform(get("/api/ws/brm-rest-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(brmRestLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].correlationId").value(hasItem(DEFAULT_CORRELATION_ID)))
            .andExpect(jsonPath("$.[*].httpMethod").value(hasItem(DEFAULT_HTTP_METHOD)))
            .andExpect(jsonPath("$.[*].httpStatus").value(hasItem(DEFAULT_HTTP_STATUS)))
            .andExpect(jsonPath("$.[*].requestUrl").value(hasItem(DEFAULT_REQUEST_URL)))
            .andExpect(jsonPath("$.[*].requestBody").value(hasItem(DEFAULT_REQUEST_BODY)))
            .andExpect(jsonPath("$.[*].responseBody").value(hasItem(DEFAULT_RESPONSE_BODY)));
    }

    @Test
    @Transactional
    public void getBrmRestLog() throws Exception {
        // Initialize the database
        brmRestLogRepository.saveAndFlush(brmRestLog);

        // Get the brmRestLog
        restBrmRestLogMockMvc.perform(get(WS_BRM_REST_LOGS_BY_ID_API, brmRestLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(brmRestLog.getId().intValue()))
            .andExpect(jsonPath("$.correlationId").value(DEFAULT_CORRELATION_ID))
            .andExpect(jsonPath("$.httpMethod").value(DEFAULT_HTTP_METHOD))
            .andExpect(jsonPath("$.httpStatus").value(DEFAULT_HTTP_STATUS))
            .andExpect(jsonPath("$.requestUrl").value(DEFAULT_REQUEST_URL))
            .andExpect(jsonPath("$.requestBody").value(DEFAULT_REQUEST_BODY))
            .andExpect(jsonPath("$.responseBody").value(DEFAULT_RESPONSE_BODY));
    }

    @Test
    @Transactional
    public void getNonExistingBrmRestLog() throws Exception {
        // Get the brmRestLog
        restBrmRestLogMockMvc.perform(get(WS_BRM_REST_LOGS_BY_ID_API, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBrmRestLog() throws Exception {
        // Initialize the database
        brmRestLogRepository.saveAndFlush(brmRestLog);

        int databaseSizeBeforeUpdate = brmRestLogRepository.findAll().size();

        // Update the brmRestLog
        Optional<BrmRestLog> optionalBrmRestLog = brmRestLogRepository.findById(brmRestLog.getId());
        if (! optionalBrmRestLog.isPresent()) {
            return;
        }

        BrmRestLog updatedBrmRestLog = optionalBrmRestLog.get();
        // Disconnect from session so that the updates on updatedBrmRestLog are not directly saved in db
        em.detach(updatedBrmRestLog);
        updatedBrmRestLog
            .correlationId(UPDATED_CORRELATION_ID)
            .httpMethod(UPDATED_HTTP_METHOD)
            .httpStatus(UPDATED_HTTP_STATUS)
            .requestUrl(UPDATED_REQUEST_URL)
            .requestBody(UPDATED_REQUEST_BODY)
            .responseBody(UPDATED_RESPONSE_BODY);
        BrmRestLogDTO brmRestLogDTO = brmRestLogMapper.toDto(updatedBrmRestLog);

        restBrmRestLogMockMvc.perform(put(WS_BRM_REST_LOGS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brmRestLogDTO)))
            .andExpect(status().isOk());

        // Validate the BrmRestLog in the database
        List<BrmRestLog> brmRestLogList = brmRestLogRepository.findAll();
        assertThat(brmRestLogList).hasSize(databaseSizeBeforeUpdate);
        BrmRestLog testBrmRestLog = brmRestLogList.get(brmRestLogList.size() - 1);
        assertThat(testBrmRestLog.getCorrelationId()).isEqualTo(UPDATED_CORRELATION_ID);
        assertThat(testBrmRestLog.getHttpMethod()).isEqualTo(UPDATED_HTTP_METHOD);
        assertThat(testBrmRestLog.getHttpStatus()).isEqualTo(UPDATED_HTTP_STATUS);
        assertThat(testBrmRestLog.getRequestUrl()).isEqualTo(UPDATED_REQUEST_URL);
        assertThat(testBrmRestLog.getRequestBody()).isEqualTo(UPDATED_REQUEST_BODY);
        assertThat(testBrmRestLog.getResponseBody()).isEqualTo(UPDATED_RESPONSE_BODY);
    }

    @Test
    @Transactional
    public void updateNonExistingBrmRestLog() throws Exception {
        int databaseSizeBeforeUpdate = brmRestLogRepository.findAll().size();

        // Create the BrmRestLog
        BrmRestLogDTO brmRestLogDTO = brmRestLogMapper.toDto(brmRestLog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBrmRestLogMockMvc.perform(put(WS_BRM_REST_LOGS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brmRestLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BrmRestLog in the database
        List<BrmRestLog> brmRestLogList = brmRestLogRepository.findAll();
        assertThat(brmRestLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBrmRestLog() throws Exception {
        // Initialize the database
        brmRestLogRepository.saveAndFlush(brmRestLog);

        int databaseSizeBeforeDelete = brmRestLogRepository.findAll().size();

        // Get the brmRestLog
        restBrmRestLogMockMvc.perform(delete(WS_BRM_REST_LOGS_BY_ID_API, brmRestLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BrmRestLog> brmRestLogList = brmRestLogRepository.findAll();
        assertThat(brmRestLogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BrmRestLog.class);
        BrmRestLog brmRestLog1 = new BrmRestLog();
        brmRestLog1.setId(1L);
        BrmRestLog brmRestLog2 = new BrmRestLog();
        brmRestLog2.setId(brmRestLog1.getId());
        assertThat(brmRestLog1).isEqualTo(brmRestLog2);
        brmRestLog2.setId(2L);
        assertThat(brmRestLog1).isNotEqualTo(brmRestLog2);
        brmRestLog1.setId(null);
        assertThat(brmRestLog1).isNotEqualTo(brmRestLog2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BrmRestLogDTO.class);
        BrmRestLogDTO brmRestLogDTO1 = new BrmRestLogDTO();
        brmRestLogDTO1.setId(1L);
        BrmRestLogDTO brmRestLogDTO2 = new BrmRestLogDTO();
        assertThat(brmRestLogDTO1).isNotEqualTo(brmRestLogDTO2);
        brmRestLogDTO2.setId(brmRestLogDTO1.getId());
        assertThat(brmRestLogDTO1).isEqualTo(brmRestLogDTO2);
        brmRestLogDTO2.setId(2L);
        assertThat(brmRestLogDTO1).isNotEqualTo(brmRestLogDTO2);
        brmRestLogDTO1.setId(null);
        assertThat(brmRestLogDTO1).isNotEqualTo(brmRestLogDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(brmRestLogMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(brmRestLogMapper.fromId(null)).isNull();
    }
}
