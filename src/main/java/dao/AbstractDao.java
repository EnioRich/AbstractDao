package dao;

import connect.DataBaseConnector;

import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T, ID> implements GenericDao<T, ID> {

    private Class<T> tClass;

    public AbstractDao(Class<T> tClass) {
        this.tClass = tClass;
    }

    private static final Logger logger = Logger.getLogger(AbstractDao.class);

    private DataBaseConnector dataBaseConnector = new DataBaseConnector();
    private Connection connection = dataBaseConnector.connect();

    public void save(T t) {
        String query = "INSERT INTO "
                + getTableName()
                + " (" + getFieldsNames()
                + ") VALUES ("
                + getValues(t)
                + ")";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            logger.debug("Couldn't save object", e);
        }
    }

    public T get(ID id) {
        try {
            T object = tClass.newInstance();
            Field[] fields = tClass.getDeclaredFields();
            String query = "SELECT * FROM "
                    + getTableName()
                    + " WHERE id ="
                    + id
                    + "";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                for (Field field : fields) {
                    field.setAccessible(true);
                    field.set(object, resultSet.getObject(field.getName()));
                }
            }
            return object;
        } catch (Exception e) {
            logger.debug("Couldn't get object", e);
        }
        return (T) Optional.empty();

    }

    public void update(T t) {
        try {
            StringBuilder sb = new StringBuilder();
            Field[] fields = tClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                sb.append(field.getName())
                        .append("='")
                        .append(field.get(t))
                        .append("', ");
            }
            sb.delete(sb.length() - 2, sb.length() - 1);
            String query = "UPDATE " + getTableName() + " SET " + sb + "WHERE id = " + getId(t);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (Exception e) {
            logger.debug("Couldn't update object", e);
        }
    }

    public void delete(ID t) {
        String query = "DELETE FROM "
                + getTableName()
                + " WHERE id = "
                + t
                + "";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            logger.debug("Couldn't delete object", e);
        }
    }

    public List<T> getAll() {
        List<T> list = new ArrayList<>();
        try {
            Field[] fields = tClass.getDeclaredFields();
            String query = "SELECT * FROM "
                    + getTableName()
                    + "";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                T object = tClass.newInstance();
                for (Field field : fields) {
                    field.setAccessible(true);
                    field.set(object, resultSet.getObject(field.getName()));
                }
                list.add(object);
            }
            return list;
        } catch (Exception e) {
            logger.debug("Couldn't get objects", e);
        }
        return list;
    }

    private StringBuilder getFieldsNames() {
        StringBuilder sb = new StringBuilder();
        Field[] fields = tClass.getDeclaredFields();
        for (Field field : fields) {
            sb.append(field.getName()).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length() - 1);
        return sb;
    }

    private StringBuilder getValues(T object) {
        StringBuilder sb = new StringBuilder();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                sb
                        .append("'")
                        .append(field.get(object))
                        .append("', ");
            } catch (IllegalAccessException e) {
                logger.debug("Couldn't connect ot Database", e);
            }
        }
        sb.delete(sb.length() - 2, sb.length() - 1);
        return sb;
    }

    private Long getId(T object) {
        Field[] fields = object.getClass().getDeclaredFields();
        Long id = null;
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getName().equals("id")) {
                try {
                    id = (Long) field.get(object);
                } catch (IllegalAccessException e) {
                    logger.debug("Couldn't connect ot Database", e);
                }
            }
        }
        return id;
    }

    private String getTableName() {
        return tClass.getSimpleName();
    }
}