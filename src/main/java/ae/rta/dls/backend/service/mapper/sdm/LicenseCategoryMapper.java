package ae.rta.dls.backend.service.mapper.sdm;

import ae.rta.dls.backend.domain.sdm.LicenseCategory;
import ae.rta.dls.backend.service.dto.sdm.LicenseCategoryDTO;
import ae.rta.dls.backend.service.mapper.EntityMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity LicenseCategory and its DTO LicenseCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {GlobalLicenseCategoryMapper.class})
public interface LicenseCategoryMapper extends EntityMapper<LicenseCategoryDTO, LicenseCategory> {

    @Mapping(source = "globalLicenseCategory", target = "globalLicenseCategoryDTO")
    @Mapping(source = "localLicenseCategory", target = "localLicenseCategoryDTO")
    LicenseCategoryDTO toDto(LicenseCategory licenseCategory);

    @Mapping(source = "globalLicenseCategoryId", target = "globalLicenseCategory")
    @Mapping(source = "localLicenseCategoryId", target = "localLicenseCategory")
    LicenseCategory toEntity(LicenseCategoryDTO licenseCategoryDTO);

    default LicenseCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        LicenseCategory licenseCategory = new LicenseCategory();
        licenseCategory.setId(id);
        return licenseCategory;
    }
}
