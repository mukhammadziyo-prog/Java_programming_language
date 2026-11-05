import java.util.Scanner;
public class Methods {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        myName(scanner);
        myAge(scanner);
        schoolGpa(scanner);

        printResult();

        scanner.close();
    }

    public static void myName(Scanner scanner2) {
        String name;
        System.out.print("Enter your name: ");
        name = scanner2.nextLine();
        System.out.println("Hello "+ name);
    }

    public static void myAge(Scanner scanner3){
        System.out.print("Enter your age: ");
        int age;
        age = scanner3.nextInt();
        System.out.println("You are "+ age + " years old");
    }

    public static void schoolGpa(Scanner scanner4) {
        System.out.print("Enter your school GPA: ");
        double gpa;
        gpa = scanner4.nextDouble();
        System.out.println("Your Gpa is: "+ gpa);
    }

    public static void printResult() {
        System.out.print(55);
    }
}