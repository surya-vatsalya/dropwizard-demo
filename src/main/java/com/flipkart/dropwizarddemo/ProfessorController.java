package com.flipkart.dropwizarddemo;

import com.flipkart.bean.Course;
import com.flipkart.bean.Grade;
import com.flipkart.bean.Student;
import com.flipkart.exception.CourseNotAccesibleException;
import com.flipkart.exception.RepeatException;
import com.flipkart.exception.StudentNotFoundException;
import com.flipkart.service.ProfessorInterface;
import com.flipkart.service.ProfessorOperation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author JEDI05
 */

/**
 * Professor Controller class
 */
@Path("/professorcontroller")
public class ProfessorController {

    /**
     * Get method used to view courses present in catalog
     *
     * @return json string with course id and course name
     */
    @GET
    @Path("/coursecatalog/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String viewCourses(@PathParam("id") int id) {
        ProfessorInterface professorOperation = new ProfessorOperation(id);
        List<Course> courseList = professorOperation.viewCourses();
        JSONArray jsonArray = new JSONArray();
        for (Course course : courseList) {
            JSONObject courseJson = new JSONObject();
            courseJson.put("Course Id", course.getCourseId());
            courseJson.put("Course Name", course.getName());
            jsonArray.add(courseJson);
        }

        return jsonArray.toJSONString();
    }

    /**
     * Get method used to view assigned courses to professor
     *
     * @return json string with course id and course name
     */
    @GET
    @Path("viewassignedcourses/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String viewAssignedCourses(@PathParam("id") int id) {
        ProfessorInterface professorOperation = new ProfessorOperation(id);
        List<Course> assignedCourseList = professorOperation.viewAssignedCourses();
        JSONArray jsonArray = new JSONArray();
        for (Course course : assignedCourseList) {
            JSONObject courseJson = new JSONObject();
            courseJson.put("Course Id", course.getCourseId());
            courseJson.put("Course Name", course.getName());
            jsonArray.add(courseJson);
        }
        return jsonArray.toJSONString();
    }

    /**
     * Get method used to view students enrolled in particular course
     *
     * @return json string with student id, name, gender, semester and branch
     */
    @GET
    @Path("viewstudents/{profid}/{courseid}")
    @Produces(MediaType.APPLICATION_JSON)
    public String viewStudents(@PathParam("profid") int professorId, @PathParam("courseid") int courseId)
    {
        ProfessorInterface professorOperation = new ProfessorOperation(professorId);
        List<Student> studentsInCourse = null;
        try {
            studentsInCourse = professorOperation.viewStudents(courseId);
        } catch (CourseNotAccesibleException e) {
            return "Course Not Accessible";
        }
        JSONArray jsonArray = new JSONArray();
        for(Student student : studentsInCourse)
        {
            JSONObject studentJson = new JSONObject();
            studentJson.put("Student Id", student.getStudentId());
            studentJson.put("Name", student.getName());
            studentJson.put("Gender", student.getGender());
            studentJson.put("Semester", student.getSemester());
            studentJson.put("Branch", student.getBranch());
            jsonArray.add(studentJson);
        }
        return jsonArray.toJSONString();
    }

    /**
     * Post method used for selecting courses
     *
     * @param courseId unique identifier of course
     * @param profId unique identifier of professor
     * @return response object with status and json string with message
     * @throws RepeatException if course is already assigned to a Professor
     */
    @POST
    @Path("/choose-course/{courseId}/{profId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response chooseCourses(@PathParam("courseId") int courseId,@PathParam("profId") int profId) throws RepeatException {
        String result = "Saved "  + courseId + " to "+ profId;
        ProfessorInterface professorOperation = new ProfessorOperation(profId);
        professorOperation.chooseCourse(courseId);
        return Response.status(201).entity(result).build();
    }

    /**
     * Post method used for assigning grades to student in particular course
     *
     * @param courseId unique identifier of course
     * @param profId unique identifier of professor
     * @param studentId unique identifier of student
     * @param score score of student in a course
     * @return response object with the status and json string with message
     * @throws RepeatException if course is already graded
     * @throws CourseNotAccesibleException if course is not accessible
     * @throws StudentNotFoundException if student is not enrolled in particular course
     */
    @POST
    @Path("/assign-grade/{courseId}/{profId}/{studentId}/{score}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response assignGrades(@PathParam("courseId") int courseId,@PathParam("profId") int profId,@PathParam("studentId") int studentId,@PathParam("score") String score) throws RepeatException,CourseNotAccesibleException, StudentNotFoundException {
        String result = "Grade added for" + courseId + "for "+ studentId;
        ProfessorInterface professorOperation = new ProfessorOperation(profId);
        char gradeLetter = score.charAt(0);
        Grade newGrade = new Grade(studentId, courseId, gradeLetter);
        professorOperation.assignGrades(newGrade);
        return Response.status(201).entity(result).build();
    }

}