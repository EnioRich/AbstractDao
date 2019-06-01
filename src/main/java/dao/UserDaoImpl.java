package dao;

import model.MyQuery;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends AbstractDao<User, Long> implements UserDao {

    public UserDaoImpl(Connection connection) {
        super(connection);
    }

    public void save(User user) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(MyQuery.save())) {
            setValues(preparedStatement, user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User get(Long id) {
        User newUser = new User();
        try (PreparedStatement preparedStatement = connection.prepareStatement(MyQuery.selectById())) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString(2);
                String password = resultSet.getString(3);
                newUser = new User(id, name, password);
                return newUser;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newUser;
    }

    public void delete(Long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(MyQuery.delete())) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(MyQuery.selecAll());
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String pass = resultSet.getString("password");
                User user = new User(id, name, pass);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void update(User user) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(MyQuery.update(user))) {
            setValues(preparedStatement, user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setValues(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.executeUpdate();
    }
}