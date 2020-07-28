package ae.rta.dls.backend.domain.sys;


import ae.rta.dls.backend.domain.AbstractAuditingEntity;
import ae.rta.dls.backend.domain.util.MultilingualJsonConverter;
import ae.rta.dls.backend.domain.type.MultilingualJsonType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.io.Serializable;
import java.util.Objects;

/**
 * The DomainValue entity.
 * @author Mena Emiel.
 */
@ApiModel(description = "Domain Value entity. @author Mena Emiel.")
@Entity
@Table(name = "sys_domain_value")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DomainValue extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dovaSequenceGenerator")
    @SequenceGenerator(name = "dovaSequenceGenerator", sequenceName = "dova_seq", allocationSize = 1)
    private Long id;

    @Column(name = "value")
    private String value;

    @Column(name = "description", nullable = false)
    @Type(type = "json")
    //@Convert(converter = MultilingualJsonConverter.class)
    private MultilingualJsonType description;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @ManyToOne
    @JsonIgnoreProperties("domainValues")
    private Domain domain;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public DomainValue value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public MultilingualJsonType getDescription() {
        return description;
    }

    public DomainValue description(MultilingualJsonType description) {
        this.description = description;
        return this;
    }

    public void setDescription(MultilingualJsonType description) {
        this.description = description;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public DomainValue sortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Domain getDomain() {
        return domain;
    }

    public DomainValue domain(Domain domain) {
        this.domain = domain;
        return this;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DomainValue domainValue = (DomainValue) o;
        if (domainValue.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), domainValue.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DomainValue{" +
            "id=" + getId() +
            ", value=" + getValue() +
            ", description='" + getDescription() + "'" +
            ", sortOrder=" + getSortOrder() +
            "}";
    }
}
