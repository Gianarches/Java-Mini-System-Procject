package javaapplication1.Java;

import java.util.HashMap;
import java.util.Map;

public class Student {
    private String name;
    private int section;
    private int present;
    private int late;
    private int absent;

    // Store scores per subject
    private final Map<String, Double> totalScores = new HashMap<>();
    private final Map<String, Integer> totalExams = new HashMap<>();

    public static final String[] SUBJECTS = {"Math", "Science", "English"};

    public Student(String name, int section) {
        this.name = name;
        this.section = section;
        this.present = 0;
        this.late = 0;
        this.absent = 0;

        for (String subject : SUBJECTS) {
            totalScores.put(subject, 0.0);
            totalExams.put(subject, 0);
        }
    }

    public String getName() { return name; }
    public int getSection() { return section; }
    public int getPresent() { return present; }
    public int getLate() { return late; }
    public int getAbsent() { return absent; }

    public double getAttendanceRate() {
        int total = present + late + absent;
        if (total == 0) return 0;
        return (present * 100.0) / total;
    }

    // Add score for a subject
    public void addScore(String subject, double score) {
        if (!totalScores.containsKey(subject)) return;
        totalScores.put(subject, totalScores.get(subject) + score);
        totalExams.put(subject, totalExams.get(subject) + 1);
    }

    // Average grade for a subject
    public double getAverageGrade(String subject) {
        int exams = totalExams.getOrDefault(subject, 0);
        if (exams == 0) return 0;
        return totalScores.get(subject) / exams;
    }

    // Overall average across all subjects
    public double getOverallAverage() {
        double sum = 0;
        int count = 0;
        for (String subject : SUBJECTS) {
            int exams = totalExams.get(subject);
            if (exams > 0) {
                sum += totalScores.get(subject) / exams;
                count++;
            }
        }
        return count == 0 ? 0 : sum / count;
    }

    // Attendance methods
    public void markPresent() { present++; }
    public void markLate() { late++; }
    public void markAbsent() { absent++; }
}
