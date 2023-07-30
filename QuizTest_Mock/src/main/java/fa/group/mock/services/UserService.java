/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fa.group.mock.services;

import fa.group.mock.entity.Role;
import fa.group.mock.entity.User;
import fa.group.mock.forms.UserForm;
import fa.group.mock.repository.RoleRepository;
import fa.group.mock.repository.UserRepository;
import fa.group.mock.utils.EncrytedPasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Duc Huy
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    public User getUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        return user;
    }

    public User createUser(UserForm userForm) {
        String encrytedPassword = EncrytedPasswordUtils.encrytePassword(userForm.getPassword());
        Role role = roleRepository.getById(2);
        User user = new User(null, userForm.getFullName(),
                userForm.getUsername(), encrytedPassword, userForm.getUsername(), true, role);
        return userRepository.save(user);
    }

}
