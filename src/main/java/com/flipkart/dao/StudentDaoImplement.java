package com.flipkart.dao;

import com.flipkart.bean.Student;
import com.flipkart.constant.SQLQueries;
import com.flipkart.util.DBUtils;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author JEDI05
 */

/**
 * StudentDaoImplement class that implements StudentDoaInterface
 */
public class StudentDaoImplement implements StudentDaoInterface {
    private static Logger logger = Logger.getLogger(StudentDaoImplement.class);
    private static StudentDaoImplement singleton;
    Connection conn = DBUtils.getConnection();


    private StudentDaoImplement(){
        // pass
    }


    public static StudentDaoImplement getInstance(){
        if(singleton == null){
            singleton = new StudentDaoImplement();
        }
        return singleton;
    }

    /**
     * Extracts student object of Student class
     *
     * @param username used for extracting Student details
     * @return student object containing Student details
     */
    public Student getStudent(String username) {
        //String SQL = SQLQueriesConstants.GET_STUDENT; // this statement we will get from the const package
        PreparedStatement stmt = null;

        Student student = null;

        try {
            // studentId, username, name, branch, gender, semester
            stmt = conn.prepareStatement(SQLQueries.GET_STUDENT); // red lines due to unhandled exception
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();


            while (rs.next()) {
                student = new Student(rs.getString(3), rs.getInt(1), rs.getString(5), rs.getString(4), rs.getInt(6));
            }
        } catch (SQLException se) {
            System.out.println(se.getMessage());
        }

        return student;
    }


    /**
     * Extracts student object of Student class
     *
     * @param studentId unique identifier of student used for obtaining student username
     * @return student username
     */
    public String getStudentUsername(int studentId) {

        PreparedStatement stmt = null;
        String username = null;
        try {
            stmt = conn.prepareStatement("SELECT username from studentdetails where studentId = ?");
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                username = rs.getString(1);
            }

        } catch (SQLException se) {
            System.out.println(se.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return username;
    }

    public void notifyPayment(String username, int totalFees) {
        PreparedStatement stmtNotify = null;
        try {
            stmtNotify = conn.prepareStatement(SQLQueries.ADD_NOTIFY);
            stmtNotify.setString(1, username);
            String notifyText = "Fee Confirmation of INR ";
            notifyText = notifyText + totalFees;
            stmtNotify.setString(2, notifyText);
            int rows = stmtNotify.executeUpdate();
            System.out.println(notifyText);
        } catch (SQLException se) {
            System.out.println(se.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


}