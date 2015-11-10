package com.itt.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T> {

    void create(T object);
    void update(T object);
    void delete(Serializable id);
    T read(Serializable id);
    List<T> getAll();
    void updateStatus(Serializable id, String status);
}
