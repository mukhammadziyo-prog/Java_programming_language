import java.util.Scanner;
import java.util.Random;

public class RandomNumber{
    public static void main(String[]args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int computerNumber = random.nextInt(10)+ 1;
        int userNumber = 0;

            System.out.println("Computer thought a number from 1 - 10. Find out it");

        while(userNumber != computerNumber) {
            System.out.print("Enter your number: ");
            userNumber = scanner.nextInt();

            if(userNumber < computerNumber) {
                System.out.println("Enter greater number");
            } else if(userNumber > computerNumber){
                System.out.println("Enter less number");
            } else {
                System.out.println("You got the number!!!");
            }
        }
        scanner.close();
    }
}
