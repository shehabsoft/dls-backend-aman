package ae.rta.dls.backend.domain.util;


import ae.rta.dls.backend.common.errors.SystemException;
import ae.rta.dls.backend.common.util.CommonUtil;
import ae.rta.dls.backend.common.util.StringUtil;
import ae.rta.dls.backend.domain.type.FeeDocumentJsonType;
import com.fasterxml.jackson.core.JsonProcessingException;
import javax.persistence.AttributeConverter;
import java.io.IOException;

/**
 * Fee Document Json Converter
 *
 * This class is designed mainly to be used for all annotated jpa attributes
 * to convert its value into db json column and read from the same db
 * column then convert it back into FeeDocumentJsonConverter.
 *
 * @author Mena Emiel.
 */
public class FeeDocumentJsonConverter implements AttributeConverter<FeeDocumentJsonType, String> {

    @Override
    public String convertToDatabaseColumn(FeeDocumentJsonType jsonAttributeValue) {
        try {
            if (jsonAttributeValue == null) {
                return null;
            }
            return CommonUtil.getMapper().writeValueAsString(jsonAttributeValue);
        } catch (JsonProcessingException e) {
            throw new SystemException("Error while writing JSON content :: ", e);
        }
    }

    @Override
    public FeeDocumentJsonType convertToEntityAttribute(String jsonColumnValue) {
        try {
            if (StringUtil.isBlank(jsonColumnValue)) {
                return null;
            }
            return CommonUtil.getMapper().readValue(jsonColumnValue, FeeDocumentJsonType.class);
        } catch (IOException e) {
            throw new SystemException(e);
        }
    }
}
