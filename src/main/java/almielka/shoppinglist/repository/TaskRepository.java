package almielka.shoppinglist.repository;

import almielka.shoppinglist.domain.Task;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Extension of {@link AbstractRepository} with concrete generic Task
 * includes additional methods only for Task
 *
 * @author Anna S. Almielka
 */

@Repository
public interface TaskRepository extends AbstractRepository<Task> {

    List<Task> findAllByOrderByCompletedDateAsc();

}
