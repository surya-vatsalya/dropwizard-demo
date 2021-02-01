package com.flipkart.dao;

import com.flipkart.bean.Professor;
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
 * ProfessorDaoImplement class that implements ProfessorDoaInterface
 */
public class ProfessorDaoImplement implements ProfessorDaoInterface {


    private static Logger logger = Logger.getLogger(ProfessorDaoImplement.class);

    // set up the DB connection
    Connection conn = DBUtils.getConnection();


    /**
     * Extracts professor object of Professor class
     *
     * @param username unique identifier of professor for obtaining professor object
     * @return professor object
     */
    @Override
    public Professor getProfessor(String username) {

        PreparedStatement stmt = null;

        Professor professor = null;

        try {
            //professorId, name, username, gender
            stmt = conn.prepareStatement(SQLQueries.GET_PROFESSOR);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                professor = new Professor(rs.getString(2), rs.getInt(1), rs.getString(4));
            }

        } catch (SQLException se) {
            System.out.println(se.getMessage());
        }

        return professor;
    }

    /**
     * Extracts professor object of Professor class
     *
     * @param professorId unique identifier of professor used for obtaining professor username
     * @return professor username
     */
    public String getProfessorUsername(int professorId) {
        PreparedStatement stmt = null;
        String username = null;
        try {
            stmt = conn.prepareStatement("SELECT username from professordetails where professorId = ?");
            stmt.setInt(1, professorId);
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


}
