package almielka.shoppinglist.service.impl;


import almielka.shoppinglist.domain.Task;
import almielka.shoppinglist.repository.TaskRepository;
import almielka.shoppinglist.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Extension of {@link AbstractServiceImpl}
 * with explicitly defined the repository,
 * which is then called in the abstract constructor {@link AbstractServiceImpl#AbstractServiceImpl}
 * class {@link Task}, repository {@link TaskRepository}
 *
 * @author Anna S. Almielka
 */

@Service
public class TaskServiceImpl extends AbstractServiceImpl<Task, TaskRepository>
        implements TaskService {

    public TaskServiceImpl(TaskRepository taskRepository) {
        super(taskRepository);
    }

    @Override
    public List<Task> findAllByOrderByCompletedDateAsc() {
        return repository.findAllByOrderByCompletedDateAsc();
    }

}
