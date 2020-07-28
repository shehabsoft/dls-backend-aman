package ae.rta.dls.backend.service.mapper.prd;

import ae.rta.dls.backend.domain.prd.BusinessProfile;
import ae.rta.dls.backend.service.dto.prd.BusinessProfileDTO;

import ae.rta.dls.backend.service.mapper.EntityMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity BusinessProfile and its DTO BusinessProfileDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BusinessProfileMapper extends EntityMapper<BusinessProfileDTO, BusinessProfile> {



    default BusinessProfile fromId(Long id) {
        if (id == null) {
            return null;
        }
        BusinessProfile businessProfile = new BusinessProfile();
        businessProfile.setId(id);
        return businessProfile;
    }
}
