package ae.rta.dls.backend.config.audit;

import ae.rta.dls.backend.service.dto.sys.EntityAuditConfigurationDTO;
import ae.rta.dls.backend.service.sys.EntityAuditConfigurationService;
import ae.rta.dls.backend.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.Optional;

public class EntityAuditEventListener extends AuditingEntityListener {

    private final Logger log = LoggerFactory.getLogger(EntityAuditEventListener.class);

    private static final String ENTITY_AUDIT_CONFIGURATION = "ae.rta.dls.backend.domain.sys.EntityAuditConfiguration";

    private static BeanFactory beanFactory;

    static void setBeanFactory(BeanFactory beanFactory) {
        EntityAuditEventListener.beanFactory = beanFactory;
    }

    @PrePersist
    public void onPrePersist(Object target) throws NoSuchFieldException, IllegalAccessException {

        // Retrieve entity class with reflection
        Class<?> entityClass = target.getClass();

        Field privateLongField = entityClass.getDeclaredField("id");
        if(privateLongField == null) {
            return;
        }
        privateLongField.setAccessible(true);
        Long entityId = (Long) privateLongField.get(target);
        privateLongField.setAccessible(false);

        if(entityId != null) {
            throw new BadRequestAlertException("Its not allowed to pass Id when create a new entity: " + entityId);
        }
    }

    @PostPersist
    public void onPostCreate(Object target) {
        try {
            if (!isEntityAuditRequired(target)) {
                log.debug("The provided entity: {} is not requires an audit during create", target.getClass().getName());
                return;
            }
            AsyncEntityAuditEventWriter asyncEntityAuditEventWriter = beanFactory.getBean(AsyncEntityAuditEventWriter.class);
            asyncEntityAuditEventWriter.writeAuditEvent(target, EntityAuditAction.CREATE);
        } catch (NoSuchBeanDefinitionException e) {
            log.error("No bean found for AsyncEntityAuditEventWriter during create");
        } catch (Exception e) {
            log.error("Exception while persisting create audit entity {}", e);
        }
    }

    @PostUpdate
    public void onPostUpdate(Object target) {
        try {
            if (!isEntityAuditRequired(target)) {
                log.debug("The provided entity: {} is not requires an audit during update", target.getClass().getName());
                return;
            }
            AsyncEntityAuditEventWriter asyncEntityAuditEventWriter = beanFactory.getBean(AsyncEntityAuditEventWriter.class);
            asyncEntityAuditEventWriter.writeAuditEvent(target, EntityAuditAction.UPDATE);
        } catch (NoSuchBeanDefinitionException e) {
            log.error("No bean found for AsyncEntityAuditEventWriter during update");
        } catch (Exception e) {
            log.error("Exception while persisting update audit entity {}", e);
        }
    }

    @PostRemove
    public void onPostRemove(Object target) {
        try {
            if (!isEntityAuditRequired(target)) {
                log.debug("The provided entity: {} is not requires an audit during delete", target.getClass().getName());
                return;
            }
            AsyncEntityAuditEventWriter asyncEntityAuditEventWriter = beanFactory.getBean(AsyncEntityAuditEventWriter.class);
            asyncEntityAuditEventWriter.writeAuditEvent(target, EntityAuditAction.DELETE);
        } catch (NoSuchBeanDefinitionException e) {
            log.error("No bean found for AsyncEntityAuditEventWriter during delete");
        } catch (Exception e) {
            log.error("Exception while persisting delete audit entity {}", e);
        }
    }

    /**
     * Check if the provided entity needs audit
     *
     * @param target Target entity object
     * @return True if audit is required, false otherwise
     */
    private boolean isEntityAuditRequired(Object target) {
        // Check the object availability
        if (target == null) {
            return false;
        }
        // Retrieve entity class with reflection
        Class<?> entityClass = target.getClass();

        if (ENTITY_AUDIT_CONFIGURATION.equals(entityClass.getName())) {
            return true;
        }
        // Get the service bean
        EntityAuditConfigurationService entityAuditConfigurationService = beanFactory
            .getBean(EntityAuditConfigurationService.class);
        Optional<EntityAuditConfigurationDTO> entityConfiguration = entityAuditConfigurationService.findByEntityName(entityClass.getName());
        return entityConfiguration.isPresent() ? entityConfiguration.get().isNeedsAudit() : Boolean.FALSE;
    }
}
