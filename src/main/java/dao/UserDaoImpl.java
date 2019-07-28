package dao;

import model.User;

public class UserDaoImpl extends AbstractDao<User, Long> implements UserDao {

    public UserDaoImpl(Class<User> userClass) {
        super(userClass);
    }
}