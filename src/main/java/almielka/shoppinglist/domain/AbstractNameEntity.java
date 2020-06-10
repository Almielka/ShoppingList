package almielka.shoppinglist.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * Simple JavaBean domain object adds a name property {@code title} to {@link AbstractIdEntity}.
 * Used as a base abstract class for objects needing these properties.
 *
 * @author Anna S. Almielka
 */

@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = "title")
public abstract class AbstractNameEntity extends AbstractIdEntity {

    @Column
    @NotNull
    private String title;

    @Override
    public String toString() {
        return title;
    }

}