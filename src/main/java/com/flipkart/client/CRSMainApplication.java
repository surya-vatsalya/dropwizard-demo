package com.flipkart.client;

import com.flipkart.bean.Admin;
import com.flipkart.bean.Professor;
import com.flipkart.bean.Student;
import com.flipkart.constant.StatementConstants;
import com.flipkart.dao.*;
import com.flipkart.service.UserInterface;
import com.flipkart.service.UserOperation;
import org.apache.log4j.Logger;

import java.util.Scanner;

/**
 * @author JEDI05
 */

/**
 * Class of CRSApplication that shows options for login, registration and forgot password to the user
 */
public class CRSMainApplication {
    private static Logger logger = Logger.getLogger(CRSMainApplication.class);
    private static Scanner sc = new Scanner(System.in);

    public static boolean loggedIn = false;

    public static void main(String[] args) {

        System.out.println(StatementConstants.Welcome);
        Boolean inApplication = true;
        do {
            System.out.println(StatementConstants.Choice);
            System.out.println(StatementConstants.Login);
            System.out.println(StatementConstants.Registration);
            System.out.println(StatementConstants.ForgotPassword);
            System.out.println(StatementConstants.Exit);

            int n = Integer.parseInt(sc.nextLine());

            switch (n) {
                case 1: // Login
                    System.out.println(StatementConstants.LoginToSystem);
                    CRSMainApplication.login();
                    break;

                case 2: // Register as a Student
                    System.out.println(StatementConstants.StudentRegistration);
                    CRSMainApplication.registerStudent();
                    break;

                case 3: // Forgot Password
                    System.out.println(StatementConstants.ForgotPasswordInSystem);
                    CRSMainApplication.forgotPassword();
                    break;

                default:
                    inApplication = false;
                    break;
            }

        } while (inApplication);
        System.out.println(StatementConstants.ExitSystem);
    }

    /**
     * Uses username and password of users for logging into the system
     */
    public static void login() {

        System.out.println(StatementConstants.Uname);
        String username = sc.nextLine();
        System.out.println(StatementConstants.Pwd);
        String password = sc.nextLine();
        UserInterface userOperation = new UserOperation();

        String userType = userOperation.login(username, password);

        switch (userType.toLowerCase()) {
            case "a": // Admin Login
                System.out.println("Welcome " + username + "!");
                System.out.println(StatementConstants.LoggedAsAdmin);
                CRSMainApplication.loggedIn = true;
                AdminDaoInterface adminDaoImpl = new AdminDaoImplement();
                Admin admin = adminDaoImpl.getAdmin(username);
                AdminCRSClient adminCRSClient = new AdminCRSClient(admin.getAdminId());
                adminCRSClient.displayMenu();
                break;
            case "p": // Professor Login
                System.out.println("Welcome " + username + "!");
                System.out.println(StatementConstants.LoggedAsProfessor);
                CRSMainApplication.loggedIn = true;
                ProfessorDaoInterface professorDaoImpl = new ProfessorDaoImplement();
                Professor professor = professorDaoImpl.getProfessor(username);
                ProfessorCRSClient professorCRSClient = new ProfessorCRSClient(professor.getProfessorId());
                professorCRSClient.displayMenu();
                break;
            case "s": // Student Login
                System.out.println("Welcome " + username + "!");
                System.out.println(StatementConstants.LoggedAsStudent);
                CRSMainApplication.loggedIn = true;
                StudentDaoInterface studentDaoImpl = new StudentDaoImplement();
                Student student = studentDaoImpl.getStudent(username);
                StudentCRSClient studentCRSClient = new StudentCRSClient(student.getStudentId());
                studentCRSClient.displayMenu();
                break;
            default:
                System.out.println(StatementConstants.InvalidUser); // maybe add an exception here
        }
    }

    /**
     * Logouts the system by setting loggedIn variable to false
     */

    public static void logout() {
        CRSMainApplication.loggedIn = false;
    }


    /**
     * Uses id, name, branch, gender, semester, username and password for Student Registration
     */
    public static void registerStudent() {
        System.out.println(StatementConstants.SId);
        int studentId = Integer.parseInt(sc.nextLine());
        System.out.println(StatementConstants.SName);
        String name = sc.nextLine();
        System.out.println(StatementConstants.Branch);
        String branch = sc.nextLine();
        System.out.println(StatementConstants.Gender);
        String gender = sc.nextLine();
        if (gender.equalsIgnoreCase("m")) {
            gender = "male";
        } else {
            gender = "female";
        }
        System.out.println(StatementConstants.Semester);
        int semester = Integer.parseInt(sc.nextLine());
        Student student = new Student(name, studentId, gender, branch, semester);

        System.out.println(StatementConstants.Uname);
        String username = sc.nextLine();
        System.out.println(StatementConstants.Pwd);
        String password = sc.nextLine();
        UserInterface userOperation = new UserOperation();
        Boolean studentRegistered = userOperation.registerStudent(student, username, password); // return 1 if account created, else return 0

        if (studentRegistered == false) {
            System.out.println(StatementConstants.RegFailed);
        }

    }

    /**
     * Uses username for Forgot Password
     */
    public static void forgotPassword() {
        System.out.println(StatementConstants.Uname);
        String username = sc.nextLine();
        UserInterface userOperation = new UserOperation();
        System.out.println(userOperation.forgotPassword(username));
    }

}