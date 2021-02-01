package com.flipkart.dao;

import com.flipkart.bean.Student;

/**
 * @author JEDI05
 */

/**
 * StudentDaoInterface class
 */
public interface StudentDaoInterface {

    /**
     * Extracts student object of Student class
     *
     * @param username used for extracting Student details
     * @return student object containing Student details
     */
    public Student getStudent(String username);
}
