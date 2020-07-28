package ae.rta.dls.backend.service.mapper.trf;

import ae.rta.dls.backend.domain.trf.ExamTraining;
import ae.rta.dls.backend.service.dto.trf.ExamTrainingViewDTO;

import ae.rta.dls.backend.service.mapper.EntityMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity ExamTraining and its DTO ExamTrainingDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ExamTrainingMapper extends EntityMapper<ExamTrainingViewDTO, ExamTraining> {

    default ExamTraining fromId(Long id) {
        if (id == null) {
            return null;
        }
        ExamTraining examTraining = new ExamTraining();
        examTraining.setId(id);
        return examTraining;
    }
}
