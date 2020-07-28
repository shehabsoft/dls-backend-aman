package ae.rta.dls.backend.service.mapper.trf;

import ae.rta.dls.backend.domain.trf.EyeTestResult;
import ae.rta.dls.backend.service.dto.trf.EyeTestResultViewDTO;
import ae.rta.dls.backend.service.mapper.EntityMapper;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity EyeTestResult and its DTO EyeTestResultDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EyeTestResultMapper extends EntityMapper<EyeTestResultViewDTO, EyeTestResult> {

    default EyeTestResult fromId(Long id) {
        if (id == null) {
            return null;
        }
        EyeTestResult examTraining = new EyeTestResult();
        examTraining.setId(id);
        return examTraining;
    }
}
