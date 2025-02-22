package com.sudheer.projects.application;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.sudheer.projects.daoimpl.StudentDAOImpl;
import com.sudheer.projects.dto.Student;
import com.tap.projects.interfaces.StudentDAO;

public class StudentManagementApp extends JFrame {
    private StudentDAO studentDAO;

    public StudentManagementApp() {
        studentDAO = new StudentDAOImpl();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Student Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 1));

        JButton addButton = new JButton("Add Student");
        JButton updateButton = new JButton("Update Student");
        JButton deleteButton = new JButton("Delete Student");
        JButton viewButton = new JButton("View Students");
        JButton reportButton = new JButton("Generate Report");

        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(viewButton);
        panel.add(reportButton);

        add(panel);

        // Add action listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStudent();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteStudent();
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewStudents();
            }
        });

        reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateReport();
            }
        });
    }

    private void addStudent() {
        // Create a dialog to input student details
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField courseField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(new JLabel("Course:"));
        panel.add(courseField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Student", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String course = courseField.getText();

            Student student = new Student(0, name, email, phone, course);
            try {
                studentDAO.addStudent(student);
                JOptionPane.showMessageDialog(this, "Student added successfully!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error adding student: " + ex.getMessage());
            }
        }
    }

    private void updateStudent() {
        // Create a dialog to input student ID and updated details
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField courseField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(new JLabel("Course:"));
        panel.add(courseField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Update Student", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String course = courseField.getText();

            Student student = new Student(id, name, email, phone, course);
            try {
                studentDAO.updateStudent(student);
                JOptionPane.showMessageDialog(this, "Student updated successfully!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error updating student: " + ex.getMessage());
            }
        }
    }

    private void deleteStudent() {
        // Create a dialog to input student ID
        JTextField idField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(new JLabel("ID:"));
        panel.add(idField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Delete Student", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            int id = Integer.parseInt(idField.getText());
            try {
                studentDAO.deleteStudent(id);
                JOptionPane.showMessageDialog(this, "Student deleted successfully!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error deleting student: " + ex.getMessage());
            }
        }
    }

    private void viewStudents() {
        try {
            List<Student> students = studentDAO.getAllStudents();
            StringBuilder sb = new StringBuilder();
            for (Student student : students) {
                sb.append("ID: ").append(student.getId()).append(", Name: ").append(student.getName())
                  .append(", Email: ").append(student.getEmail()).append(", Phone: ").append(student.getPhone())
                  .append(", Course: ").append(student.getCourse()).append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString(), "Student List", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching students: " + ex.getMessage());
        }
    }

    private void generateReport() {
        try {
            List<Student> students = studentDAO.getAllStudents();
            ReportGenerator.generateReport(students, "report.txt");
            JOptionPane.showMessageDialog(this, "Report generated successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error generating report: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StudentManagementApp().setVisible(true);
            }
        });
    }
}