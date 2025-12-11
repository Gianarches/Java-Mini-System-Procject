package javaapplication1.Java;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * GUI layer only — uses StudentController and AttendanceManager.
 * Layout and behavior kept the same as your original GUI.
 */
public class MainGUI extends JFrame {

    private final StudentController controller = new StudentController();

    private final JTextField nameField = new JTextField();
    private final JTextField sectionField = new JTextField();
    private final JTextField scoreField = new JTextField();
    private final JComboBox<String> subjectBox;

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
    private final JButton saveBtn = new JButton("Save Records");
    private final JButton exitBtn = new JButton("Exit");

    public MainGUI() {
        this.subjectBox = new JComboBox<>(Student.subjects);
        setTitle("Student Attendance & Grade System");
        setSize(950, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("STUDENT ATTENDANCE AND GRADE SYSTEM", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setOpaque(true);
        title.setBackground(new Color(0, 102, 204));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(title, BorderLayout.NORTH);

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

        model = new DefaultTableModel(new String[]{
                "Name", "Section", "Present", "Late", "Absent",
                "Attendance %",
                "Math Avg", "Science Avg", "English Avg",
                "Overall Average"
        }, 0);

        table = new JTable(model);
        tablePane = new JScrollPane(table);
        tablePane.setVisible(false);
        add(tablePane, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new GridLayout(2, 5, 10, 10));
        buttons.setBorder(BorderFactory.createEmptyBorder(10,20,20,20));

        buttons.add(addBtn);
        buttons.add(presentBtn);
        buttons.add(lateBtn);
        buttons.add(absentBtn);
        buttons.add(scoreBtn);
        buttons.add(viewBtn);
        buttons.add(saveBtn);
        buttons.add(exitBtn);

        add(buttons, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> addStudent());
        presentBtn.addActionListener(e -> recordAttendance(1));
        lateBtn.addActionListener(e -> recordAttendance(2));
        absentBtn.addActionListener(e -> recordAttendance(3));
        scoreBtn.addActionListener(e -> addScore());
        viewBtn.addActionListener(e -> toggleTable());
        saveBtn.addActionListener(e -> saveToFile());
        exitBtn.addActionListener(e -> exitApp());

        setVisible(true);
    }

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

        if (!name.matches("[a-zA-Z ]+")) {
            JOptionPane.showMessageDialog(this, "Name must contain letters only.");
            return;
        }

        if (!sectionStr.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Section must contain numbers only.");
            return;
        }

        int section = Integer.parseInt(sectionStr);

        if (section < 1 || section > 6) {
            JOptionPane.showMessageDialog(this, "Section must be between 1 and 6 only.");
            return;
        }

        if (!controller.addStudent(name, section)) {
            JOptionPane.showMessageDialog(this, "Student already exists!");
            return;
        }

        JOptionPane.showMessageDialog(this, "Student added!");
        clearFields();
        if (tablePane.isVisible()) refreshTable();
    }

    private void recordAttendance(int type) {
        String studentName = nameField.getText().trim();
        if (studentName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter student name.");
            return;
        }

        Student s = controller.findStudent(studentName);
        if (s == null) {
            JOptionPane.showMessageDialog(this, "Student not found!");
            return;
        }

        AttendanceManager.record(s, type);
        updateDisplay(s);
        if (tablePane.isVisible()) refreshTable();
    }

    private void addScore() {
        String studentName = nameField.getText().trim();
        if (studentName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter student name.");
            return;
        }

        Student s = controller.findStudent(studentName);
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

        double score = Double.parseDouble(scoreText);
        if (score < 0 || score > 100) {
            JOptionPane.showMessageDialog(this, "Score must be between 0 and 100.");
            return;
        }

        String subject = (String) subjectBox.getSelectedItem();
        s.addScore(subject, score);
        updateDisplay(s);
        if (tablePane.isVisible()) refreshTable();
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
        revalidate();
        repaint();
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
                    String.format("%.2f", s.getSubjectAverage("Math")),
                    String.format("%.2f", s.getSubjectAverage("Science")),
                    String.format("%.2f", s.getSubjectAverage("English")),
                    String.format("%.2f", s.getOverallAverage())
            });
        }
    }

    private void saveToFile() {
        try (java.io.BufferedWriter writer = new java.io.BufferedWriter(
                new java.io.FileWriter("students.txt")
        )) {
            writer.write("============================================\n");
            writer.write("      STUDENT CLASS RECORD\n");
            writer.write("============================================\n\n");

            for (Student s : controller.getAllStudents()) {
                writer.write("Name       : " + s.getName() + "\n");
                writer.write("Section    : " + s.getSection() + "\n");
                writer.write("Present    : " + s.getPresent() + "\n");
                writer.write("Late       : " + s.getLate() + "\n");
                writer.write("Absent     : " + s.getAbsent() + "\n");
                writer.write("Attendance : " + String.format("%.2f%%", s.getAttendanceRate()) + "\n\n");

                writer.write("Grades:\n");
                writer.write("  Math     : " + String.format("%.2f", s.getSubjectAverage("Math")) + "\n");
                writer.write("  Science  : " + String.format("%.2f", s.getSubjectAverage("Science")) + "\n");
                writer.write("  English  : " + String.format("%.2f", s.getSubjectAverage("English")) + "\n\n");

                writer.write("Overall Score Average : " + String.format("%.2f", s.getOverallAverage()) + "\n");
                writer.write("--------------------------------------------\n\n");
            }

            JOptionPane.showMessageDialog(this, "Records saved successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving file!");
        }
    }

    private void exitApp() {
        int confirm = JOptionPane.showConfirmDialog(this,"Exit program?","Confirm Exit",JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) System.exit(0);
    }

    public static void main(String[] args) {
        // Swing best-practice: create UI on Event Dispatch Thread
        SwingUtilities.invokeLater(MainGUI::new);
    }
}
