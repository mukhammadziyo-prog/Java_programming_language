import java.util.Scanner;
public class DoWhile{
    public static void main(String[] args) {
     Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your age: ");
        int age = scanner.nextInt();

            do{
                System.out.println("Your age cannot be negative");
                System.out.print("Enter your age: ");
                age = scanner.nextInt();
            }while(age < 0);

        System.out.println("You are "+ age + " years old");
        scanner.close();
    }
}