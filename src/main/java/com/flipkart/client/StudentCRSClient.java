package com.flipkart.client;

import com.flipkart.bean.Course;
import com.flipkart.bean.Grade;
import com.flipkart.bean.Notification;
import com.flipkart.bean.RequestedCourse;
import com.flipkart.constant.StatementConstants;
import com.flipkart.exception.CourseNotFoundException;
import com.flipkart.exception.LimitExceededException;
import com.flipkart.exception.RepeatException;
import com.flipkart.service.StudentInterface;
import com.flipkart.service.StudentOperation;
import com.flipkart.service.UserOperation;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Scanner;

/**
 * @author JEDI05
 */

/**
 * Class of Student Client that gives different options to student
 */
public class StudentCRSClient {

    private static Logger logger = Logger.getLogger(StudentCRSClient.class);
    private int studentId;
    StudentInterface studentOperation;
    Scanner sc = new Scanner(System.in);
    UserOperation userOperation = new UserOperation();

    public StudentCRSClient(int studentId) {
        this.studentId = studentId;
        studentOperation = new StudentOperation(this.studentId);
    }

    /**
     * Display menus for the student
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
                    chooseCourse();
                    break;
                case 3:
                    dropCourse();
                    break;
                case 4:
                    payFees();
                    break;
                case 5:
                    viewReportCard();
                    break;
                case 6:
                    viewRequestedCourses();
                    break;
                case 7:
                    viewAssignedCourses();
                    break;
                case 8:
                    showNotifications();
                case 9:
                    return;
            }
        } while (true);
    }

    /**
     * Displays available choices to the student
     */
    public void showChoices() {
        System.out.println(StatementConstants.Choice);
        System.out.println(StatementConstants.ViewCourses);
        System.out.println(StatementConstants.RequestCourse);
        System.out.println(StatementConstants.DropACourse);
        System.out.println(StatementConstants.PayFees);
        System.out.println(StatementConstants.ViewReportCard);
        System.out.println(StatementConstants.ViewRequestedCourses);
        System.out.println(StatementConstants.ViewAssignedCourses);
        System.out.println("8. " + StatementConstants.showNotification);
        System.out.println("9. Logout");
    }

    /**
     * Displays courseId, name and department of all the courses from catalog
     */
    public void viewCourses() {
        List<Course> courses = studentOperation.viewCourses();
        for (Course course : courses) {
            System.out.println(course.getName());
            System.out.println(course.getCourseId());
            System.out.println(course.getDepartment());
            System.out.println(" ");
            // TODO: Need to manage this error
            // System.out.println(course.getProfessorId().getName());
        }
    }

    /**
     * Uses course Id to select a course
     */
    public void chooseCourse() {
        System.out.println(StatementConstants.EnterCId);
        int courseId = Integer.parseInt(sc.nextLine());
        System.out.println(StatementConstants.IsPrimary);
        int isPrimaryInt = Integer.parseInt(sc.nextLine());
        boolean isPrimary = isPrimaryInt == 1;
        try {
            studentOperation.chooseCourse(courseId, isPrimary);
        } catch (RepeatException | LimitExceededException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Uses course Id to drop a course
     */
    public void dropCourse() {
        System.out.println(StatementConstants.DropCId);
        int courseId = Integer.parseInt(sc.nextLine());
        try {
            studentOperation.dropCourse(courseId);
        } catch (CourseNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Allows different Payment Methods for fees payment
     */
    public void payFees() {
        System.out.println(StatementConstants.GetFees);
        sc.nextLine();
        int totalFees = studentOperation.getFees();
        System.out.println(String.format("Your total amount is %d", totalFees));
        System.out.println(StatementConstants.ChoosePaymentMethod);
        System.out.println(StatementConstants.Methods);
        sc.nextLine();
        System.out.println(StatementConstants.Pay);
        sc.nextLine();
        System.out.println(StatementConstants.PaySuccess);
        studentOperation.notifyPayment(totalFees);
    }

    /**
     * Displays course Id and corresponding grade of a particular student
     */
    public void viewReportCard() {
        List<Grade> allGradeOfStudents = studentOperation.viewReportCard();

        for (Grade grade : allGradeOfStudents) {
            System.out.println("courseId: " + grade.getCourseId());
            System.out.println("grade:" + grade.getGradeLetter());
        }

    }

    /**
     * Displays all the requested courses of a student
     */
    public void viewRequestedCourses() {
        List<RequestedCourse> allRequestedCourse = studentOperation.viewRequestedCourses();
        for (RequestedCourse requestedCourse : allRequestedCourse) {
            System.out.println(requestedCourse.getCourseId());
            System.out.println(requestedCourse.isPrimary());
            System.out.println(" ");
        }
    }

    /**
     * Displays all the assigned courses of a student
     */
    public void viewAssignedCourses() {
        List<Course> allAssignedCourse = studentOperation.viewAssignedCourses();

        for (Course course : allAssignedCourse) {
            System.out.println(course.getName());
            System.out.println(course.getDepartment());
            System.out.println(course.getCourseId());
            System.out.println(" ");
        }
    }

    /**
     * Sends notification to a particular user
     */
    public void showNotifications() {
        String username = studentOperation.getStudentUsername();
        List<Notification> notificationList = userOperation.showNotifications(username);
        int count = 0;
        for (Notification notification : notificationList) {
            count++;
            System.out.println(count + ". -> " + notification.getNotificationText());
        }
    }

}
