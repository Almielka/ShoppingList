package almielka.shoppinglist.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;


/**
 * JavaBean domain object representing a Statistics.
 * Statistics has an id property {@code id},
 * than extends class with this property {@link AbstractIdEntity}.
 * Also Statistics has:
 * <ul>
 * <li>quantity {@code openedTotal} of opened tasks in general;</li>
 * <<li>quantity {@code closedTotal} of closed tasks in general.</li>
 * </ul>
 *
 * @author Anna S. Almielka
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component
@Table(name = "stat")
public class Stat extends AbstractIdEntity {

    @Column(name = "opened_total")
    private long openedTotal;

    @Column(name = "closed_total")
    private long closedTotal;

}