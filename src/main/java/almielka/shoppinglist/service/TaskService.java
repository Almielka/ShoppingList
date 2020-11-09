package almielka.shoppinglist.service;

import almielka.shoppinglist.domain.Task;

import java.util.List;

/**
 * TaskService interface includes additional methods only for Task
 *
 * @author Anna S. Almielka
 */

public interface TaskService extends AbstractService<Task> {

    List<Task> findAllCompletedOrderByCompletedDateAsc();

}
