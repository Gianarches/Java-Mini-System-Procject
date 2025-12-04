package javaapplication1.Java;

 class AttendanceManager {
    public static void record(Student s, int type) {
        switch(type) {
            case 1:
                s.markPresent();
                break;
            case 2:
                s.markLate();
                break;
            case 3:
                s.markAbsent();
                break;
            default:
                // Optional: handle invalid type
                System.out.println("Invalid attendance type: " + type);
                break;
        }
    }
}
