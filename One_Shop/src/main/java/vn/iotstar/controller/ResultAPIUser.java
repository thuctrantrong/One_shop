package vn.iotstar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.iotstar.entity.User;
import vn.iotstar.service.UserService;

import java.util.List;

@RequestMapping("/admin/api/employees")
@RestController
public class ResultAPIUser {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllEmployees() {
        return userService.findAllUsers();
    }
    @PutMapping("/edit/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable("userId") int userId, @RequestBody User user) {
        User updatedUser = userService.updateUser(userId, user);

        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") int userId) {
        boolean isDeleted = userService.deleteUser(userId);

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
