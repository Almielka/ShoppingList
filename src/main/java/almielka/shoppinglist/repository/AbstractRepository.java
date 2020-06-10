package almielka.shoppinglist.repository;

import almielka.shoppinglist.domain.AbstractIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

/**
 * Generic Repository interface with common CRUD methods
 * @param <T> the entity class related to this Repository
 *
 * @author Anna S. Almielka
 */

@NoRepositoryBean
public interface AbstractRepository<T extends AbstractIdEntity> extends JpaRepository<T, Long> {
    <S extends T> S saveAndFlush(S entity);
    Optional<T> findById(Long id);
    List<T> findAllByOrderByTitleAsc();
    List<T> findByTitleContaining(String title);
    void deleteById(Long id);

}
