package javaapplication1.Java;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainGUI extends JFrame {

    private final StudentController controller = new StudentController();

    private final JTextField nameField = new JTextField();
    private final JTextField sectionField = new JTextField();
    private final JTextField scoreField = new JTextField();
    private final JComboBox<String> subjectBox = new JComboBox<>(Student.SUBJECTS);

    private final JLabel attendanceLabel = new JLabel("0%");
    private final JLabel averageLabel = new JLabel("0");

    private JTable table;
    private DefaultTableModel model;
    private JScrollPane tablePane;

    private final JButton addBtn = new JButton("Add Student");
    private final JButton presentBtn = new JButton("Present");
    private final JButton lateBtn = new JButton("Late");
    private final JButton absentBtn = new JButton("Absent");
    private final JButton scoreBtn = new JButton("Add Score");
    private final JButton viewBtn = new JButton("View All Students");
    private final JButton exitBtn = new JButton("Exit");

    public MainGUI() {

        setTitle("Student Attendance & Grade System");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== TITLE =====
        JLabel title = new JLabel("STUDENT ATTENDANCE AND GRADE SYSTEM", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setOpaque(true);
        title.setBackground(new Color(0, 102, 204));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(title, BorderLayout.NORTH);

        // ===== INPUT PANEL =====
        JPanel left = new JPanel(new GridLayout(8, 2, 10, 10));
        left.setBorder(BorderFactory.createEmptyBorder(20,20,20,10));

        left.add(new JLabel("Student Name:"));
        left.add(nameField);

        left.add(new JLabel("Section:"));
        left.add(sectionField);

        left.add(new JLabel("Subject:"));
        left.add(subjectBox);

        left.add(new JLabel("Score:"));
        left.add(scoreField);

        left.add(new JLabel("Attendance Rate:"));
        left.add(attendanceLabel);

        left.add(new JLabel("Average Grade:"));
        left.add(averageLabel);

        add(left, BorderLayout.WEST);

        // ===== TABLE =====
        model = new DefaultTableModel(new String[]{
                "Name", "Section", "Present", "Late", "Absent",
                "Attendance %", "Overall Average"
        }, 0);

        table = new JTable(model);
        tablePane = new JScrollPane(table);
        tablePane.setVisible(false);
        add(tablePane, BorderLayout.CENTER);

        // ===== BUTTON PANEL =====
        JPanel buttons = new JPanel(new GridLayout(2, 4, 10, 10));
        buttons.setBorder(BorderFactory.createEmptyBorder(10,20,20,20));

        buttons.add(addBtn);
        buttons.add(presentBtn);
        buttons.add(lateBtn);
        buttons.add(absentBtn);
        buttons.add(scoreBtn);
        buttons.add(viewBtn);
        buttons.add(exitBtn);

        add(buttons, BorderLayout.SOUTH);

        // ===== EVENTS =====
        addBtn.addActionListener(e -> addStudent());
        presentBtn.addActionListener(e -> recordAttendance(1));
        lateBtn.addActionListener(e -> recordAttendance(2));
        absentBtn.addActionListener(e -> recordAttendance(3));
        scoreBtn.addActionListener(e -> addScore());
        viewBtn.addActionListener(e -> toggleTable());
        exitBtn.addActionListener(e -> exitApp());

        setVisible(true);
    }

    // ================= LOGIC =================

    private void addStudent() {
        String name = nameField.getText().trim();
        String sectionStr = sectionField.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter name.");
            return;
        }

        if (sectionStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter section.");
            return;
        }

        boolean nameIsLetters = name.matches("[a-zA-Z ]+");
        boolean sectionIsNumbers = sectionStr.matches("\\d+");

        // Check for swapped input
        if (!nameIsLetters && sectionIsNumbers) {
            JOptionPane.showMessageDialog(this, "Name contains invalid characters. Please enter letters only.");
            return;
        }

        if (nameIsLetters && !sectionIsNumbers) {
            JOptionPane.showMessageDialog(this, "Section contains invalid characters. Please enter numbers only.");
            return;
        }

        if (!nameIsLetters && !sectionIsNumbers) {
            JOptionPane.showMessageDialog(this, "Both Name and Section are invalid. Check your input.");
            return;
        }

        
        if (!controller.addStudent(name, sectionStr)) {
            JOptionPane.showMessageDialog(this, "Student already exists or invalid input!");
            return;
        }

        JOptionPane.showMessageDialog(this, "Student added!");
        clearFields();
        if (tablePane.isVisible()) refreshTable();
    }

    private void recordAttendance(int type) {
        Student s = controller.findStudent(nameField.getText().trim());
        if (s == null) {
            JOptionPane.showMessageDialog(this, "Student not found!");
            return;
        }

        AttendanceManager.record(s, type);
        updateDisplay(s);
        if (tablePane.isVisible()) refreshTable();
    }

    private void addScore() {
        Student s = controller.findStudent(nameField.getText().trim());
        if (s == null) {
            JOptionPane.showMessageDialog(this, "Student not found!");
            return;
        }

        String scoreText = scoreField.getText().trim();
        if (scoreText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a score.");
            return;
        }

        if (!scoreText.matches("\\d{1,3}(\\.\\d+)?")) {
            JOptionPane.showMessageDialog(this, "Numbers Only!");
            return;
        }

        try {
            double score = Double.parseDouble(scoreText);
            if (score < 0 || score > 100) {
                JOptionPane.showMessageDialog(this, "Score must be between 0 and 100.");
                return;
            }
            String subject = (String) subjectBox.getSelectedItem();
            s.addScore(subject, score);
            updateDisplay(s);
            if (tablePane.isVisible()) refreshTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid score!");
        }
    }

    private void updateDisplay(Student s) {
        attendanceLabel.setText(String.format("%.2f%%", s.getAttendanceRate()));
        averageLabel.setText(String.format("%.2f", s.getOverallAverage()));
    }

    private void clearFields() {
        nameField.setText("");
        sectionField.setText("");
        scoreField.setText("");
    }

    private void toggleTable() {
        if (!tablePane.isVisible()) {
            refreshTable();
            tablePane.setVisible(true);
            viewBtn.setText("Hide Class Record");
        } else {
            tablePane.setVisible(false);
            viewBtn.setText("View All Students");
        }
        this.revalidate();
        this.repaint();
    }

    private void refreshTable() {
        model.setRowCount(0);
        for (Student s : controller.getAllStudents()) {
            model.addRow(new Object[]{
                    s.getName(),
                    s.getSection(),
                    s.getPresent(),
                    s.getLate(),
                    s.getAbsent(),
                    String.format("%.2f%%", s.getAttendanceRate()),
                    String.format("%.2f", s.getOverallAverage())
            });
        }
    }

    private void exitApp() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Exit program?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) System.exit(0);
    }

    // ================= STUDENT CONTROLLER =================

    private static class StudentController {

        private java.util.List<Student> students = new java.util.ArrayList<>();

        public boolean addStudent(String name, String sectionStr) {
            int section;

            try {
                section = Integer.parseInt(sectionStr);
            } catch (NumberFormatException e) {
                return false;
            }

            for (Student s : students) {
                if (s.getName().equalsIgnoreCase(name)) {
                    return false;
                }
            }

            students.add(new Student(name, section));
            return true;
        }

        public Student findStudent(String name) {
            for (Student s : students) {
                if (s.getName().equalsIgnoreCase(name)) {
                    return s;
                }
            }
            return null;
        }

        public java.util.List<Student> getAllStudents() {
            return students;
        }
    }

    public static void main(String[] args) {
        new MainGUI();
    }
}
