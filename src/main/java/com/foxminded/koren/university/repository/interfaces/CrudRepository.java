package com.foxminded.koren.university.repository.interfaces;

import java.util.List;

public interface CrudRepository<I, E> {
    
    E save(E entity);
    
    void update (E entity);
    
    void deleteById (I id);
    
    E getById (I id);
    
    List<E> getAll ();
}