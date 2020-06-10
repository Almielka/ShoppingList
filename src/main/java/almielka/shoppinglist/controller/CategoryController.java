package almielka.shoppinglist.controller;

import almielka.shoppinglist.domain.Category;
import almielka.shoppinglist.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Extension of {@link AbstractController}
 * with explicitly defined the entity {@link Category} and the service {@link CategoryService},
 * Service then called in the abstract constructor {@link AbstractController#AbstractController}
 *
 * @author Anna S. Almielka
 */

@RestController
@RequestMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryController extends AbstractController<Category, CategoryService> {

    public CategoryController(CategoryService categoryService) {
        super(categoryService);
    }

    //create Category
    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> createOne(@RequestBody Category newCategory) {
        return super.createOne(newCategory);
    }

    //read Category by ID
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Category> readOneById(@PathVariable Long id) {
        return super.readOneById(id);
    }

    //read List Categories by Title containing
    @Override
    @GetMapping("/search/{title}")
    public ResponseEntity<List<Category>> readListByTitleContaining(@PathVariable String title) {
        return super.readListByTitleContaining(title);
    }

    //read All Categories order by Title ASC
    @Override
    @GetMapping("/")
    public ResponseEntity<List<Category>> readAllOrderByTitleAsc() {
        return super.readAllOrderByTitleAsc();
    }

    //update Category by ID
    @Override
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> updateOneById(@PathVariable Long id, @RequestBody Category updateCategory) {
        return super.updateOneById(id, updateCategory);
    }

    //delete Category by ID
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOneById(@PathVariable Long id) {
        return super.deleteOneById(id);
    }

}
