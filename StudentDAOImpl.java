package com.sudheer.projects.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sudheer.projects.dto.Student;
import com.sudheer.projects.utility.DatabaseConnection;
import com.tap.projects.interfaces.StudentDAO;

public class StudentDAOImpl implements StudentDAO {
    @Override
    public void addStudent(Student student) throws SQLException {
        String query = "INSERT INTO students (name, email, phone, course) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getEmail());
            stmt.setString(3, student.getPhone());
            stmt.setString(4, student.getCourse());
            stmt.executeUpdate();
        }
    }

    @Override
    public void updateStudent(Student student) throws SQLException {
        String query = "UPDATE students SET name=?, email=?, phone=?, course=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getEmail());
            stmt.setString(3, student.getPhone());
            stmt.setString(4, student.getCourse());
            stmt.setInt(5, student.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteStudent(int id) throws SQLException {
        String query = "DELETE FROM students WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Student student = new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("course")
                );
                students.add(student);
            }
        }
        return students;
    }
}
