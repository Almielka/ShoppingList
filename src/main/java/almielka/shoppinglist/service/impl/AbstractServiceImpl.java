package almielka.shoppinglist.service.impl;

import almielka.shoppinglist.domain.AbstractIdEntity;
import almielka.shoppinglist.repository.AbstractRepository;
import almielka.shoppinglist.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link AbstractService}
 * includes parameterized constructor for the future repository
 * and with override all methods
 *
 * @param <T> Class extends {@link AbstractIdEntity}
 * @param <R> Repository extends {@link AbstractRepository <T>}
 *
 * @author Anna S. Almielka
 */

public abstract class AbstractServiceImpl <T extends AbstractIdEntity, R extends AbstractRepository <T>>
        implements  AbstractService <T> {

    protected final R repository;

    @Autowired
    public AbstractServiceImpl(R repository) {
        this.repository = repository;
    }

    @Override
    public <S extends T> S saveAndFlush(S entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public Optional<T> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<T> findAllByOrderByTitleAsc() {
        return repository.findAllByOrderByTitleAsc();
    }

    @Override
    public List<T> findByTitleContaining(String title) {
        return repository.findByTitleContaining(title);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
