package javaapplication1.Java;

import java.util.ArrayList;
import java.util.List;

public class StudentController {

    // Stores all registered students
    private final List<Student> students = new ArrayList<>();

    // Adds a new student if valid and not duplicate
    public boolean addStudent(String name, int section) {
        if (name == null || name.trim().isEmpty()) return false;
        if (section < 1 || section > 6) return false; // Valid sections are 1–6 only

        // Avoid duplicate names
        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(name.trim()))
                return false;
        }

        // Add student to list
        students.add(new Student(name.trim(), section));
        return true;
    }

    // Finds a student by name
    public Student findStudent(String name) {
        if (name == null) return null;

        String q = name.trim();
        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(q)) return s;
        }

        return null; // Not found
    }

    // Returns list of all students (used in GUI table)
    public List<Student> getAllStudents() {
        return students;
    }
}
