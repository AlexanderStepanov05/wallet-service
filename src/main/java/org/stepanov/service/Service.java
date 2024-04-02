package org.stepanov.service;

import java.util.List;
import java.util.Optional;

public interface Service<T, V> {

    Optional<V> findById(T id);

    List<V> findAll();

    void save(V v);

    boolean delete(T id);

    boolean deleteAll();
}
