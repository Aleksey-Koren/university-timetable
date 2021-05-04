package com.foxminded.koren.university.dao;

import java.util.Optional;

public interface Dao <I, E> {
    
    E save(E entity);
    
    void update (E entity);
    
    boolean deleteById (I id);
    
    Optional<E> getById (I id);
}