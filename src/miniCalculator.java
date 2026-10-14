import java.util.Scanner;
public class miniCalculator {
    public static void main(String[]args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("==========| Welcome |==========");
        System.out.print("Please enter number:");

        int number = scanner.nextInt();

        if(number == 0) {
            System.out.println("Cannot devide zero!!! Please, try again.\n=========================");
            return;

        } else if (number %2 == 0) {
            System.out.println("Juft son\n=========================");
        } else {
            System.out.println("Toq son\n=========================");
        }

        System.out.println("======| Welcome to calculator |======");

        System.out.println("Please, enter a number to calculate(+):");
        int a = scanner.nextInt();

        System.out.println("Please, enter Second number(+):");
        int b = scanner.nextInt();

        int result = a + b;
        System.out.println("The result is: " + result+ "\n=========================");

        System.out.println("Please, Enter a number to calculate(-):");
        int x = scanner.nextInt();

        if(x == 0) {
            System.out.println("You cannot subtract zero!\n=========================");
            return;
        }

        System.out.println("Please, Enter second number(-):");
        int y = scanner.nextInt();

        int resultMinus = x - y;
        System.out.println("The result is: "+ resultMinus+"\n=========================");


    }
}