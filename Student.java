package javaapplication1.Java;

public class Student {

    private String name;
    private int section;
    private int present;
    private int late;
    private int absent;

    // Subjects
    public static final String[] subjects = {"Math", "Science", "English"};


    // Scores & Exam Count (per subject index)
    private double[] totalScores = new double[subjects.length];
    private int[] totalExams = new int[subjects.length];

    public Student(String name, int section) {
        this.name = name;
        this.section = section;
        this.present = 0;
        this.late = 0;
        this.absent = 0;

        // Initialize all totals
        for (int i = 0; i < subjects.length; i++) {
            totalScores[i] = 0.0;
            totalExams[i] = 0;
        }
    }

    // ===============================
    // Getters
    // ===============================
    public String getName() {
        return name;
    }

    public int getSection() {
        return section;
    }

    public int getPresent() {
        return present;
    }

    public int getLate() {
        return late;
    }

    public int getAbsent() {
        return absent;
    }

    // ===============================
    // Attendance Computation
    // ===============================
    public double getAttendanceRate() {
        int total = present + late + absent;
        if (total == 0) return 0;
        return (present * 100.0) / total;
    }

    // ===============================
    // Score Handling
    // ===============================

    public void addScore(String subject, double score) {
        int index = getSubjectIndex(subject);
        if (index == -1) return;

        totalScores[index] += score;
        totalExams[index]++;
    }

    public double getSubjectAverage(String subject) {
        int index = getSubjectIndex(subject);
        if (index == -1) return 0;

        if (totalExams[index] == 0) return 0;

        return totalScores[index] / totalExams[index];
    }

    public double getOverallAverage() {
        double sum = 0;
        int count = 0;

        for (int i = 0; i < subjects.length; i++) {
            if (totalExams[i] > 0) {
                sum += (totalScores[i] / totalExams[i]);
                count++;
            }
        }

        return count == 0 ? 0 : sum / count;
    }

    // Helper method to find subject index
    private int getSubjectIndex(String subject) {
        for (int i = 0; i < subjects.length; i++) {
            if (subjects[i].equalsIgnoreCase(subject)) {
                return i;
            }
        }
        return -1; // Not found
    }

    // ===============================
    // Attendance Updates
    // ===============================
    public void markPresent() {
        present++;
    }

    public void markLate() {
        late++;
    }

    public void markAbsent() {
        absent++;
    }
}
