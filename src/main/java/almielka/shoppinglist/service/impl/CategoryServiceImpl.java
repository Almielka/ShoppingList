package almielka.shoppinglist.service.impl;

import almielka.shoppinglist.domain.Category;
import almielka.shoppinglist.repository.CategoryRepository;
import almielka.shoppinglist.service.CategoryService;
import org.springframework.stereotype.Service;

/**
 * Extension of {@link AbstractServiceImpl}
 * with explicitly defined the repository,
 * which is then called in the abstract constructor {@link AbstractServiceImpl#AbstractServiceImpl}
 * entity {@link Category}, repository {@link CategoryRepository}
 *
 * @author Anna S. Almielka
 */

@Service
public class CategoryServiceImpl extends AbstractServiceImpl<Category, CategoryRepository>
        implements CategoryService {

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        super(categoryRepository);
    }

}
