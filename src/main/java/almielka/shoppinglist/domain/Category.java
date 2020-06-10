package almielka.shoppinglist.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaBean domain object representing a Category of Task {@link Task}.
 * Each Category has an id property {@code id} and a title property {@code title},
 * than extends class with this properties {@link AbstractNameEntity}.
 * Also each Category has:
 * <ul>
 * <li>color {@code color} of the category;</li>
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
@Table(name = "category", indexes = {@Index(columnList = "title", unique = true)})
public class Category extends AbstractNameEntity {


    @Column
    private String color;

    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Task> tasks = new ArrayList<>();

}

