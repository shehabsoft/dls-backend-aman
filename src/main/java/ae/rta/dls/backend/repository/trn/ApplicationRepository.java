package ae.rta.dls.backend.repository.trn;

import ae.rta.dls.backend.domain.enumeration.trn.ApplicationStatus;
import ae.rta.dls.backend.domain.trn.Application;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Application entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> , JpaSpecificationExecutor<Application> {

    List<Application> getByStatusAndStatusDateLessThan(ApplicationStatus applicationStatus, LocalDateTime statusDate);

    /**
     * Find application by passed criteria
     *
     * @param applicationTypeId application type id
     * @param mobileNo mobile number
     * @param birthDate Birth Date
     *
     * @return List of application which's fitted the passed criteria
     */
    @Query(value =
        "SELECT APPl.*" +
            "  FROM TRN_APPLICATION APPl" +
            " WHERE APPl.APPLICATION_TYPE_ID = :applicationTypeId" +
            "   AND APPl.STATUS IN ('DRAFT' , 'UNDER_PROCESSING')" +
            "   AND APPl.APPLICATION_CRITERIA ->> 'mobileNo' = :mobileNo "+
            "   AND APPl.APPLICATION_CRITERIA ->> 'birthdate' = :birthdate" ,nativeQuery = true)
    List<Application> findByMobileCriteria(@Param("applicationTypeId") String applicationTypeId,
                                           @Param("mobileNo") String mobileNo,
                                           @Param("birthdate") String birthDate);

    /**
     * Find application by emirates id and application type id criteria
     *
     * @param applicationTypeId application type id
     * @param eidNumber
     * @param eidExpiryDate
     *
     * Application which's fitted the passed criteria
     */
    @Query(value =
        "SELECT APPl.*" +
            "  FROM TRN_APPLICATION APPl" +
            " WHERE APPl.STATUS IN ('DRAFT' , 'UNDER_PROCESSING')" +
            "   AND APPl.APPLICATION_TYPE_ID = :applicationTypeId" +
            "   AND APPl.APPLICATION_CRITERIA ->> 'eidNumber'     = :eidNumber "+
            "   AND APPl.APPLICATION_CRITERIA ->> 'eidExpiryDate' = :eidExpiryDate" ,nativeQuery = true)
    Optional<Application> findOneByEmiratesId(@Param("applicationTypeId") Long applicationTypeId,
                                              @Param("eidNumber") String eidNumber,
                                              @Param("eidExpiryDate") String eidExpiryDate);


    Optional<Application> findByProcessInstanceId(Long processInstanceId);

    /**
     * Find application by user id and application type id criteria
     *
     * @param id application type id
     * @param userId User Id
     * @param status ApplicationStatus Domain
     * Application which's fitted the passed criteria
     */
    Optional<Application> findByApplicationType_idAndUserIdAndStatusIs(Long id,Long userId,ApplicationStatus status);

    
    /**
     * Find application by info
     *
     * Application which's fitted the passed criteria
     */
    @Query(value =
            "SELECT APPl.*" +
                    "  FROM TRN_APPLICATION APPl" +
                    " WHERE APPl.PROCESS_INSTANCE_ID = :processInstanceId" +
                    "   AND APPl.STATUS = NVL(:status,APPl.STATUS)" +
                    "   AND APPl.APPLICATION_CRITERIA ->> 'eidNumber' = NVL(:eidNumber,APPl.APPLICATION_CRITERIA ->> 'eidNumber') "+
                    "   AND APPl.APPLICATION_CRITERIA ->> 'mobileNo'  = NVL(:mobileNo,APPl.APPLICATION_CRITERIA  ->> 'mobileNo' ) "+
                    "   AND APPl.APPLICATION_CRITERIA ->> 'licenseCategoryCode' = NVL(:licenseCategoryCode,APPl.APPLICATION_CRITERIA ->> 'licenseCategoryCode') "+
                    "   AND APPl.ID = nvl(to_number(:applicationReferenceNo),APPl.ID)"
            , nativeQuery = true)
    Optional<Application> findOneByProcessInstanceInfo(@Param("processInstanceId") Long processInstanceId,
                                                       @Param("status") String status,
                                                       @Param("eidNumber") String eidNumber,
                                                       @Param("mobileNo") String mobileNo,
                                                       @Param("licenseCategoryCode") String licenseCategoryCode,
                                                       @Param("applicationReferenceNo") Long applicationReferenceNo);

    /**
     * Find applications list by info
     *
     * Application which's fitted the passed criteria
     */
    @Query(value =
            "SELECT APPl.*" +
            "  FROM TRN_APPLICATION APPl" +
            " WHERE APPl.PROCESS_INSTANCE_ID IS NULL" +
            "   AND APPl.STATUS = NVL(:status,APPl.STATUS)" +
            "   AND APPl.APPLICATION_CRITERIA ->> 'eidNumber' = NVL(:eidNumber,APPl.APPLICATION_CRITERIA ->> 'eidNumber') "+
            "   AND APPl.APPLICATION_CRITERIA ->> 'mobileNo'  = NVL(:mobileNo,APPl.APPLICATION_CRITERIA ->> 'mobileNo') "+
            "   AND APPl.APPLICATION_CRITERIA ->> 'licenseCategoryCode' = NVL(:licenseCategoryCode,APPl.APPLICATION_CRITERIA ->> 'licenseCategoryCode') " +
            "   ORDER BY APPl.ID" +
            "  OFFSET :offset ROWS FETCH NEXT :pageSize ROWS ONLY"
            , nativeQuery = true)
    List<Application> findByInfo(@Param("status") String status,
                                 @Param("eidNumber") String eidNumber,
                                 @Param("mobileNo") String mobileNo,
                                 @Param("licenseCategoryCode") String licenseCategoryCode,
                                 @Param("pageSize") Integer pageSize,
                                 @Param("offset") Integer offset);
    /**
     * Find application by info
     *
     * Application which's fitted the passed criteria
     */
    @Query(value =
            "SELECT COUNT(1)" +
            "  FROM TRN_APPLICATION APPl" +
            " WHERE APPl.PROCESS_INSTANCE_ID IS NULL" +
            "   AND APPl.STATUS = NVL(:status,APPl.STATUS)" +
            "   AND APPl.APPLICATION_CRITERIA ->> 'eidNumber' = NVL(:eidNumber,APPl.APPLICATION_CRITERIA ->> 'eidNumber') "+
            "   AND APPl.APPLICATION_CRITERIA ->> 'mobileNo'  = NVL(:mobileNo,APPl.APPLICATION_CRITERIA ->> 'mobileNo') "+
            "   AND APPl.APPLICATION_CRITERIA ->> 'licenseCategoryCode' = NVL(:licenseCategoryCode,APPl.APPLICATION_CRITERIA - >> 'licenseCategoryCode') "
            , nativeQuery = true)
    Integer findTotalCountByInfo(@Param("status") String status,
                                 @Param("eidNumber") String eidNumber,
                                 @Param("mobileNo") String mobileNo,
                                 @Param("licenseCategoryCode") String licenseCategoryCode);
}
