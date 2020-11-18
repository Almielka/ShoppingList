package almielka.shoppinglist.controller;

import almielka.shoppinglist.domain.Board;
import almielka.shoppinglist.domain.Category;
import almielka.shoppinglist.domain.Task;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TaskControllerTest {
    @Autowired
    private TaskController taskController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    private static final String URL = "/tasks";

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    public void createOne() throws Exception {
        Task newTask = new Task();
        newTask.setTitle("New Task for Test");
        newTask.setDescription("Description for new Task for Test");
        Board board = new Board();
        board.setId(2L);
        newTask.setBoard(board);
        Category category = new Category();
        category.setId(2L);
        newTask.setCategory(category);
        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(convertToJson(newTask)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void readOneByIdSuccess() throws Exception {
        mockMvc.perform(get(URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1));
    }

    @Test
    public void readOneByIdFailed() throws Exception {
        mockMvc.perform(get(URL + "/99")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void readListByTitleContaining() throws Exception {
        mockMvc.perform(get(URL + "/search/i")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", containsStringIgnoringCase("i")));
    }

    @Test
    public void readAllOrderByTitleAsc() throws Exception {
        mockMvc.perform(get(URL + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", containsString("Add test")));
    }

    @Test
    public void readAllCompletedOrderByCompletedDateAsc() throws Exception {
        mockMvc.perform(get(URL + "/order-by-completed-date/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].completed").value(true));
    }

    @Test
    public void updateOneById() throws Exception {
        Task updateTask = new Task();
        updateTask.setId(2L);
        updateTask.setTitle("Updated Bread");
        updateTask.setAmount(2);
        updateTask.setCompleted(true);
        mockMvc.perform(put(URL + "/2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(convertToJson(updateTask)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(updateTask.getId()))
                .andExpect(jsonPath("title").value(updateTask.getTitle()))
                .andExpect(jsonPath("amount").value(updateTask.getAmount()))
                .andExpect((jsonPath("completed").value(updateTask.getCompleted())));
    }

    @Test
    public void deleteOneById() throws Exception {
        mockMvc.perform(delete(URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private String convertToJson(Task task) throws JsonProcessingException {
        return objectMapper.writeValueAsString(task);
    }
}