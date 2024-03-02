package models;
import java.time.LocalDate;


public class User {
    private int user_id;
    private String module_name, first_name, last_name, email, password, gender, role, bio, phone_number, profile_picture;
    private LocalDate date_of_birth;

    public User() {
    }

    public User(int user_id, String module_name, String first_name, String last_name, String email, String password, String gender, String role, String bio, String phone_number, String profile_picture, LocalDate date_of_birth) {
        this.user_id = user_id;
        this.module_name = module_name;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.role = role;
        this.bio = bio;
        this.phone_number = phone_number;
        this.profile_picture = profile_picture;
        this.date_of_birth = date_of_birth;
    }

    public User(String module_name, String first_name, String last_name, String email, String password, String gender, String role, String bio, String phone_number, String profile_picture, LocalDate date_of_birth) {
        this.module_name = module_name;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.role = role;
        this.bio = bio;
        this.phone_number = phone_number;
        this.profile_picture = profile_picture;
        this.date_of_birth = date_of_birth;
    }


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getModule_name() {
        return module_name;
    }

    public void setModule_name(String module_name) {
        this.module_name = module_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }
    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", module_name='" + module_name + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", role='" + role + '\'' +
                ", bio='" + bio + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", profile_picture='" + profile_picture + '\'' +
                ", date_of_birth=" + date_of_birth +
                '}';
    }

}