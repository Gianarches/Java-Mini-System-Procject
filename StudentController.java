package javaapplication1.Java;

import java.util.ArrayList;
import java.util.List;


public class StudentController {

    private final List<Student> students = new ArrayList<>();

    
    public boolean addStudent(String name, int section) {
        if (name == null || name.trim().isEmpty()) return false;
        if (section < 1 || section > 6) return false;

        // avoid duplicate names (case-insensitive)
        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(name.trim())) return false;
        }

        students.add(new Student(name.trim(), section));
        return true;
    }

    
    public Student findStudent(String name) {
        if (name == null) return null;
        String q = name.trim();
        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(q)) return s;
        }
        return null;
    }

  
    public List<Student> getAllStudents() {
        return students;
    }
}
