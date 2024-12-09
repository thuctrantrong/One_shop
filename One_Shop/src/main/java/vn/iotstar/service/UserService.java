package vn.iotstar.service;

import java.util.List;

import vn.iotstar.entity.User;

public interface UserService {
    User findUserByEmail(String email);

    boolean login(String email, String password);

    List<User> findAllUsers();

    public User updateUser(int userId, User user);

    public boolean deleteUser(int userId);

    public boolean existEmail(String email);

    public Boolean existsEmail(String email);

    public User saveUser(User user);

    public User getUserByEmail(String email);

    public void updateUserResetToken(String email, String resetToken);

    public User getUserByToken(String token);

    public User updateUser(User user);
    public User changepassUser(String email, String password);


}
