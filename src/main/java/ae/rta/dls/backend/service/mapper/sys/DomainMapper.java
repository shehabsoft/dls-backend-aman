package ae.rta.dls.backend.service.mapper.sys;

import ae.rta.dls.backend.domain.sys.Domain;
import ae.rta.dls.backend.service.dto.sys.DomainDTO;
import ae.rta.dls.backend.service.mapper.EntityMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity Domain and its DTO DomainDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DomainMapper extends EntityMapper<DomainDTO, Domain> {

    DomainDTO toDto(Domain domain);

    @Mapping(target = "domainValues", ignore = true)
    Domain toEntity(DomainDTO domainDTO);

    default Domain fromId(Long id) {
        if (id == null) {
            return null;
        }
        Domain domain = new Domain();
        domain.setId(id);
        return domain;
    }
}
