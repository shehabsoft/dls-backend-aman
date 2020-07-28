package ae.rta.dls.backend.common.util;

import ae.rta.dls.backend.common.errors.SystemException;
import ae.rta.dls.backend.domain.type.MultilingualJsonType;
import ae.rta.dls.backend.service.dto.sys.ApplicationConfigurationDTO;
import ae.rta.dls.backend.service.dto.sys.ErrorLogDTO;
import ae.rta.dls.backend.service.sys.ApplicationConfigurationService;
import ae.rta.dls.backend.service.sys.DomainValueService;
import ae.rta.dls.backend.service.sys.ErrorLogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.*;
/**
 * Configuration provides a useful and easy-to-use Util methods for all tiers.
 */
@Component
public class CommonUtil {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DomainValueService domainValueService;

    @Autowired
    private ErrorLogService errorLogService;

    @Autowired
    private ApplicationConfigurationService applicationConfigurationService;

    @Autowired(required = false)
    private HttpServletRequest httpRequest;

    // Create new instance of mapper
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Get the "Value" for Application Configuration Key.
     *
     * @param key the key of the entity.
     * @return Application Configuration Value.
     */
    public String getConfigurationValue(String key) {
        Optional<ApplicationConfigurationDTO> configuration = null;
        try {
            configuration = applicationConfigurationService.getConfiguration(key);
        } catch (Exception e) {
            log.error("Error occurred during get configuration: {} :", e);
            return null;
        }
        if(!configuration.isPresent()) {
            return null;
        }
        return configuration.get().getConfigValue();
    }

    /**
     * Get one domainValue by Value and domain id.
     *
     * @param value the value of the entity
     * @param domainCode the Domain Code
     * @return the multilingual json type of the the given value
     */
    public MultilingualJsonType getDomainValueDescription(String value, String domainCode) {
        return domainValueService.getDomainValueDescription(value,domainCode);
    }

    /**
     * Get application id.
     *
     * @param requestBody Request body
     * @param method Method
     * @param url URL
     * @return Application id
     */
    public Long getApplicationIdParam(String requestBody, String method, String url) {
        try {
            Long applicationId = null;
            if (httpRequest != null) {
                String applicationRefNo = httpRequest.getHeader("applicationRefNo");
                if (!StringUtil.isBlank(applicationRefNo) && NumberUtil.toLong(applicationRefNo) != 0) {
                    applicationId = NumberUtil.toLong(applicationRefNo);
                }
                if (applicationId != null) {
                    return applicationId;
                }
            }
            if (StringUtil.isNotBlank(requestBody)) {
                Map jsonJavaRootObject = new Gson().fromJson(requestBody, Map.class);
                String applicationReferenceNoStr = StringUtil.getString(jsonJavaRootObject.get("applicationReferenceNo"));
                if (StringUtil.isNotBlank(applicationReferenceNoStr)) {
                    Double doubleApplicationReferenceNo = NumberUtils.createDouble(applicationReferenceNoStr);

                    return doubleApplicationReferenceNo == null ? null : doubleApplicationReferenceNo.longValue();
                }
            } else if (method != null && method.equals(HttpMethod.GET.toString())) {
                return getApplicationReferenceQueryParam(url);
            }
            return null;
        } catch(Exception ex) {
            log.error(String.format("Error While Extract the applicationReferenceNo value in rest Log for url %s" ,url));
            return null;
        }
    }

    /**
     * Get application reference query param
     *
     * @param url url
     * @return url appended parameters
     */
    private Long getApplicationReferenceQueryParam(String url) {
        String applicationReferenceNoStr = null;

        if(url.contains("/applicationReferenceNo/")) {
            applicationReferenceNoStr = url.substring(url.indexOf("/applicationReferenceNo/") + 24);
            int nextParamIndex = applicationReferenceNoStr.indexOf('/');
            if (nextParamIndex != -1) {
                applicationReferenceNoStr = applicationReferenceNoStr.substring(0,nextParamIndex);
            }
        } else if(url.contains("/api/trn/applications/")) {
            applicationReferenceNoStr = url.substring(url.indexOf("/api/trn/applications/") + 22);
            int nextParamIndex = applicationReferenceNoStr.indexOf('/');
            if (nextParamIndex != -1) {
                applicationReferenceNoStr = applicationReferenceNoStr.substring(0,nextParamIndex);
            }
        }

        if (NumberUtil.isDigits(applicationReferenceNoStr)) {
            return NumberUtil.toLong(applicationReferenceNoStr);
        } else {
            return null;
        }
    }

    /**
     * Log methods throwing exceptions into database.
     *
     * @param e exception
     */
    public Long logError (Throwable e) {
        try {
            ErrorLogDTO errorLogDTO = new ErrorLogDTO();
            errorLogDTO.setMessage(ExceptionUtils.getStackTrace(e));
            errorLogDTO.setCause(e.getMessage());
            errorLogDTO.setSource(e.getCause() != null ? e.getCause().getMessage() : e.getMessage());

            errorLogDTO = errorLogService.save(errorLogDTO);
            Optional<ErrorLogDTO> optionalErrorLog = Optional.ofNullable(errorLogDTO);
            Long errorLogId = null;
            if (optionalErrorLog.isPresent()) {
                errorLogId = optionalErrorLog.get().getId();
            }
            return errorLogId;
        } catch (Exception exp) {
            log.error("Error while adding error log in logError because of the following {}", exp.getMessage());
            log.error("Original Exception : ",e);
            return null;
        }
    }

    /**
     * Append exception parameters to exception message.
     *
     * @param msg Exception message.
     * @param parameters Message parameter.
     * @return formatted message.
     */
    public static String appendParameters(String msg, Object parameters) {
        if (parameters != null && parameters.getClass().isArray()) {
            return appendParameters(msg, (Object[]) parameters);
        } else {
            return appendParameters(msg, new Object[]{parameters});
        }
    }

    /**
     * Append exception parameters to exception message.
     *
     * @param msg Exception message.
     * @param parameters Message parameter.
     * @return formatted message.
     */
    public static String appendParameters(String msg, Object[] parameters) {
        if (msg == null || parameters == null || parameters.length == 0) {
            return msg;
        }

        StringBuilder buf = new StringBuilder(msg);
        int startIndex = 0;
        for (int i = 0; i < parameters.length; i++)  {
            String param = (parameters[i] == null) ? "null" : parameters[i].toString();
            int index = buf.indexOf("?", startIndex);
            startIndex = index + 1;

            if (index >= 0) {
                buf.replace(index, index + 1, param);
            } else {
                buf.append(", [").append(param).append("]");
            }
        }

        return buf.toString();
    }

    /**
     * Copy not null properties from a new instance passed from old instance that retreived from the db
     *  to the application instance (request) of the same type,
     *  - Primitive types are not handled as they are having default values
     *  - Collections handled if they implement equals method and It's comparing if the id is the same for
     *   each object, otherwise It will copy the object as is!!
     * @param source Old instnace that retreived from db
     * @param target New instance passed from the application
     */
    public <T extends Object> T copyNotNullProperty(T source, T target) {

        if(target == null && source == null) {
            throw new SystemException("Both target and source objects are manadatory for copying process");
        }

        if(target == null) {
            return source;
        }

        try{
            BeanWrapper sourceBeanWrapper = new BeanWrapperImpl(source);
            BeanWrapper targetBeanWrapper = new BeanWrapperImpl(target);

            checkArrayLength(source.getClass().getDeclaredFields(), target.getClass().getDeclaredFields());

            for (final Field field : source.getClass().getDeclaredFields()) {

                String sourceFieldClassName = field.getType().getName();
                // Get both target and source fields values
                T sourceFieldValue = (T) sourceBeanWrapper.getPropertyValue(field.getName());
                T targetFieldValue = (T) targetBeanWrapper.getPropertyValue(field.getName());

                if(sourceFieldValue == null) {
                    continue;
                }

                if(field.getType().isPrimitive()) {
                    throw new SystemException("Primitive fields out of scope of copying process");

                } else if(sourceFieldClassName.equalsIgnoreCase("java.lang.Object")) {

                    LinkedHashMap targetFieldValueMap = (LinkedHashMap) targetFieldValue;
                    LinkedHashMap sourceFieldValueMap = (LinkedHashMap) sourceFieldValue;

                    targetBeanWrapper.setPropertyValue(field.getName(),  handleObjectType(sourceFieldValueMap, targetFieldValueMap));

                } else if(sourceFieldClassName.toLowerCase().indexOf("java.lang".toLowerCase()) != -1 ||
                    sourceFieldClassName.indexOf("java.time") != -1 ||
                    sourceFieldClassName.indexOf("enumeration") != -1) {

                    targetBeanWrapper.setPropertyValue(field.getName(), handleJavaWrapperType(sourceFieldValue, targetFieldValue));

                } else if(sourceFieldValue instanceof Collection<?> && !((Collection) sourceFieldValue).isEmpty()) {

                    Collection sourceCollection = (Collection) sourceFieldValue;
                    Collection targetCollection = (Collection) targetFieldValue;

                    targetBeanWrapper.setPropertyValue(field.getName(),  handleCollectionType(sourceCollection, targetCollection));

                } else if(sourceFieldClassName.toLowerCase().indexOf("ae.rta.".toLowerCase()) != -1) {
                    targetBeanWrapper.setPropertyValue(field.getName(),
                        copyNotNullProperty(sourceFieldValue, targetFieldValue));

                }
            }

        } catch(Exception ex) {
            throw new SystemException(ex);
        }

        return target;
    }

    /**
     * Check Array Length
     * sourceField
     * targetField
     */
    private void checkArrayLength(Field[] sourceField, Field[] targetField) {

        if (sourceField.length == 0) {
            throw new SystemException("No declared fields in the source object to process fields copy");
        }

        if (targetField.length == 0) {
            throw new SystemException("No declared fields in the target object to process fields copy");
        }
    }

    /**
     * Handle JSON Type
     *
     * @param source
     * @param target
     *
     * @return updated target object
     */
    public Object handleObjectType(Object source, Object target) {

        if (source == null || target == null) {
            return target;
        }

        if (source instanceof LinkedHashMap && target instanceof LinkedHashMap) {
            LinkedHashMap sourceFieldValueMap = (LinkedHashMap) source;
            LinkedHashMap targetFieldValueMap = (LinkedHashMap) target;

            sourceFieldValueMap.forEach((key,value) -> {
                if(targetFieldValueMap.get(key) == null) {
                    targetFieldValueMap.put(key, value);
                } else {
                    handleObjectType(sourceFieldValueMap.get(key), targetFieldValueMap.get(key));
                }
            });
        } else if (source instanceof Collection && target instanceof Collection) {
            Collection sourceCollection = (Collection) source;
            Collection targetCollection = (Collection) target;

            Integer sourceIndex = 0;
            for (Object sourceObj : sourceCollection) {

                if (sourceIndex < targetCollection.size()) {
                    Object targetObj = targetCollection.toArray()[sourceIndex];
                    handleObjectType(sourceObj,targetObj);
                } else {
                    targetCollection.add(sourceObj);
                }

                sourceIndex++;
            }
        }

        return target;
    }

    /**
     * Handle Java Wrapper Type
     * @param source
     * @param target
     * @return target
     */
    private <T extends Object> T handleJavaWrapperType(T source, T target) {

        if(target == null) {

            return source;

        } else if(!source.equals(target)) {

            return target;
        }

        return target;
    }

    /**
     * Handle Collection Type
     * @param sourceCollection
     * @param targetCollection
     * @return targetCollection
     */
    private Collection handleCollectionType(Collection sourceCollection, Collection targetCollection) {

        if(targetCollection == null || targetCollection.isEmpty()) {

            return sourceCollection;
        } else {

            sourceCollection.forEach(sourceKey ->

                targetCollection.forEach(targetKey -> {

                    if(targetCollection.contains(sourceKey)) {
                        if(targetKey.equals(sourceKey)) {
                            targetCollection.add(copyNotNullProperty(sourceKey, targetKey));
                        }

                    } else {
                        targetCollection.add(sourceKey);
                    }
                })
            );
        }

        return targetCollection;
    }

    /**
     * Copy not null properties from one object to another of the same type
     * @param target Target class
     * @param notNullProperties List of the not null properties names to be excluded
     */
    public boolean hasNotNullProperties(Object target, List notNullProperties){

        BeanWrapper trg = new BeanWrapperImpl(target);
        for (final Field property : target.getClass().getDeclaredFields()) {
            if(trg.getPropertyValue(property.getName()) != null &&
                !notNullProperties.contains(property.getName())) {
                Object providedObject = trg.getPropertyValue(property.getName());
                if (providedObject != null) {
                    return true;
                }
            }

        }

        return false;
    }

    /**
     * Get object mapper
     *
     * @return object mapper instance
     */
    public static ObjectMapper getMapper() {
        return mapper.registerModule(new JavaTimeModule())
                      .setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
    }
}
