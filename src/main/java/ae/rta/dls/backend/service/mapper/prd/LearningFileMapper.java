package ae.rta.dls.backend.service.mapper.prd;

import ae.rta.dls.backend.domain.prd.LearningFile;
import ae.rta.dls.backend.service.dto.prd.LearningFileDTO;

import ae.rta.dls.backend.service.mapper.EntityMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity LearningFile and its DTO LearningFileDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LearningFileMapper extends EntityMapper<LearningFileDTO, LearningFile> {



    default LearningFile fromId(Long id) {
        if (id == null) {
            return null;
        }
        LearningFile learningFile = new LearningFile();
        learningFile.setId(id);
        return learningFile;
    }
}
