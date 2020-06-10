package almielka.shoppinglist.controller;

import almielka.shoppinglist.domain.Board;
import almielka.shoppinglist.domain.Category;
import almielka.shoppinglist.domain.Task;
import almielka.shoppinglist.service.BoardService;
import almielka.shoppinglist.service.CategoryService;
import almielka.shoppinglist.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * @author Anna S. Almielka
 */


@RestController
@RequestMapping(value = "/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskController {

    private TaskService taskService;
    private CategoryService categoryService;
    private BoardService boardService;

    public TaskController(TaskService taskService, CategoryService categoryService, BoardService boardService) {
        this.taskService = taskService;
        this.categoryService = categoryService;
        this.boardService = boardService;
    }

    //create Task
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Task> createOne(@RequestBody Task newTask) {
        Optional<Category> categoryOptional = categoryService.findById(newTask.getCategory().getId());
        Optional<Board> boardOptional = boardService.findById(newTask.getBoard().getId());
        if (categoryOptional.isPresent() && boardOptional.isPresent()) {
            newTask.setCreatedDate(Instant.now());
            return ResponseEntity.status(HttpStatus.CREATED).body(taskService.saveAndFlush(newTask));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    //read Task by ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> readOneById(@PathVariable Long id) {
        Optional<Task> taskOptional = taskService.findById(id);
        return taskOptional.map(value -> ResponseEntity.status(HttpStatus.OK).body(value))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    //read List Tasks by Title Containing
    @GetMapping("/search/{title}")
    public ResponseEntity<List<Task>> readListByTitleContaining(@PathVariable String title) {
        List<Task> tasks = taskService.findByTitleContaining(title);
        return checkTaskList(tasks);
    }

    //read All Tasks order by Title ASC
    @GetMapping("/")
    public ResponseEntity<List<Task>> readAllOrderByTitleAsc() {
        List<Task> tasks = taskService.findAllByOrderByTitleAsc();
        return checkTaskList(tasks);
    }

    //read All Tasks order by Completed Date ASC
    @GetMapping("/order-by-completed-date/")
    public ResponseEntity<List<Task>> readAllOrderByCompletedDateAsc() {
        List<Task> tasks = taskService.findAllByOrderByCompletedDateAsc();
        return checkTaskList(tasks);
    }

    //update Task by ID
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Task> updateOneById(@PathVariable Long id, @RequestBody Task updateTask) {
        Optional<Task> taskOptional = taskService.findById(id);
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
            taskService.saveAndFlush(updateTask);
            return ResponseEntity.status(HttpStatus.OK).body(updateTask);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //delete Task by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOneById(@PathVariable Long id) {
        try {
            taskService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
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
