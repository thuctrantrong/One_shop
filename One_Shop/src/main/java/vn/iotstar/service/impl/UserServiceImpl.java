package vn.iotstar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.Shop;
import vn.iotstar.entity.User;
import vn.iotstar.repository.ShopRepository;
import vn.iotstar.repository.UserRepository;
import vn.iotstar.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	private ShopRepository shopRepository;

	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	} 
	@Override
	public boolean login(String email, String password) {
	    return userRepository.findByEmail(email)
	            .map(user -> user.getPasswordHash().trim().equals(password))
	            .orElse(false); 
	}

	@Override
	public List<User> findAllUsers() {
		return userRepository.findUsersWithRole();
	}

	@Override
	public User updateUser(int userId, User user) {
		Optional<User> finds = userRepository.findById(userId);
		if (finds.isPresent()) {
			User existingUser = finds.get();

			existingUser.setUsername(user.getUsername());
			existingUser.setFullName(user.getFullName());
			existingUser.setPhoneNumber(user.getPhoneNumber());
			existingUser.setAddress(user.getAddress());
			existingUser.setRole(user.getRole());
			existingUser.setCreatedAt(user.getCreatedAt());

			return userRepository.save(existingUser);
		}

		return null;
	}

	@Override
	public boolean deleteUser(int userId) {
		Optional<User> User = userRepository.findById(userId);
		if (User.isPresent()) {
			User existingUser = User.get();
			existingUser.setRole("0");
			userRepository.save(existingUser);
			List<Shop> shops = shopRepository.findByOwnerId(userId);
			for (Shop shop : shops) {
				shop.setCondition(0);
				shopRepository.save(shop);
			}
			return true;
		}
		return false;
	}

	@Override
	public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User with email " + email + " not found"));
    }
	
}
