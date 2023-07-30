/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fa.group.mock.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Duc Huy
 */
public class EncrytedPasswordUtils {
    // Encryte Password with BCryptPasswordEncoder

    public static String encrytePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

//    public static void main(String[] args) {
//        String password = "123";
//        String encrytedPassword = encrytePassword(password);
//
//        System.out.println("Encryted Password: " + encrytedPassword);
//    }
}
