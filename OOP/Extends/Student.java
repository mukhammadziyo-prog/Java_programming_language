package Extends;

class Student extends StudentInfo {
    public static void main(String[] args){
        StudentInfo student1 = new StudentInfo();
        System.out.println("Student name: " +student1.name);
        System.out.println("Student age: " + student1.age);
        System.out.println("Student GPA:  " + student1.gpa);
    }
}