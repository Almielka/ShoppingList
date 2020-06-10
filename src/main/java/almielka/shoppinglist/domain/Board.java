package almielka.shoppinglist.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaBean domain object representing a Board of Tasks {@link Task}.
 * Each Board has an id property {@code id} and a title property {@code title},
 * than extends class with this properties {@link AbstractNameEntity}.
 * Also each Board has:
 * <ul>
 * <li>quantity {@code openedCount} of opened tasks in this Board;</li>
 * <<li>quantity {@code closedCount} of closed tasks in this Board;</li>
 * <li>collection of tasks {@code tasks} {@link Task}.</li>
 * </ul>
 *
 * @author Anna S. Almielka
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DynamicUpdate
@DynamicInsert
@Component
@Table(name = "board", indexes = {@Index(columnList = "title", unique = true)})
public class Board extends AbstractNameEntity {

    @Column(name = "opened_count")
    private long openedCount;

    @Column(name = "closed_count")
    private long closedCount;

    @JsonIgnore
    @OneToMany(mappedBy = "board", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Task> tasks = new ArrayList<>();

}
