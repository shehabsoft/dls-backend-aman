package ae.rta.dls.backend.web.rest.sys;

import ae.rta.dls.backend.DlsBackendApp;

import ae.rta.dls.backend.domain.sys.ErrorLog;
import ae.rta.dls.backend.repository.sys.ErrorLogRepository;
import ae.rta.dls.backend.service.dto.sys.ErrorLogDTO;
import ae.rta.dls.backend.service.mapper.sys.ErrorLogMapper;
import ae.rta.dls.backend.service.sys.ErrorLogService;
import ae.rta.dls.backend.web.rest.TestUtil;
import ae.rta.dls.backend.web.rest.util.ExceptionTranslator;

import org.checkerframework.checker.units.qual.A;
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
 * Test class for the ErrorLogResource REST controller.
 *
 * @see ErrorLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DlsBackendApp.class)
public class ErrorLogResourceIntTest {

    private static final String DEFAULT_LOG_LEVEL = "AAAAAAAAAA";

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_CAUSE = "AAAAAAAAAA";
    private static final String UPDATED_CAUSE = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ErrorLogService errorLogService;

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

    @Autowired
    private ErrorLogMapper errorLogMapper;

    private MockMvc restErrorLogMockMvc;

    private ErrorLog errorLog;

    private static final String SYS_ERROR_LOGS_API = "/api/sys/error-logs";

    private static final String SYS_ERROR_LOGS_BY_ID_API = SYS_ERROR_LOGS_API + "/{id}";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ErrorLogResource errorLogResource = new ErrorLogResource(errorLogService);
        this.restErrorLogMockMvc = MockMvcBuilders.standaloneSetup(errorLogResource)
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
    public static ErrorLog createEntity(EntityManager em) {
        return new ErrorLog()
            .source(DEFAULT_SOURCE)
            .cause(DEFAULT_CAUSE)
            .message(DEFAULT_MESSAGE);
    }

    @Before
    public void initTest() {
        errorLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createErrorLog() throws Exception {
        int databaseSizeBeforeCreate = errorLogRepository.findAll().size();

        // Create the ErrorLog
        restErrorLogMockMvc.perform(post(SYS_ERROR_LOGS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(errorLog)))
            .andExpect(status().isCreated());

        // Validate the ErrorLog in the database
        List<ErrorLog> errorLogList = errorLogRepository.findAll();
        assertThat(errorLogList).hasSize(databaseSizeBeforeCreate + 1);
        ErrorLog testErrorLog = errorLogList.get(errorLogList.size() - 1);
        assertThat(testErrorLog.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testErrorLog.getCause()).isEqualTo(DEFAULT_CAUSE);
        assertThat(testErrorLog.getMessage()).isEqualTo(DEFAULT_MESSAGE);
    }

    @Test
    @Transactional
    public void createErrorLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = errorLogRepository.findAll().size();

        // Create the ErrorLog with an existing ID
        errorLog.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restErrorLogMockMvc.perform(post(SYS_ERROR_LOGS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(errorLog)))
            .andExpect(status().isBadRequest());

        // Validate the ErrorLog in the database
        List<ErrorLog> errorLogList = errorLogRepository.findAll();
        assertThat(errorLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSourceIsRequired() throws Exception {
        int databaseSizeBeforeTest = errorLogRepository.findAll().size();
        // set the field null
        errorLog.setSource(null);

        // Create the ErrorLog, which fails.

        restErrorLogMockMvc.perform(post(SYS_ERROR_LOGS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(errorLog)))
            .andExpect(status().isBadRequest());

        List<ErrorLog> errorLogList = errorLogRepository.findAll();
        assertThat(errorLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllErrorLogs() throws Exception {
        // Initialize the database
        errorLogRepository.saveAndFlush(errorLog);

        // Get all the errorLogList
        restErrorLogMockMvc.perform(get("/api/sys/error-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(errorLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].logLevel").value(hasItem(DEFAULT_LOG_LEVEL)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].cause").value(hasItem(DEFAULT_CAUSE)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)));
    }

    @Test
    @Transactional
    public void getErrorLog() throws Exception {
        // Initialize the database
        errorLogRepository.saveAndFlush(errorLog);

        // Get the errorLog
        restErrorLogMockMvc.perform(get(SYS_ERROR_LOGS_BY_ID_API, errorLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(errorLog.getId().intValue()))
            .andExpect(jsonPath("$.logLevel").value(DEFAULT_LOG_LEVEL))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE))
            .andExpect(jsonPath("$.cause").value(DEFAULT_CAUSE))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE));
    }

    @Test
    @Transactional
    public void getNonExistingErrorLog() throws Exception {
        // Get the errorLog
        restErrorLogMockMvc.perform(get(SYS_ERROR_LOGS_BY_ID_API, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateErrorLog() throws Exception {
        // Initialize the database
        ErrorLogDTO errorLogDTO = errorLogMapper.toDto(errorLog);
        errorLogService.save(errorLogDTO);

        int databaseSizeBeforeUpdate = errorLogRepository.findAll().size();

        // Update the errorLog
        Optional<ErrorLog> optionalErrorLog = errorLogRepository.findById(errorLog.getId());
        if (! optionalErrorLog.isPresent()) {
            return;
        }

        ErrorLog updatedErrorLog = optionalErrorLog.get();
        // Disconnect from session so that the updates on updatedErrorLog are not directly saved in db
        em.detach(updatedErrorLog);
        updatedErrorLog
            .source(UPDATED_SOURCE)
            .cause(UPDATED_CAUSE)
            .message(UPDATED_MESSAGE);

        restErrorLogMockMvc.perform(put(SYS_ERROR_LOGS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedErrorLog)))
            .andExpect(status().isOk());

        // Validate the ErrorLog in the database
        List<ErrorLog> errorLogList = errorLogRepository.findAll();
        assertThat(errorLogList).hasSize(databaseSizeBeforeUpdate);
        ErrorLog testErrorLog = errorLogList.get(errorLogList.size() - 1);
        assertThat(testErrorLog.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testErrorLog.getCause()).isEqualTo(UPDATED_CAUSE);
        assertThat(testErrorLog.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingErrorLog() throws Exception {
        int databaseSizeBeforeUpdate = errorLogRepository.findAll().size();

        // Create the ErrorLog

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restErrorLogMockMvc.perform(put(SYS_ERROR_LOGS_API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(errorLog)))
            .andExpect(status().isBadRequest());

        // Validate the ErrorLog in the database
        List<ErrorLog> errorLogList = errorLogRepository.findAll();
        assertThat(errorLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteErrorLog() throws Exception {
        // Initialize the database
        ErrorLogDTO errorLogDTO = errorLogMapper.toDto(errorLog);
        errorLogService.save(errorLogDTO);

        int databaseSizeBeforeDelete = errorLogRepository.findAll().size();

        // Get the errorLog
        restErrorLogMockMvc.perform(delete(SYS_ERROR_LOGS_BY_ID_API, errorLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ErrorLog> errorLogList = errorLogRepository.findAll();
        assertThat(errorLogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ErrorLog.class);
        ErrorLog errorLog1 = new ErrorLog();
        errorLog1.setId(1L);
        ErrorLog errorLog2 = new ErrorLog();
        errorLog2.setId(errorLog1.getId());
        assertThat(errorLog1).isEqualTo(errorLog2);
        errorLog2.setId(2L);
        assertThat(errorLog1).isNotEqualTo(errorLog2);
        errorLog1.setId(null);
        assertThat(errorLog1).isNotEqualTo(errorLog2);
    }
}
