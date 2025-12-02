import java.util.ArrayList;
import java.util.Scanner;

// ✅ STUDENT CLASS
class Student {
    String name;
    String section;
    int present;
    int late;
    int absent;
    double totalScore;
    int totalExams;

    // Constructor
    Student(String name, String section) {
        this.name = name;
        this.section = section;
        this.present = 0;
        this.late = 0;
        this.absent = 0;
        this.totalScore = 0;
        this.totalExams = 0;
    }

    // Attendance percentage
    double getAttendanceRate() {
        int total = present + late + absent;
        if (total == 0) return 0;
        return (present * 100.0) / total;
    }

    // Average grade
    double getAverageGrade() {
        if (totalExams == 0) return 0;
        return totalScore / totalExams;
    }

    // Display student summary
    void display() {
        System.out.println("Name: " + name);
        System.out.println("Section: " + section);
        System.out.println("Present: " + present + " | Late: " + late + " | Absent: " + absent);
        System.out.printf("Attendance Rate: %.2f%%\n", getAttendanceRate());
        System.out.printf("Average Grade: %.2f\n", getAverageGrade());
        System.out.println("----------------------------------");
    }
}

// ✅ MAIN SYSTEM CLASS
public class StudentSystem {

    static ArrayList<Student> students = new ArrayList<>();
    static Scanner input = new Scanner(System.in);

    // ✅ MAIN MENU
    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\nSTUDENT ATTENDANCE AND GRADE SYSTEM");
            System.out.println("1. Add Student");
            System.out.println("2. Record Attendance");
            System.out.println("3. Add Quiz/Exam Score");
            System.out.println("4. View Student Summary");
            System.out.println("5. Exit");
            System.out.print("Choose: ");

            choice = input.nextInt();
            input.nextLine(); // clear buffer

            switch (choice) {
                case 1: addStudent(); 
                        break;
                case 2: recordAttendance(); 
                        break;
                case 3: addScore();
                        break;
                case 4: viewStudent(); 
                        break;
                case 5: System.out.println("Program terminated."); break;
                default: System.out.println("Invalid choice!");
            }

        } while (choice != 5);
    }

    // ✅ ADD STUDENT
    static void addStudent() {
    System.out.print("Enter name: ");
    String name = input.nextLine();

    // ✅ CHECK: Name must be letters only
    if (!name.matches("[a-zA-Z ]+")) {
        System.out.println("Invalid name! Letters only are allowed.");
        return;
    }

    System.out.print("Enter section: ");
    String section = input.nextLine();

    // ✅ CHECK: Section must be numbers only
    if (!section.matches("[0-9]+")) {
        System.out.println("Invalid section! Numbers only are allowed.");
        return;
    }

    // ✅ Check duplicate name
    for (Student s : students) {
        if (s.name.equalsIgnoreCase(name)) {
            System.out.println("Student already exists!");
            return;
        }
    }

    students.add(new Student(name, section));
    System.out.println("Student added successfully.");
}


    // ✅ RECORD ATTENDANCE
    static void recordAttendance() {
        Student s = searchStudent();
        if (s == null) return;

        System.out.println("1 - Present | 2 - Late | 3 - Absent");
        System.out.print("Choose attendance: ");
        int choice = input.nextInt();

        switch (choice) {
            case 1 -> s.present++;
            case 2 -> s.late++;
            case 3 -> s.absent++;
            default -> {
                System.out.println("Invalid attendance option.");
                return;
            }
        }
        System.out.println("Attendance recorded.");
    }

    // ✅ ADD SCORE
    static void addScore() {
        Student s = searchStudent();
        if (s == null) return;

        System.out.print("Enter score (0–100): ");
        double score = input.nextDouble();

        if (score < 0 || score > 100) {
            System.out.println("Score must be between 0 and 100.");
            return;
        }

        s.totalScore += score;
        s.totalExams++;

        System.out.println("Score recorded successfully.");
    }

    // ✅ VIEW SUMMARY
    static void viewStudent() {
        Student s = searchStudent();
        if (s != null) {
            s.display();
        }
    }

    // ✅ SEARCH STUDENT
    static Student searchStudent() {
    System.out.print("Enter student name: ");
    String name = input.nextLine();

    // ✅ CHECK: Letters only
    if (!name.matches("[a-zA-Z ]+")) {
        System.out.println("Invalid name!");
        return null;
    }

    for (Student s : students) {
        if (s.name.equalsIgnoreCase(name)) {
            return s;
        }
    }

    System.out.println("Student not found.");
    return null;
    }
}
