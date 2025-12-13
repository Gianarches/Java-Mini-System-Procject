    package javaapplication1.Java;

    class AttendanceManager {

         // Handles attendance update based on type:
        // 1 = Present, 2 = Late, 3 = Absent
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
                    System.out.println("Invalid attendance type");
            }
        }
    }
