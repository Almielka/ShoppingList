package almielka.shoppinglist.controller;

import almielka.shoppinglist.domain.Category;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CategoryControllerTest {


    @Autowired
    private CategoryController categoryController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    private static final String URL = "/categories";

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    public void createOne() throws Exception {
        Category newCategory = new Category();
        newCategory.setTitle("New Category for Test");
        newCategory.setColor("#222222");
        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(convertToJson(newCategory)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void readOneByIdSuccess() throws Exception {
        mockMvc.perform(get(URL + "/2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(2));
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
        mockMvc.perform(get(URL + "/search/at")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", containsStringIgnoringCase("at")));
    }

    @Test
    public void readAllOrderByTitleAsc() throws Exception {
        mockMvc.perform(get(URL + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", containsString("Bakery")));
    }

    @Test
    public void updateOneById() throws Exception {
        Category updateCategory = new Category();
        updateCategory.setId(1L);
        updateCategory.setTitle("Updated Milk Products");
        updateCategory.setColor("#555555");
        mockMvc.perform(put(URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(convertToJson(updateCategory)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(updateCategory.getId()))
                .andExpect(jsonPath("title").value(updateCategory.getTitle()))
                .andExpect(jsonPath("color").value(updateCategory.getColor()));
    }

    @Test
    public void deleteOneById() throws Exception {
        mockMvc.perform(delete(URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private String convertToJson(Category category) throws JsonProcessingException {
        return objectMapper.writeValueAsString(category);
    }
}