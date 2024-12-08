package vn.iotstar.service;


import vn.iotstar.entity.Shop;
import vn.iotstar.entity.User;

import java.util.List;

public interface UserService {
    User findUserByEmail(String email);
    boolean login(String email, String password);
    List<User> findAllUsers();
    public User updateUser(int userId, User user);
    public boolean deleteUser(int userId);
}
