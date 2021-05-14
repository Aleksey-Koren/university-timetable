package com.foxminded.koren.university.dao.interfaces;

import java.util.List;

public interface CrudDao <I, E> {
    
    E save(E entity);
    
    void update (E entity);
    
    boolean deleteById (I id);
    
    E getById (I id);
    
    List<E> getAll ();
}