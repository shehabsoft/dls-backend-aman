package ae.rta.dls.backend.domain.sys;

import ae.rta.dls.backend.domain.AbstractAuditingEntity;
import ae.rta.dls.backend.domain.util.MultilingualJsonConverter;
import ae.rta.dls.backend.domain.type.MultilingualJsonType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * The Domain entity.
 * @author Mena Emiel.
 */
@ApiModel(description = "Domain entity. @author Mena Emiel.")
@Entity
@Table(name = "sys_domain")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Domain extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "domaSequenceGenerator")
    @SequenceGenerator(name = "domaSequenceGenerator", sequenceName = "doma_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "description", nullable = false)
    //@Convert(converter = MultilingualJsonConverter.class)
    @Type(type = "json")
    private MultilingualJsonType description;

    @OneToMany(mappedBy = "domain")
    @OrderBy(value = "sortOrder")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DomainValue> domainValues = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Domain code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public MultilingualJsonType getDescription() {
        return description;
    }

    public Domain description(MultilingualJsonType description) {
        this.description = description;
        return this;
    }

    public void setDescription(MultilingualJsonType description) {
        this.description = description;
    }

    public Set<DomainValue> getDomainValues() {
        return domainValues;
    }

    public Domain domainValues(Set<DomainValue> domainValues) {
        this.domainValues = domainValues;
        return this;
    }

    public Domain addDomainValue(DomainValue domainValue) {
        this.domainValues.add(domainValue);
        domainValue.setDomain(this);
        return this;
    }

    public Domain removeDomainValue(DomainValue domainValue) {
        this.domainValues.remove(domainValue);
        domainValue.setDomain(null);
        return this;
    }

    public void setDomainValues(Set<DomainValue> domainValues) {
        this.domainValues = domainValues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Domain domain = (Domain) o;
        if (domain.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), domain.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Domain{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
