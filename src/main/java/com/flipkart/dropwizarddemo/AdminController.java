package com.flipkart.dropwizarddemo;

import com.flipkart.bean.Course;
import com.flipkart.bean.Notification;
import com.flipkart.bean.Professor;
import com.flipkart.bean.Student;
import com.flipkart.exception.LimitExceededException;
import com.flipkart.exception.RepeatException;
import com.flipkart.service.AdminInterface;
import com.flipkart.service.AdminOperation;
import com.flipkart.service.UserOperation;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Scanner;

/**
 * @author JEDI05
 */

/**
 * Admin Controller class
 */
@Path("/admincontroller")
public class AdminController {
    private static Logger logger = Logger.getLogger(AdminController.class);
    AdminInterface adminOperation = new AdminOperation(1);
    Scanner sc = new Scanner(System.in);
    UserOperation userOperation = new UserOperation();

    /**
     * Get method used to view courses present in catalog
     *
     * @return response object with the status and json string with course id and course name
     */
    @GET
    @Path("/coursecatalog")
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewCourses() {
        List<Course> courseList = adminOperation.viewCoursesInCatalog();
        JSONArray jsonArray = new JSONArray();
        for (Course course : courseList) {
            JSONObject courseJson = new JSONObject();
            courseJson.put("Course Id", course.getCourseId());
            courseJson.put("Course Name", course.getName());
            jsonArray.add(courseJson);
        }
        return Response.status(200).entity(jsonArray.toJSONString()).build();
    }

    /**
     * Post method that gathers required information to add new courses in the course catalog
     *
     * @return response object with the status and json string with message
     */
    @POST
    @Path("/addcourse")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCourse(Course course) {
        String res = "";
        try {
            adminOperation.addCourseInCatalog(course);
            res = "Successfully added: " + course;
        } catch (RepeatException e) {
            res = e.getMessage();
        }
        return Response.status(201).entity(res).build();
    }

    /**
     * Delete method that uses course id to delete a particular course
     *
     * @return response object with the status and json string with message
     */
    @DELETE
    @Path("/deletecourse/{courseId}")
    public Response deleteCourse(@PathParam("courseId") int courseId) {
        String result = adminOperation.dropCourseInCatalog(courseId);
        return Response.status(200).entity(result).build();
    }

    /**
     * Post method which uses professor details for adding professor
     *
     * @param professorId unique identifier of professor used for adding professor
     * @param name professor name
     * @param gender professor gender
     * @param username professor username
     * @param password professor passowrd
     * @return response object with the status and json string with message
     */
    @POST
    @Path("/addprofessor")
    public Response addProfessor(@FormParam("professorId") int professorId, @FormParam("name") String name, @FormParam("gender") String gender, @FormParam("username") String username, @FormParam("password") String password) {
        String res = "";
        Professor professor = new Professor(name, professorId, gender);
        res=adminOperation.addProfessor(professor, username, password);
        return Response.status(201).entity(res).build();
    }

    /**
     * Delete method that uses Professor username to drop him/her
     *
     * @param username used for dropping professor
     * @return response object with the status and json string with message
     */
    @DELETE
    @Path("/dropprofessor/{username}")
    public Response dropProfessor(@PathParam("username") String username) {
        String result = adminOperation.dropProfessor(username);
        return Response.status(200).entity(result).build();
    }

    /**
     * Get method that displays student details of all the students registered in a particular course
     *
     * @param courseId unique identifier of course for viewing students in that course
     * @return response object with the status and json string with student id, name, branch, semester and gender
     */
    @GET
    @Path("/students/{courseId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewStudentsInCourse(@PathParam("courseId") int courseId) {
        List<Student> studentsInCourse = adminOperation.viewStudentInCourse(courseId);
        JSONArray jsonArray = new JSONArray();
        for (Student student : studentsInCourse) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Student Id", student.getStudentId());
            jsonObject.put("Name", student.getName());
            jsonObject.put("Branch", student.getBranch());
            jsonObject.put("Semester", student.getSemester());
            jsonObject.put("Gender", student.getGender());
            jsonArray.add(jsonObject);
        }
        return Response.status(200).entity(jsonArray).build();
    }

    /**
     * Post method that uses course Id and student Id to assign a particular course to a student
     *
     * @param studentId unique identifier of student
     * @param courseId unique identifier of course
     * @return response object with the status and json string with message
     */
    @POST
    @Path("/assigncourse")
    public Response assignCourseToStudent(@FormParam("studentId") int studentId, @FormParam("courseId") int courseId) {
        String res = "";
        System.out.println(studentId);
        System.out.println(courseId);
        try {
            adminOperation.AssignStudentToCourse(studentId, courseId);
            res = "Assigned " + studentId + " to " + courseId;
        } catch (LimitExceededException e) {
            res = e.getMessage();
        }
        return Response.status(201).entity(res).build();
    }

    /**
     * Get method that sends notification to a particular user
     *
     * @param username of user used for sending notifications
     * @return response object with the status and json string with notification id and content
     */
    @GET
    @Path("/notifications")
    @Produces(MediaType.APPLICATION_JSON)
    public Response showNotifications(@QueryParam("username") String username) {
        System.out.println(username);
        List<Notification> notificationList = userOperation.showNotifications(username);
        int count = 0;
        JSONArray jsonArray = new JSONArray();
        for (Notification notification : notificationList) {
            count++;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Notification ID", count);
            jsonObject.put("Content", notification.getNotificationText());
            jsonArray.add(jsonObject);
        }
        return Response.status(200).entity(jsonArray.toJSONString()).build();
    }

}
