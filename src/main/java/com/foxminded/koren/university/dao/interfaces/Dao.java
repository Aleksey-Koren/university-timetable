package com.foxminded.koren.university.dao.interfaces;

import java.util.Optional;

public interface Dao <I, E> {
    
    E save(E entity);
    
    void update (E entity);
    
    boolean deleteById (I id);
    
    E getById (I id);
}