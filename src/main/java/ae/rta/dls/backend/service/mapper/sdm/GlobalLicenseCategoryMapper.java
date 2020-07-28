package ae.rta.dls.backend.service.mapper.sdm;

import ae.rta.dls.backend.domain.sdm.GlobalLicenseCategory;
import ae.rta.dls.backend.service.dto.sdm.GlobalLicenseCategoryDTO;

import ae.rta.dls.backend.service.mapper.EntityMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity GlobalLicenseCategory and its DTO GlobalLicenseCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GlobalLicenseCategoryMapper extends EntityMapper<GlobalLicenseCategoryDTO, GlobalLicenseCategory> {

    default GlobalLicenseCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        GlobalLicenseCategory globalLicenseCategory = new GlobalLicenseCategory();
        globalLicenseCategory.setId(id);
        return globalLicenseCategory;
    }
}
