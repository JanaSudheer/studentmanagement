package com.sudheer.projects.application;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.sudheer.projects.dto.Student;

public class ReportGenerator {
    public static void generateReport(List<Student> students, String filePath) {
        if (students == null || students.isEmpty()) {
            System.out.println("No students to generate a report.");
            return;
        }

        try (FileWriter writer = new FileWriter(filePath)) {
            for (Student student : students) {
                writer.write("ID: " + student.getId() + ", Name: " + student.getName() +
                        ", Email: " + student.getEmail() + ", Phone: " + student.getPhone() +
                        ", Course: " + student.getCourse() + "\n");
            }
            System.out.println("Report generated successfully at: " + filePath);
        } catch (IOException e) {
            System.err.println("Error generating report: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
