package dao;

import java.sql.Connection;
import java.util.List;

public abstract class AbstractDao<T, ID> implements GenericDao<T, ID> {

    protected final Connection connection;

    protected AbstractDao(Connection connection) {
        this.connection = connection;
    }

    public void save(T t) {
    }

    public T get(ID id) {
        return null;
    }

    public void update(T t) {
    }

    public void delete(ID t) {
    }

    public List<T> getAll() {
        return null;
    }
}