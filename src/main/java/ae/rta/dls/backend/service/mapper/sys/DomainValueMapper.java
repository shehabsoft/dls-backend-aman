package ae.rta.dls.backend.service.mapper.sys;

import ae.rta.dls.backend.domain.sys.DomainValue;
import ae.rta.dls.backend.service.dto.sys.DomainValueDTO;

import ae.rta.dls.backend.service.mapper.EntityMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity DomainValue and its DTO DomainValueDTO.
 */
@Mapper(componentModel = "spring", uses = {DomainMapper.class})
public interface DomainValueMapper extends EntityMapper<DomainValueDTO, DomainValue> {

    @Mapping(source = "domain.id", target = "domainId")
    DomainValueDTO toDto(DomainValue domainValue);

    @Mapping(source = "domainId", target = "domain")
    DomainValue toEntity(DomainValueDTO domainValueDTO);

    default DomainValue fromId(Long id) {
        if (id == null) {
            return null;
        }
        DomainValue domainValue = new DomainValue();
        domainValue.setId(id);
        return domainValue;
    }
}
