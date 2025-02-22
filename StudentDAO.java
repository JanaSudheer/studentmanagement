package com.tap.projects.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.sudheer.projects.dto.Student;

public interface StudentDAO {
    void addStudent(Student student) throws SQLException;
    void updateStudent(Student student) throws SQLException;
    void deleteStudent(int id) throws SQLException;
    List<Student> getAllStudents() throws SQLException;
}
