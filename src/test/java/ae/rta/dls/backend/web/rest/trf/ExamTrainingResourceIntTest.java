package ae.rta.dls.backend.web.rest.trf;

import ae.rta.dls.backend.DlsBackendApp;

import ae.rta.dls.backend.domain.trf.ExamTraining;
import ae.rta.dls.backend.repository.trf.ExamTrainingRepository;
import ae.rta.dls.backend.service.dto.trf.ExamTrainingViewDTO;
import ae.rta.dls.backend.service.mapper.trf.ExamTrainingMapper;
import ae.rta.dls.backend.service.trf.ExamTrainingService;
import ae.rta.dls.backend.service.trn.ServiceRequestService;
import ae.rta.dls.backend.web.rest.TestUtil;
import ae.rta.dls.backend.web.rest.util.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;


import static ae.rta.dls.backend.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for the ExamTrainingResource REST controller.
 *
 * @see ExamTrainingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DlsBackendApp.class)
public class ExamTrainingResourceIntTest {

    private static final String DEFAULT_TRY_CODE = "THEORY";

    private static final String DEFAULT_ENGLISH_TRAINING_TYPE_DESCRIPTION = "AAAAAAAAAA";

    private static final String DEFAULT_ARABIC_TRAINING_TYPE_DESCRIPTION = "AAAAAAAAAA";

    private static final Integer DEFAULT_TOTAL_LESSONS = 1;

    @Autowired
    private ExamTrainingRepository examTrainingRepository;

    @Autowired
    private ExamTrainingMapper examTrainingMapper;

    @Autowired
    private ExamTrainingService examTrainingService;

    @Autowired
    private ServiceRequestService serviceRequestService;

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

    private MockMvc restExamTrainingMockMvc;

    private ExamTraining examTraining;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExamTrainingResource examTrainingResource = new ExamTrainingResource(examTrainingService, serviceRequestService);
        this.restExamTrainingMockMvc = MockMvcBuilders.standaloneSetup(examTrainingResource)
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
    public static ExamTraining createEntity(EntityManager em) {
        return new ExamTraining()
            .tryCode(DEFAULT_TRY_CODE)
            .englishTrainingTypeDescription(DEFAULT_ENGLISH_TRAINING_TYPE_DESCRIPTION)
            .arabicTrainingTypeDescription(DEFAULT_ARABIC_TRAINING_TYPE_DESCRIPTION)
            .minRequiredLessonsNo(DEFAULT_TOTAL_LESSONS);
    }

    @Before
    public void initTest() {
        examTraining = createEntity(em);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamTraining.class);
        ExamTraining examTraining1 = new ExamTraining();
        examTraining1.setId(1L);
        ExamTraining examTraining2 = new ExamTraining();
        examTraining2.setId(examTraining1.getId());
        assertThat(examTraining1).isEqualTo(examTraining2);
        examTraining2.setId(2L);
        assertThat(examTraining1).isNotEqualTo(examTraining2);
        examTraining1.setId(null);
        assertThat(examTraining1).isNotEqualTo(examTraining2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamTrainingViewDTO.class);
        ExamTrainingViewDTO examTrainingViewDTO1 = new ExamTrainingViewDTO();
        examTrainingViewDTO1.setId(1L);
        ExamTrainingViewDTO examTrainingViewDTO2 = new ExamTrainingViewDTO();
        assertThat(examTrainingViewDTO1).isNotEqualTo(examTrainingViewDTO2);
        examTrainingViewDTO2.setId(examTrainingViewDTO1.getId());
        assertThat(examTrainingViewDTO1).isEqualTo(examTrainingViewDTO2);
        examTrainingViewDTO2.setId(2L);
        assertThat(examTrainingViewDTO1).isNotEqualTo(examTrainingViewDTO2);
        examTrainingViewDTO1.setId(null);
        assertThat(examTrainingViewDTO1).isNotEqualTo(examTrainingViewDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(examTrainingMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(examTrainingMapper.fromId(null)).isNull();
    }
}
