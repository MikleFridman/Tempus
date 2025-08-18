package il.fridman.tempus.general.service;

import il.fridman.tempus.aspect.Loggable;
import il.fridman.tempus.general.entity.BasicEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Loggable
public interface EntityService<T extends BasicEntity> {

    T save(T entity);

    T update(T entity);

    @Transactional
    default boolean deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        T entity = getById(id);
        if (entity == null) {
            throw new IllegalArgumentException("Entity with ID " + id + " does not exist");
        }
        return delete(entity);
    }

    @Transactional
    default boolean delete(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }
        entity.setIsDeleted(true);
        return entity.getIsDeleted();
    }

    T getById(Long id);

    List<T> getAll();
}

