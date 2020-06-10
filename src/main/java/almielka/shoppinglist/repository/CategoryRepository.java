package almielka.shoppinglist.repository;

import almielka.shoppinglist.domain.Category;
import org.springframework.stereotype.Repository;

/**
 * Extension of {@link AbstractRepository} with concrete generic Category
 * includes additional methods only for Category
 *
 * @author Anna S. Almielka
 */

@Repository
public interface CategoryRepository extends AbstractRepository<Category> {

}
