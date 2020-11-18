package almielka.shoppinglist.controller;

import almielka.shoppinglist.domain.Board;
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
public class BoardControllerTest {

    @Autowired
    private BoardController boardController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    private static final String URL = "/boards";

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(boardController).build();
    }

    @Test
    public void createOne() throws Exception {
        Board newBoard = new Board();
        newBoard.setTitle("New Board for Test");
        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(convertToJson(newBoard)))
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
        mockMvc.perform(get(URL + "/search/li")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", containsStringIgnoringCase("li")));
    }


    @Test
    public void readAllOrderByTitleAsc() throws Exception {
        mockMvc.perform(get(URL + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", containsString("Shopping List")));
    }

    @Test
    public void updateOneById() throws Exception {
        Board updateBoard = new Board();
        updateBoard.setId(1L);
        updateBoard.setTitle("Updated Shopping List");
        mockMvc.perform(put(URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(convertToJson(updateBoard)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(updateBoard.getId()))
                .andExpect(jsonPath("title").value(updateBoard.getTitle()));
    }

    @Test
    public void deleteOneById() throws Exception {
        mockMvc.perform(delete(URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private String convertToJson(Board board) throws JsonProcessingException {
        return objectMapper.writeValueAsString(board);
    }
}