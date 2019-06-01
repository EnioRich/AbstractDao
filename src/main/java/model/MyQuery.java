package model;

public class MyQuery {

    public static String save() {
        return "INSERT INTO Users (name, password) VALUES (?, ?)";
    }

    public static String update(User user) {
        return "UPDATE Users SET name=?, password = ? WHERE id=" + user.getId();
    }

    public static String delete() {
        return "DELETE FROM Users WHERE id = ?";
    }

    public static String selectById() {
        return "SELECT * FROM Users WHERE id = ?;";
    }

    public static String selecAll() {
        return "SELECT id, name, password FROM Users";
    }
}