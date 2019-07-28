package dao;

import java.util.List;

public interface GenericDao <T, ID> {
    void save(T t);
    T get(ID id);
    void update(T t);
    void delete(ID t);
    List<T> getAll();
}