package Extends;

class Student extends StudentInfo {
    public Student(String name, int age, double gpa) {
        super(name, age, gpa);
    }

    public static void main(String[] args) {
        StudentInfo student1 = new StudentInfo("Muhammadziyo" , 19 , 4.3);

        student1.studentInfo();

    }
}