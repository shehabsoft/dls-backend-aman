package ae.rta.dls.backend.service.util;

import ae.rta.dls.backend.common.errors.SystemException;
import ae.rta.dls.backend.common.util.CommonUtil;
import ae.rta.dls.backend.common.util.StringUtil;
import ae.rta.dls.backend.service.dto.sct.ServiceDTO;
import ae.rta.dls.backend.service.sct.ServiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import java.util.Optional;

/**
 * Service Request Factory.
 *
 * This class is designed to act as a factory for all service requests
 *
 * @author Mena Emiel
 */
@Component
public class ServiceRequestFactory {

    /*
     * Class variables
     */

    /** Logger instance. */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ServiceService serviceService;


    /*
     * Class methods
     */


    /**
     * Get service request
     *
     * @param serviceCode service request code
     * @return the related service handler instance
     */
    public AbstractServiceRequest getServiceRequest(String serviceCode) {
        // Validate service code parameter
        if (StringUtil.isBlank(serviceCode)) {
            throw new SystemException("Invalid service request code");
        }

        Optional<ServiceDTO> service = serviceService.findByCode(serviceCode);

        if (!service.isPresent()) {
            throw new SystemException("No active service for provided service code");
        }

        if (service.get().getServiceImplClass() == null) {
            throw new SystemException("No service Impl found for this service");
        }

        // Load the related service handler class from configuration
        String className = service.get().getServiceImplClass();
        // Retrieve entity class with reflection
        Class<?> serviceClass = null;
        try {
            if (className == null) {
                throw new SystemException("No available configuration class for the given service request");
            }
            serviceClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            log.error("Error occurred during getting the configuration class {} ", className, e);
            return null;
        }

        log.debug("Class name found in the configuration model with name {} : ", className);
        // Autowire an instance of the retrieved service class
        return (AbstractServiceRequest) applicationContext.getBean(serviceClass);

    }
}
