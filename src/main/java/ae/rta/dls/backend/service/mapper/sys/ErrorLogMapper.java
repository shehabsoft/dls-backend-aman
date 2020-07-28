package ae.rta.dls.backend.service.mapper.sys;

import ae.rta.dls.backend.domain.sys.ErrorLog;
import ae.rta.dls.backend.service.dto.sys.ErrorLogDTO;
import ae.rta.dls.backend.service.mapper.EntityMapper;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity ErrorLog and its DTO ErrorLogDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ErrorLogMapper extends EntityMapper<ErrorLogDTO, ErrorLog> {

    default ErrorLog fromId(Long id) {
        if (id == null) {
            return null;
        }
        ErrorLog errorLog = new ErrorLog();
        errorLog.setId(id);
        return errorLog;
    }
}
