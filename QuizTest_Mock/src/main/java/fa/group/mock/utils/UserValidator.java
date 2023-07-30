/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fa.group.mock.utils;

import fa.group.mock.entity.User;
import fa.group.mock.forms.UserForm;
import fa.group.mock.services.UserService;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator; 

/**
 *
 * @author Duc Huy
 */
@Component 
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == UserForm.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserForm userForm = (UserForm) target;

        // Kiểm tra các field của UserForm.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty.userForm.username");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fullName", "NotEmpty.userForm.fullName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.userForm.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotEmpty.userForm.confirmPassword");

        if (!errors.hasFieldErrors("username")) {
            User dbUser = userService.getUserByUsername(userForm.getUsername());
            if (dbUser != null) {
                // Tên tài khoản đã bị sử dụng bởi người khác.
                errors.rejectValue("username", "Duplicate.userForm.username");
            }
        }

        if (!errors.hasErrors()) {
            if (!userForm.getConfirmPassword().equals(userForm.getPassword())) {
                errors.rejectValue("confirmPassword", "Match.userForm.confirmPassword");
            }
        }
    }

}
