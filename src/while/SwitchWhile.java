import javax.naming.BinaryRefAddr;
import java.util.Scanner;
public class SwitchWhile {
public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("===== Answer and questions =====");

    System.out.println("1.calculating");
    System.out.println("2.Division");
    System.out.print("Select the number: ");
    int choose = scanner.nextInt();

    switch(choose) {
        case 1:
            System.out.print("What is 34 x 3: ");
            int num1 = scanner.nextInt();

            while(num1 != 102) {
                System.out.println("This is INCORRECT.Please, try again!");
                System.out.print("Enter your correct answer: ");
                num1 = scanner.nextInt();
            }

                System.out.println("Your answer is " + num1 + " which is CORRECT");
                break;



        case 2:
            System.out.println("What is 90 divided 3: ");
            int num2 = scanner.nextInt();

            while(num2 != 30) {
                System.out.println("Your answer is INCORRECT");
                System.out.print("Enter your correct answer: ");
                    num2 = scanner.nextInt();
            }
            System.out.println("Your answer is "+ num2 + " which is CORRECT");
            break;

                default:
                    System.out.println("Wrong selection.Please select a valid number!!!");
                    System.exit(0);
    }


    scanner.close();
    }
}
