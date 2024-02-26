package test;

import models.Module;
import models.User;
import services.UserService;
import services.LevelService;
import services.ModuleService;
import java.time.LocalDate;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        UserService us = new UserService();
        LevelService ls = new LevelService();
        ModuleService ms = new ModuleService();


        try {
            us.create(new User(
                    0, // module_id, assuming it's set to 0
                    "John", // first_name
                    "Doe", // last_name
                    "john.doe@example.com", // email
                    "password123", // password
                    "Male", // gender
                    "User", // role
                    "I'm a software engineer", // bio
                    "123456", // phone_number
                    "profile.jpg", // profile_picture
                    LocalDate.of(1990, 5, 15) // date_of_birth
            ));
            /*us.update(new User(5,
                    0, // module_id, assuming it's set to 0
                    "John", // first_name
                    "Doe", // last_name
                    "john.doe@exakjkjkjple.com", // email
                    "password123", // password
                    "Male", // gender
                    "User", // role
                    "I'm a software engineer", // bio
                    "123456", // phone_number
                    "profile.jpg", // profile_picture
                    LocalDate.of(1990, 5, 15) // date_of_birth
            ));

           // System.out.println(us.read());
            //ls.delete(1);
            /*us.create(new User(5,
                    0, // module_id, assuming it's set to 0
                    "John", // first_name
                    "Doe", // last_name
                    "john.doe@exakjkjkjple.com", // email
                    "password123", // password
                    "Male", // gender
                    "User", // role
                    "I'm a software engineer", // bio
                    "123456", // phone_number
                    "profile.jpg", // profile_picture
                    LocalDate.of(1990, 5, 15) // date_of_birth
            ));*/
            //ms.update(new Module(1,5,"oekdl"));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
