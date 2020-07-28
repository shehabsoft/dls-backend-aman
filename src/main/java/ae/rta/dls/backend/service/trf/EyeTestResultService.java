package ae.rta.dls.backend.service.trf;

import ae.rta.dls.backend.service.dto.trf.EyeTestResultViewDTO;

import java.util.Optional;

public interface EyeTestResultService {

    Optional<EyeTestResultViewDTO> findByEmiratesId(String emiratesId);
}
