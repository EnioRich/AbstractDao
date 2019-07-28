import dao.AbstractDao;
import dao.UserDaoImpl;
import model.User;

public class Runner {

    public static void main(String[] args) {
        User firstUser = new User(1L,"Gosha", "123");
        User secondUser = new User(2L,"Vasya", "1234");
        User thirdUser = new User(3L,"Alexey", "12345");
        AbstractDao<User, Long> ad = new UserDaoImpl(User.class);
//        ad.save(firstUser);
//        ad.save(secondUser);
//        ad.save(thirdUser);
//        ad.delete(1L);
//        ad.delete(2L);
//        ad.delete(3L);
//        System.out.println(ad.get(3L));
        firstUser.setName("Andrey");
        ad.update(firstUser);
        System.out.println(ad.getAll());
    }
}