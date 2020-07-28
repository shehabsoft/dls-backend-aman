package ae.rta.dls.backend.domain.util;

import ae.rta.dls.backend.common.errors.SystemException;
import ae.rta.dls.backend.common.util.CommonUtil;
import ae.rta.dls.backend.common.util.StringUtil;
import ae.rta.dls.backend.domain.util.type.BusinessProfileProductJsonType;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.persistence.AttributeConverter;
import java.io.IOException;

/**
 * Business Profile Product Json Converter
 *
 * @author Rami Nassar.
 */
public class BusinessProfileProductJsonConverter implements AttributeConverter<BusinessProfileProductJsonType, String> {

    @Override
    public String convertToDatabaseColumn(BusinessProfileProductJsonType jsonAttributeValue) {
        try {
            if (jsonAttributeValue == null) {
                return null;
            }
            return CommonUtil.getMapper().writeValueAsString(jsonAttributeValue);
        } catch (JsonProcessingException e) {
            throw new SystemException(e);
        }
    }

    @Override
    public BusinessProfileProductJsonType convertToEntityAttribute(String jsonColumnValue) {
        try {
            if (StringUtil.isBlank(jsonColumnValue)) {
                return null;
            }
            return CommonUtil.getMapper().readValue(jsonColumnValue, BusinessProfileProductJsonType.class);
        } catch (IOException e) {
            throw new SystemException(e);
        }
    }
}
