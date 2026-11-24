package JavaConstructor;

class Student{
    String name;
    int age;
    boolean isStudent;

    public Student(String name , int age , boolean isStudent){
        this.name = name;
        this.age = age;
        this.isStudent = isStudent;
    }
}

public class JavaConstructor {
    public static void main(String[]args){
        Student id1 = new Student("Muhammadziyo", 22 , true);
        System.out.println("--- Student Info ---");
        System.out.println("Name: " + id1.name);

        if(id1.age >= 18){
            System.out.println(id1.name + " is " + id1.age + " years old.He is an Adult.");
        } else {
            System.out.println(id1.name + " is " + id1.age + " years old.He is not an Adult.");
        }

        if(id1.isStudent == true){
            System.out.println(id1.name + " is Student");
        } else {
            System.out.println(id1.name + " is not Student");
        }

    }
}