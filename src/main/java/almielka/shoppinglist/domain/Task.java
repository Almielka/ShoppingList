package almielka.shoppinglist.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.Instant;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

/**
 * JavaBean domain object representing a Task.
 * Each Task has an id property {@code id} and a title property {@code title},
 * than extends class with this properties {@link AbstractNameEntity}.
 * Also each Task has:
 * <ul>
 * <li>long description {@code description} of the task;</li>
 * <li>quantity {@code amount} if it is a task from the shopping list;</li>
 * <li>boolean type {@code completed} is task was completed</li>
 * <li>date when task was added {@code createdDate}, UTC time zone;</li>
 * <li>date when task was completed {@code completedDate}, UTC time zone;</li>
 * <li>task's category {@code category} {@link Category};</li>
 * <li>task's board {@code board} {@link Board}.</li>
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
@Table(name = "task", indexes = {@Index(columnList = "title", unique = true),
        @Index(columnList = "completed"),
        @Index(columnList = "completed_date")})
public class Task extends AbstractNameEntity {

    @Column
    private String description;

    @Column
    private Integer amount;

    @Column
    private Boolean completed;

    @Column(name = "created_date")
    @NotNull
    @PastOrPresent
    private Instant createdDate;

    @Column(name = "completed_date")
    private Instant completedDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

}
