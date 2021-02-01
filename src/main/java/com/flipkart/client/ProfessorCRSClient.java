package com.flipkart.client;

import com.flipkart.bean.Course;
import com.flipkart.bean.Grade;
import com.flipkart.bean.Notification;
import com.flipkart.bean.Student;
import com.flipkart.constant.StatementConstants;
import com.flipkart.dao.ProfessorDaoImplement;
import com.flipkart.exception.CourseNotAccesibleException;
import com.flipkart.exception.RepeatException;
import com.flipkart.exception.StudentNotFoundException;
import com.flipkart.service.ProfessorInterface;
import com.flipkart.service.ProfessorOperation;
import com.flipkart.service.UserOperation;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Scanner;

/**
 * @author JEDI05
 */

/**
 * Class of Professor Client that gives different options to professor
 */
public class ProfessorCRSClient {
    private static Logger logger = Logger.getLogger(ProfessorCRSClient.class);
    private static int professorId;
    private ProfessorInterface professorOperation;
    private ProfessorDaoImplement professorDaoImplement = new ProfessorDaoImplement();
    private UserOperation userOperation = new UserOperation();
    public ProfessorCRSClient(int professorId) {
        this.professorId = professorId;
        professorOperation = new ProfessorOperation(this.professorId);
    }


    Scanner sc = new Scanner(System.in);

    /**
     * Display menu for the professor
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
                    viewAssignedCourses();
                    break;
                case 3:
                    viewStudents();
                    break;
                case 4:
                    assignGrades();
                    break;
                case 5:
                    showNotifications();
                    break;
                case 6:
                    return;
            }
        } while (true);
    }

    /**
     * Displays available choices to the professor
     */
    public void showChoices() {
        System.out.println(StatementConstants.Choice);
        System.out.println(StatementConstants.ViewCourses);
        System.out.println(StatementConstants.ViewAssignedCourse);
        //System.out.println(Statements.ChooseCourse);
        System.out.println(StatementConstants.ViewStudents);
        System.out.println(StatementConstants.AssignGrades);
        System.out.println("5. " + StatementConstants.showNotification);
        System.out.println("6. Logout");
    }

    /**
     * Displays courseId, name and department of all the courses from catalog
     */
    public void viewCourses() {
        List<Course> courses = professorOperation.viewCourses();
        for (Course course : courses) {
            System.out.println(course.getCourseId());
            System.out.println(course.getName());
            System.out.println(course.getDepartment());
        }
    }

    /**
     * Displays Course Id and Course name of the courses assigned to a professor
     */
    private void viewAssignedCourses() {
        List<Course> assignedCourseList = professorOperation.viewAssignedCourses();
        for (Course course : assignedCourseList) {
            System.out.println("Course ID: " + course.getCourseId());
            System.out.println("Course Name: " + course.getName());
        }
    }

    /**
     * Allows professor to select a particular course to teach
     */
    public void chooseCourse() {
        System.out.println(StatementConstants.CIdToTeach);
        int courseId = Integer.parseInt(sc.nextLine());
        try {
            professorOperation.chooseCourse(courseId);
        } catch (RepeatException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Uses Course Id to view Students registered in that course
     */
    public void viewStudents() {
        System.out.println(StatementConstants.CIdToView);
        int courseId = Integer.parseInt(sc.nextLine());
        List<Student> studentList = null;
        try {
            studentList = professorOperation.viewStudents(courseId);
        } catch (CourseNotAccesibleException e) {
            System.out.println(e.getMessage());
        }
        if (studentList == null) {
            return;
        }
        System.out.println("The List of Students enrolled in " + courseId + " taught by you are: ");
        for (Student student : studentList) {
            System.out.println("Student Id: " + student.getStudentId());
            System.out.println("Name: " + student.getName());
            System.out.println("Branch: " + student.getBranch());
            System.out.println("Semester: " + student.getSemester());
            System.out.println("Gender: " + student.getGender() + "\n");
        }
    }

    /**
     * Uses course id and student id to assign grade to the student enrolled in that course
     */
    private void assignGrades() {
        System.out.println(StatementConstants.CId);
        int courseId = Integer.parseInt(sc.next());
        System.out.println(StatementConstants.SId);
        int studentId = Integer.parseInt(sc.next());
        System.out.println(StatementConstants.ScoreForCourse);
        char gradeLetter = sc.next().charAt(0);
        Grade newGrade = new Grade(studentId, courseId, gradeLetter);
        try {
            professorOperation.assignGrades(newGrade);
        } catch (CourseNotAccesibleException | StudentNotFoundException | RepeatException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Sends notification to a particular user
     */
    public void showNotifications() {
        String username = professorOperation.getProfessorUsername();
        List<Notification> notificationList = userOperation.showNotifications(username);
        int count = 0;
        for (Notification notification : notificationList) {
            count++;
            System.out.println(count + ". -> " + notification.getNotificationText());
        }
    }

}