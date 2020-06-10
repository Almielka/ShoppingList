package almielka.shoppinglist.controller;

import almielka.shoppinglist.domain.Board;
import almielka.shoppinglist.domain.Category;
import almielka.shoppinglist.domain.Task;
import almielka.shoppinglist.service.BoardService;
import almielka.shoppinglist.service.CategoryService;
import almielka.shoppinglist.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Extension of {@link AbstractController}
 * with explicitly defined the entity {@link Task} and the service {@link TaskService},
 * Service then called in the abstract constructor {@link AbstractController#AbstractController}
 *
 * @author Anna S. Almielka
 */

@RestController
@RequestMapping(value = "/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskController extends AbstractController<Task, TaskService> {

    private CategoryService categoryService;
    private BoardService boardService;

    public TaskController(TaskService taskService, CategoryService categoryService, BoardService boardService) {
        super(taskService);
        this.categoryService = categoryService;
        this.boardService = boardService;
    }

    //create Task
    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Task> createOne(@RequestBody Task newTask) {
        Optional<Category> categoryOptional = categoryService.findById(newTask.getCategory().getId());
        Optional<Board> boardOptional = boardService.findById(newTask.getBoard().getId());
        if (categoryOptional.isPresent() && boardOptional.isPresent()) {
            newTask.setCreatedDate(Instant.now());
            return super.createOne(newTask);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    //read Task by ID
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Task> readOneById(@PathVariable Long id) {
        return super.readOneById(id);
    }

    //read List Tasks by Title Containing
    @Override
    @GetMapping("/search/{title}")
    public ResponseEntity<List<Task>> readListByTitleContaining(@PathVariable String title) {
        return super.readListByTitleContaining(title);
    }

    //read All Tasks order by Title ASC
    @Override
    @GetMapping("/")
    public ResponseEntity<List<Task>> readAllOrderByTitleAsc() {
        return super.readAllOrderByTitleAsc();
    }

    //read All Tasks order by Completed Date ASC
    @GetMapping("/order-by-completed-date/")
    public ResponseEntity<List<Task>> readAllOrderByCompletedDateAsc() {
        List<Task> tasks = service.findAllByOrderByCompletedDateAsc();
        return checkTaskList(tasks);
    }

    //update Task by ID
    @Override
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Task> updateOneById(@PathVariable Long id, @RequestBody Task updateTask) {
        Optional<Task> taskOptional = service.findById(id);
        if (taskOptional.isPresent()) {
            updateTask.setId(taskOptional.get().getId());
            updateTask.setCreatedDate(taskOptional.get().getCreatedDate());
            if (updateTask.getCompleted() == null) {
                updateTask.setCompleted(taskOptional.get().getCompleted());
                updateTask.setCompletedDate(taskOptional.get().getCompletedDate());
            }
            if (updateTask.getCompleted() != taskOptional.get().getCompleted()) {
                isCompleted(updateTask);
            }
            service.saveAndFlush(updateTask);
            return ResponseEntity.status(HttpStatus.OK).body(updateTask);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //delete Task by ID
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOneById(@PathVariable Long id) {
        return super.deleteOneById(id);
    }

    private ResponseEntity<List<Task>> checkTaskList(List<Task> tasks) {
        if (tasks.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(tasks);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    private void isCompleted(Task task) {
        if (task.getCompleted()) {
            task.setCompletedDate(Instant.now());
        }
    }

}
