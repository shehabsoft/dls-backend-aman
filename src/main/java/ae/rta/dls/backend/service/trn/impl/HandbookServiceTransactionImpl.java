package ae.rta.dls.backend.service.trn.impl;

import ae.rta.dls.backend.common.util.NumberUtil;
import ae.rta.dls.backend.common.util.brm.calculation.fee.FeeCalculationRequest;
import ae.rta.dls.backend.common.util.brm.calculation.fee.ServiceFeeCriteriaVM;
import ae.rta.dls.backend.domain.type.FeeDocumentJsonType;
import ae.rta.dls.backend.domain.type.HandbookProductJsonType;
import ae.rta.dls.backend.service.dto.prd.CustomerInfoDTO;
import ae.rta.dls.backend.service.dto.prd.HandbookDTO;
import ae.rta.dls.backend.service.dto.trn.ApplicationDTO;
import ae.rta.dls.backend.service.dto.trn.HandbookDetailDTO;
import ae.rta.dls.backend.service.dto.trn.ServiceRequestDTO;
import ae.rta.dls.backend.service.prd.HandbookService;
import ae.rta.dls.backend.service.sys.BusinessRuleService;
import ae.rta.dls.backend.service.util.AbstractServiceRequest;
import ae.rta.dls.backend.web.rest.errors.ApplicationNotFoundException;
import ae.rta.dls.backend.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class HandbookServiceTransactionImpl extends AbstractServiceRequest {

    /*
     * Class variables
     */
    @Autowired
    private HandbookService handbookService;

    @Autowired
    private BusinessRuleService businessRuleService;

    /** Logger instance. */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /*
     * Overridden template methods
     */

    @Override
    public void preCreate(ServiceRequestDTO serviceRequestDTO) {

        log.debug("Inside preCreate :: HandbookServiceHandler");

        HandbookDetailDTO handbookDetailDTO = getServiceRequestDTO(serviceRequestDTO);

        if (handbookDetailDTO == null) {
            throw new BadRequestAlertException("Hand book DTO");
        }

        if (handbookDetailDTO.getType() == null) {
            throw new BadRequestAlertException("Handbook type");
        }

        if (handbookDetailDTO.getName() == null) {
            throw new BadRequestAlertException("Handbook name");
        }
    }

    @Override
    public void postCreate(ServiceRequestDTO serviceRequestDTO) {

        log.debug("Inside postCreate :: HandbookServiceHandler");
    }

    @Override
    public void preUpdate(ServiceRequestDTO serviceRequestDTO) {
        // Empty implementation. The needed logic should be implemented here
        log.debug("Inside preUpdate :: HandbookServiceHandler");
    }

    @Override
    public void postUpdate(ServiceRequestDTO serviceRequestDTO) {
        // Empty implementation. The needed logic should be implemented here
        log.debug("Inside postUpdate :: HandbookServiceHandler");
    }

    @Override
    public void preVerify(ServiceRequestDTO serviceRequestDTO) {
        // Empty implementation. The needed logic should be implemented here
        log.debug("Inside preVerify :: HandbookServiceHandler");
    }

    @Override
    public void postVerify(ServiceRequestDTO serviceRequestDTO) {
        // Empty implementation. The needed logic should be implemented here
        log.debug("Inside postVerify :: HandbookServiceHandler");

        HandbookDetailDTO handbookDetailDTO = getServiceRequestDTO(serviceRequestDTO);

        if (handbookDetailDTO == null) {
            throw new BadRequestAlertException("Null handbook DTO (postVerify)");
        }

        Optional<ApplicationDTO> application = getApplication(serviceRequestDTO.getApplicationId());

        if (!application.isPresent()) {
            throw new ApplicationNotFoundException();
        }

        ServiceFeeCriteriaVM serviceFeeCriteriaVM = new ServiceFeeCriteriaVM();
        serviceFeeCriteriaVM.setLicenseCategoryCode(application.get().getApplicationCriteria().getLicenseCategoryCode());

        FeeCalculationRequest feeCalculationRequest = new FeeCalculationRequest();
        feeCalculationRequest.setApplicationRef(serviceRequestDTO.getApplicationId());
        feeCalculationRequest.setServiceCode(NumberUtil.toLong(serviceRequestDTO.getServiceCode()));
        feeCalculationRequest.setExemptAllFees(Boolean.FALSE);
        feeCalculationRequest.setHandicapped(Boolean.FALSE);
        feeCalculationRequest.setServiceFeeCriteriaVM(serviceFeeCriteriaVM);

        FeeDocumentJsonType feeDocumentJsonType = businessRuleService.calculateFees(feeCalculationRequest);

        serviceRequestDTO.setFeeDocument(feeDocumentJsonType);
        serviceRequestDTO.setTotalFeeAmount(Double.valueOf(feeDocumentJsonType.getTotalAmount()));
    }

    @Override
    public void preConfirm(ServiceRequestDTO serviceRequestDTO, CustomerInfoDTO customerInfoDTO) {
        // Empty implementation. The needed logic should be implemented here
        log.debug("Inside preConfirm :: HandbookServiceHandler");

        if (serviceRequestDTO == null) {
            throw new BadRequestAlertException("service request DTO");
        }

        if (customerInfoDTO == null) {
            throw new BadRequestAlertException("customer info DTO");
        }

        if (serviceRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Service request Id");
        }

        if (serviceRequestDTO.getApplicationId() == null) {
            throw new BadRequestAlertException("Service request application Id");
        }

        HandbookDetailDTO handbookDetailDTO = getServiceRequestDTO(serviceRequestDTO);
        if (handbookDetailDTO == null) {
            throw new BadRequestAlertException("Handbook detail DTO");
        }
    }

    @Override
    public void postConfirm(ServiceRequestDTO serviceRequestDTO, CustomerInfoDTO customerInfoDTO) {
        // Empty implementation. The needed logic should be implemented here
        log.debug("Inside postConfirm :: HandbookServiceHandler");

        HandbookDTO handbookDTO = new HandbookDTO();
        handbookDTO.setApplicationId(serviceRequestDTO.getApplicationId());
        handbookDTO.setServiceRequestId(serviceRequestDTO.getId());

        HandbookProductJsonType handbookProductJsonType = new HandbookProductJsonType();
        HandbookDetailDTO handbookDetailDTO = getServiceRequestDTO(serviceRequestDTO);
        handbookProductJsonType.setHandbookDetails(handbookDetailDTO);
        handbookProductJsonType.setCustomerInfoDTO(customerInfoDTO);
        handbookDTO.setProductDocument(handbookProductJsonType);

        handbookService.save(handbookDTO);
    }

    @Override
    public void preReject(ServiceRequestDTO serviceRequestDTO) {
        // Empty implementation. The needed logic should be implemented here
        log.debug("Inside preReject :: HandbookServiceHandler");
    }

    @Override
    public void postReject(ServiceRequestDTO serviceRequestDTO) {
        // Empty implementation. The needed logic should be implemented here
        log.debug("Inside postReject :: HandbookServiceHandler");
    }
}
