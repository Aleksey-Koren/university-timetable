package com.foxminded.koren.university.dao.interfaces;

public interface CrudDao <I, E> {
    
    E save(E entity);
    
    void update (E entity);
    
    boolean deleteById (I id);
    
    E getById (I id);
}