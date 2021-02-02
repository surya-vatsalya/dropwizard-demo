package com.flipkart.client;

import com.flipkart.bean.*;
import com.flipkart.constant.StatementConstants;
import com.flipkart.exception.LimitExceededException;
import com.flipkart.exception.RepeatException;
import com.flipkart.service.AdminInterface;
import com.flipkart.service.AdminOperation;
import com.flipkart.service.UserOperation;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author JEDI05
 */

/**
 * Class of Admin Client that gives different options to admin
 */
public class AdminCRSClient {
    private static Logger logger = Logger.getLogger(AdminCRSClient.class);
    private int adminId;
    AdminInterface adminOperation = new AdminOperation(this.adminId);
    Scanner sc = new Scanner(System.in);
    UserOperation userOperation = new UserOperation();

    public AdminCRSClient(int adminId) {
        this.adminId = adminId;
    }

    /**
     * Displays menu for the admin
     */
    public void displayMenu() {

        int choice;
        do {
            showChoices();
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    viewCourses();
                    break;
                case 2:
                    addNewCourse();
                    break;
                case 3:
                    deleteCourse();
                    break;
                case 4:
                    assignCourseToStudent();
                    break;
                case 5:
                    addProfessor();
                    break;
                case 6:
                    dropProfessor();
                    break;
                case 7:
                    viewStudentsInCourse();
                    break;
                case 8:
                    showNotifications();
                    break;
                case 9:
                    getAllCourseRequests();
                    break;
                case 10:
                    getAllStudents();
                    break;
                case 11:
                    getAllProfessors();
                    break;
                case 12:
                    return;
            }
        } while (true);
    }


    /**
     * Displays available choices to the admin
     */
    void showChoices() {
        System.out.println(StatementConstants.Choice);
        System.out.println(StatementConstants.ViewCourses);
        System.out.println(StatementConstants.AddNewCourse);
        System.out.println(StatementConstants.DropCourse);
        System.out.println(StatementConstants.AssignCourseToStudent);
        System.out.println(StatementConstants.AddProfessor);
        System.out.println(StatementConstants.RemoveProfessor);
        System.out.println(StatementConstants.ViewStudentsInCourse);
        System.out.println("8. " + StatementConstants.showNotification);
        System.out.println("9. View All Course Requests");
        System.out.println("10. View all students");
        System.out.println("11. View all professors");
        System.out.println(StatementConstants.Logout);
    }

    /**
     * Displays courses with their ids and names from course catalog
     */
    void viewCourses() {
        List<Course> courseList = adminOperation.viewCoursesInCatalog();
        for (Course course : courseList) {
            System.out.println("Course ID: " + course.getCourseId());
            System.out.println("Course Name: " + course.getName());
            System.out.println("Department: "+ course.getDepartment());
            System.out.println("Course Fees: "+ course.getFees());
            System.out.println("Professor Id: "+ course.getProfessorId());
            System.out.println();
        }
    }

    /**
     * Gathers required information to add new courses in the course catalog
     */
    void addNewCourse() {
        System.out.println(StatementConstants.CId);
        int courseId = Integer.parseInt(sc.nextLine());
        System.out.println(StatementConstants.Cname);
        String courseName = sc.nextLine();
        System.out.println(StatementConstants.Dept);
        String department = sc.nextLine();
        System.out.println(StatementConstants.PId);
        int professorId = Integer.parseInt(sc.nextLine());
        System.out.println(StatementConstants.Fees);
        int fees = Integer.parseInt(sc.nextLine());
        Course course = new Course(courseName, courseId, professorId, department, fees); // zero professor Id means not assigned since null can not be used
        try {
            adminOperation.addCourseInCatalog(course);
        } catch (RepeatException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Uses course id to delete a particular course
     */
    void deleteCourse() {
        System.out.println(StatementConstants.CId);
        int courseId = Integer.parseInt(sc.nextLine());
        adminOperation.dropCourseInCatalog(courseId);
    }

    /**
     * Uses course Id and student Id to assign a particular course to a student
     */
    void assignCourseToStudent() {
        System.out.println(StatementConstants.CId);
        int courseId = Integer.parseInt(sc.nextLine());
        System.out.println(StatementConstants.SId);
        int studentId = Integer.parseInt(sc.nextLine());
        try {
            adminOperation.AssignStudentToCourse(studentId, courseId);
        } catch (LimitExceededException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Gathers details of professor and assigns role of Professor
     */
    void addProfessor() {
        System.out.println(StatementConstants.PId);
        int professorId = Integer.parseInt(sc.nextLine());
        System.out.println(StatementConstants.PName);
        String professorName = sc.nextLine();
        System.out.println(StatementConstants.PGender);
        String professorGender = sc.nextLine();
        System.out.println(StatementConstants.Uname);
        String username = sc.nextLine();
        System.out.println(StatementConstants.Pwd);
        String password = sc.nextLine();
        Professor professor = new Professor(professorName, professorId, professorGender);
        adminOperation.addProfessor(professor, username, password);
    }

    /**
     * Uses Professor username to drop him/her
     */
    void dropProfessor() {
        System.out.println(StatementConstants.Uname);
        String username = sc.nextLine();
        adminOperation.dropProfessor(username);
    }

    /**
     * Displays student details of all the students registered in a particular course
     */
    private void viewStudentsInCourse() {
        System.out.println(StatementConstants.CIdToView);
        int courseId = Integer.parseInt(sc.nextLine());
        List<Student> studentsInCourse = adminOperation.viewStudentInCourse(courseId);

        System.out.println("The List of Students enrolled in " + courseId);
        for (Student student : studentsInCourse) {
            System.out.println("Student Id: " + student.getStudentId());
            System.out.println("Name: " + student.getName());
            System.out.println("Branch: " + student.getBranch());
            System.out.println("Semester: " + student.getSemester());
            System.out.println("Gender: " + student.getGender() + "\n");
        }
    }


    public void getAllCourseRequests() {
        List<RequestedCourse> requestedCourseList = adminOperation.getAllRequestedCourses();
        System.out.println("All Course Requests : ");

        for (RequestedCourse requestedCourse : requestedCourseList) {
            System.out.println("Student Id: " + requestedCourse.getStudentId());
            System.out.println("Course Id: " + requestedCourse.getCourseId());
            System.out.println(requestedCourse.isPrimary() ? "Primary Choice" : "Secondary Choice");
            System.out.println();
        }
    }


    public void getAllProfessors() {
        List<Professor> professorList = adminOperation.getAllProfessors();
        System.out.println("Professor List:\n");
        for (Professor professor : professorList) {
            System.out.println("Professor Id: " + professor.getProfessorId());
            System.out.println("Name: " + professor.getName());
            System.out.println("Gender: " + professor.getGender());
            System.out.println();
        }
    }


    public void getAllStudents() {
        List<Student> studentsList = adminOperation.getAllStudents();
        System.out.println("Student List:\n");
        for (Student student : studentsList) {
            System.out.println("Student Id: " + student.getStudentId());
            System.out.println("Name: " + student.getName());
            System.out.println("Gender: " + student.getGender());
            System.out.println("Branch: " + student.getBranch());
            System.out.println("Semester: " + student.getSemester());
            System.out.println();
        }
    }


    /**
     * Sends notification to a particular user
     */
    public void showNotifications() {
        String username = adminOperation.getAdminUsername();
        List<Notification> notificationList = userOperation.showNotifications(username);
        int count = 0;
        for (Notification notification : notificationList) {
            count++;
            System.out.println(count + ". -> " + notification.getNotificationText());
        }
    }

}