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
 * @author Anna S. Almielka
 */

@RestController
@RequestMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //create Category
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> createOne(@RequestBody Category newCategory) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.saveAndFlush(newCategory));
    }

    //read Category by ID
    @GetMapping("/{id}")
    public ResponseEntity<Category> readOneById(@PathVariable Long id) {
        Optional<Category> categoryOptional = categoryService.findById(id);
        return categoryOptional.map(value -> ResponseEntity.status(HttpStatus.OK).body(value))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    //read List Categories by Title containing
    @GetMapping("/search/{title}")
    public ResponseEntity<List<Category>> readListByTitleContaining(@PathVariable String title) {
        List<Category> categories = categoryService.findByTitleContaining(title);
        return checkCategoryList(categories);
    }

    //read All Categories order by Title ASC
    @GetMapping("/")
    public ResponseEntity<List<Category>> readAllOrderByTitleAsc() {
        List<Category> categories = categoryService.findAllByOrderByTitleAsc();
        return checkCategoryList(categories);
    }

    //update Category by ID
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> updateOneById(@PathVariable Long id, @RequestBody Category updateCategory) {
        Optional<Category> categoryOptional = categoryService.findById(id);
        if (categoryOptional.isPresent()) {
            updateCategory.setId(categoryOptional.get().getId());
            categoryService.saveAndFlush(updateCategory);
            return ResponseEntity.status(HttpStatus.OK).body(updateCategory);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //delete Category by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOneById(@PathVariable Long id) {
        try {
            categoryService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    private ResponseEntity<List<Category>> checkCategoryList(List<Category> categories) {
        if (categories.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(categories);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
