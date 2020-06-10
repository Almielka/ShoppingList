package almielka.shoppinglist.service;

import almielka.shoppinglist.domain.AbstractIdEntity;

import java.util.List;
import java.util.Optional;

/**
 * Generic Service interface with common CRUD methods
 *
 * @author Anna S. Almielka
 */


public interface AbstractService<T extends AbstractIdEntity> {
    <S extends T> S saveAndFlush(S entity);
    Optional<T> findById(Long id);
    List<T> findAllByOrderByTitleAsc();
    List<T> findByTitleContaining(String title);
    void deleteById(Long id);

}
