package javaapplication1.Java;

public class Student {

    // Basic student information
    private String name;
    private int section;

    // Attendance counters
    private int present;
    private int late;
    private int absent;

    // List of subjects available
    public static final String[] subjects = {"Math", "Science", "English"};

    // Stores total scores and exam count per subject (parallel to subjects[] index)
    private double[] totalScores = new double[subjects.length];
    private int[] totalExams = new int[subjects.length];

    // Constructor: initializes student info and resets all counters
    public Student(String name, int section) {
        this.name = name;
        this.section = section;
        this.present = 0;
        this.late = 0;
        this.absent = 0;

        // Initialize score arrays
        for (int i = 0; i < subjects.length; i++) {
            totalScores[i] = 0.0;
            totalExams[i] = 0;
        }
    }

    
    // Getters
    public String getName() { return name; }
    public int getSection() { return section; }
    public int getPresent() { return present; }
    public int getLate() { return late; }
    public int getAbsent() { return absent; }

    
    // Attendance Computation
   

    // Computes attendance percentage based on: Present / (Present + Late + Absent)
    public double getAttendanceRate() {
        int total = present + late + absent;
        if (total == 0) return 0; // Avoid division by zero
        return (present * 100.0) / total;
    }

    
    // Score Handling
  
    // Adds a score to the given subject
    public void addScore(String subject, double score) {
        int index = getSubjectIndex(subject);
        if (index == -1) return; // Invalid subject
        
        totalScores[index] += score;
        totalExams[index]++;
    }

    // Returns the average score for one subject
    public double getSubjectAverage(String subject) {
        int index = getSubjectIndex(subject);

        if (index == -1) return 0;
        if (totalExams[index] == 0) return 0; // No exams taken yet

        return totalScores[index] / totalExams[index];
    }

    // Returns the average of all subject averages
    public double getOverallAverage() {
        double sum = 0;
        int count = 0;

        // Loop through each subject
        for (int i = 0; i < subjects.length; i++) {
            if (totalExams[i] > 0) {
                sum += totalScores[i] / totalExams[i];
                count++;
            }
        }

        return count == 0 ? 0 : sum / count;
    }

    // Helper method: returns the index of a subject in the subjects[] array
    private int getSubjectIndex(String subject) {
        for (int i = 0; i < subjects.length; i++) {
            if (subjects[i].equalsIgnoreCase(subject)) return i;
        }
        return -1; // Not found
    }

   
    // Attendance Updates
   
    public void markPresent() { present++; }
    public void markLate() { late++; }
    public void markAbsent() { absent++; }
}
