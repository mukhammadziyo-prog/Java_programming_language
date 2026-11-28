package Extends;

public class StudentInfo {
    protected String name;
    protected int age;
    protected double gpa;

    public StudentInfo(String name, int age , double gpa) {
        this.name = name;
        this.age = age;
        this.gpa = gpa;
    }
    void studentInfo(){

        System.out.println("\n----- Student Info -----");
        System.out.println("Student name: " + name);
        System.out.println("Student age: " + age);
        System.out.println("Student GPA: " + gpa);
    }
    }

