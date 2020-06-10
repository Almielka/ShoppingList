package almielka.shoppinglist.controller;

import almielka.shoppinglist.domain.AbstractIdEntity;
import almielka.shoppinglist.service.AbstractService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

/**
 * Generic Controller abstract class with common CRUD methods
 *
 * @param <T> Class extends {@link AbstractIdEntity}
 * @param <S> Service extends {@link AbstractService<T>}
 *
 * @author Anna S. Almielka
 */


public abstract class AbstractController<T extends AbstractIdEntity, S extends AbstractService<T>> {

    protected S service;

    public AbstractController(S service) {
        this.service = service;
    }

    //create One
    public ResponseEntity<T> createOne(T newEntity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveAndFlush(newEntity));
    }

    //read One by ID
    public ResponseEntity<T> readOneById(Long id) {
        Optional<T> entityOptional = service.findById(id);
        return entityOptional.map(value -> ResponseEntity.status(HttpStatus.OK).body(value))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    //read List Entities by Title containing
    public ResponseEntity<List<T>> readListByTitleContaining(String title) {
        List<T> entities = service.findByTitleContaining(title);
        return checkEntityList(entities);
    }

    //read All Entities order by Title ASC
    public ResponseEntity<List<T>> readAllOrderByTitleAsc() {
        List<T> entities = service.findAllByOrderByTitleAsc();
        return checkEntityList(entities);
    }

    //update One by ID
    public ResponseEntity<T> updateOneById(Long id, T updateEntity) {
        Optional<T> entityOptional = service.findById(id);
        if (entityOptional.isPresent()) {
            updateEntity.setId(entityOptional.get().getId());
            service.saveAndFlush(updateEntity);
            return ResponseEntity.status(HttpStatus.OK).body(updateEntity);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //delete One by ID
    public ResponseEntity<Void> deleteOneById(Long id) {
        try {
            service.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    private ResponseEntity<List<T>> checkEntityList(List<T> entities) {
        if (entities.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(entities);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
