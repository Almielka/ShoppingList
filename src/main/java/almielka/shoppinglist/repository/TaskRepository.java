package almielka.shoppinglist.repository;

import almielka.shoppinglist.domain.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Extension of {@link AbstractRepository} with concrete generic Task
 * includes additional methods only for Task
 *
 * @author Anna S. Almielka
 */

@Repository
public interface TaskRepository extends AbstractRepository<Task> {

    /**
     * Retrieve {@link Task}s from the database, returning all tasks
     * whose field {@code competed} equals true and sort result by {@code completedDate}.
     *
     * @return a Collection of matching tasks (or an empty Collection if none found)
     */
    @Query(value = "SELECT * " +
            "FROM task t " +
            "WHERE t.completed = true " +
            "ORDER BY t.completed_date",
            nativeQuery = true)
    @Transactional(readOnly = true)
    List<Task> findAllCompletedOrderByCompletedDateAsc();

}
