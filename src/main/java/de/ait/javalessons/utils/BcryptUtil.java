package de.ait.javalessons.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptUtil {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "adminpass";
        String encodedPassword = encoder.encode(password);
        System.out.println(password + "-->" + encodedPassword);
    }
}

//userpass-->$2a$10$zW.XmWswKr9LgkrqJ0wKtuHOuFEhrNOIYVd/CZh0pauMGVch8WrL2
//adminpass-->$2a$10$NAytWsRVGHSKkAULTGHEoesSXYIbPHyN1MfKKAEkHlz/gkyzMUM4q