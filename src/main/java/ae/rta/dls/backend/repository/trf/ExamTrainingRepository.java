package ae.rta.dls.backend.repository.trf;

import ae.rta.dls.backend.domain.trf.ExamTraining;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Spring Data  repository for the ExamTraining entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExamTrainingRepository extends JpaRepository<ExamTraining, Long> {

    Optional<ExamTraining> findByTryCodeAndClassCode(String tryCode, String classCode);

    @Query(value =
        "SELECT " +
            " ID, TRY_CODE, TRAINING_TYPE_DESC_E, TRAINING_TYPE_DESC_A, CLASS_CODE, EXAM_CODE, MIN_REQUIRED_LESSON_NO" +
            " FROM(SELECT rownum AS ID," +
            " DECODE(TRY_CODE, 1, 'THEORY', 'ROAD') TRY_CODE," +
            " TRAINING_TYPE_DESC_E," +
            " TRAINING_TYPE_DESC_A," +
            " 'VCL_ID_' || CLASS_CODE AS CLASS_CODE," +
            " DLS_DOMAIN_VALUE_MAP_CODE EXAM_CODE," +
            "    NVL(TOTAL_LESSONS, 0) AS MIN_REQUIRED_LESSON_NO" +
            " FROM(SELECT ERT.TRY_CODE," +
            "         ERT.TRAINING_TYPE_DESC_E," +
            "         ERT.TRAINING_TYPE_DESC_A," +
            "         ERT.CLASS_CODE," +
            "         ETY.DLS_DOMAIN_VALUE_MAP_CODE," +
            "         NVL (TRAFFIC.F_GET_MIN_REQ_LESSONS_NO_TRF(1, (SELECT ID FROM TRAFFIC.TF_STP_COUNTRIES WHERE DLS_MAP_CODE = :driverNationalityCode), NULL, 102211, (SELECT ID FROM TRAFFIC.TF_STP_COUNTRIES WHERE DLS_MAP_CODE = :foreignLicenseCountryCode), :foreignLicenseVclId,:foreignLicenseIssueDate, ERT.CLASS_CODE, ERT.TRAINING_TYPE_NO), ERT.MIN_REQUIRED_LESSON_NO) TOTAL_LESSONS" +
            "     FROM DLS_VIEWS_DB.LKP_EXAM_REQUIRED_TRAININGS_VW ERT," +
            "          TRAFFIC.TF_DTT_EXAM_TYPES ETY" +
            "     WHERE ETY.CODE = ERT.EXAM_CODE" +
            "     AND TRY_CODE IN (1, 6)" +
            "     AND EXAM_CODE <> 8" +
            "     AND CLASS_CODE <> 2" +
            "     AND ETY.DLS_DOMAIN_VALUE_MAP_CODE = DECODE(TRY_CODE, 1, 'THEORY', DECODE(CLASS_CODE, 9, 'YARD', 'ROAD'))" +
            "         )" +
            "         ) WHERE TRY_CODE = :tryCode" +
            "           AND CLASS_CODE = :classCode"
        ,nativeQuery = true)
    Optional<ExamTraining> findByForeignLicenseInfo(@Param("tryCode") String tryCode,
                                                     @Param("classCode") String classCode,
                                                     @Param("driverNationalityCode") String driverNationalityCode,
                                                     @Param("foreignLicenseCountryCode") String foreignLicenseCountryCode,
                                                     @Param("foreignLicenseVclId") Long foreignLicenseVclId,
                                                     @Param("foreignLicenseIssueDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String foreignLicenseIssueDate);
}
