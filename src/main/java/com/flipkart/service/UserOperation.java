package com.flipkart.service;

import com.flipkart.bean.Notification;
import com.flipkart.bean.Student;
import com.flipkart.dao.UserDaoImplement;

import java.util.List;

/**
 * @author JEDI05
 */

/**
 * User service class that implements UserInterface
 */
public class UserOperation implements UserInterface {

    /**
     * Allows user to perform login into the system
     *
     * @param username unique identifier of user required for login
     * @param password password of the user required for login
     * @return String denoting the user type
     */
    @Override
    public String login(String username, String password) {
        String userType = null;
        userType = UserDaoImplement.login(username, password);
        return userType;
    }

    /**
     * Allows student to do registration
     *
     * @param student  object of student class paased for registration
     * @param username unique identifier of user required for registration
     * @param password password of the user required for registration
     */
    @Override
    public Boolean registerStudent(Student student, String username, String password) {
        return UserDaoImplement.registerStudent(student, username, password);
    }

    /**
     * Allows user to perform forgot password
     *
     * @param username unique identifier of user used for forgot password
     * @return String denoting whether the forgot password was successful or not
     */
    @Override
    public String forgotPassword(String username) {
        return UserDaoImplement.forgotPassword(username);
    }

    /**
     * Allows user registration
     *
     * @param username unique identifier of user used for registration
     * @param password password of the user used for registration
     * @param role     denoting usertype
     * @return true if successful registration else false
     */
    @Override
    public Boolean registerUser(String username, String password, String role) {
        return UserDaoImplement.registerUser(username, password, role);
    }

    /**
     *
     * @param username
     * @return List of Notifications
     */
    @Override
    public List<Notification> showNotifications(String username){
        return UserDaoImplement.showNotifications(username);
    }

}