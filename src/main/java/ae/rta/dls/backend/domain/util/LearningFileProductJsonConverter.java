package ae.rta.dls.backend.domain.util;

import ae.rta.dls.backend.common.errors.SystemException;
import ae.rta.dls.backend.common.util.CommonUtil;
import ae.rta.dls.backend.common.util.StringUtil;
import ae.rta.dls.backend.domain.type.LearningFileProductJsonType;
import com.fasterxml.jackson.core.JsonProcessingException;
import javax.persistence.AttributeConverter;
import java.io.IOException;

/**
 * Learning File Product Json Converter
 *
 * @author Mohammad Qasim.
 */
public class LearningFileProductJsonConverter implements AttributeConverter<LearningFileProductJsonType, String> {

    @Override
    public String convertToDatabaseColumn(LearningFileProductJsonType jsonAttributeValue) {
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
    public LearningFileProductJsonType convertToEntityAttribute(String jsonColumnValue) {
        try {
            if (StringUtil.isBlank(jsonColumnValue)) {
                return null;
            }
            return CommonUtil.getMapper().readValue(jsonColumnValue, LearningFileProductJsonType.class);
        } catch (IOException e) {
            throw new SystemException(e);
        }
    }
}
