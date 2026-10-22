import java.util.Scanner;
public class while3{
    public static void main(String []args) {
        Scanner scanner = new Scanner(System.in);

            System.out.print("Enter your age: ");
        int age = scanner.nextInt();

        while(age < 0) {
            System.out.println("Your age cant be negative");
            System.out.print("Enter your age: ");
            age = scanner.nextInt();
        }
        System.out.println("Your are "+ age + " years old");

        scanner.close();
    }

}