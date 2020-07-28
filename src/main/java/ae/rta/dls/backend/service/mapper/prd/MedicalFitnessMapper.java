package ae.rta.dls.backend.service.mapper.prd;

import ae.rta.dls.backend.domain.prd.MedicalFitness;
import ae.rta.dls.backend.service.dto.prd.MedicalFitnessDTO;

import ae.rta.dls.backend.service.mapper.EntityMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity MedicalFitness and its DTO MedicalFitnessDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MedicalFitnessMapper extends EntityMapper<MedicalFitnessDTO, MedicalFitness> {



    default MedicalFitness fromId(Long id) {
        if (id == null) {
            return null;
        }
        MedicalFitness medicalFitness = new MedicalFitness();
        medicalFitness.setId(id);
        return medicalFitness;
    }
}
