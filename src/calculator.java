import java.util.Scanner;
public class calculator {
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

        System.out.print("Please, enter a number to calculate(+):");
        int a = scanner.nextInt();

        System.out.print("Please, enter Second number(+):");
        int b = scanner.nextInt();

        int result = a + b;
        System.out.println("The result is: " + result+ "\n=========================");

        System.out.print("Please, enter a number to calculate(-):");
        int x = scanner.nextInt();

        if(x == 0) {
            System.out.println("You cannot subtract zero!\n=========================");
            return;
        }

        System.out.print("Please, enter second number(-):");
        int y = scanner.nextInt();

        int resultMinus = x - y;
        System.out.println("The result is: "+ resultMinus+"\n=========================");

        System.out.print("Please enter a number to devision(/):");

        double devision1 = scanner.nextDouble();

        System.out.print("Please, enter a second number(/):");

        double devision2 = scanner.nextDouble();

        if(devision2 == 0) {
            System.out.println("Sorry, cannot devide by zero!!!");
            return;
        }

        double resultDevision = devision1 / devision2;

        System.out.println("The result is: " + resultDevision + "\n=========================");

        System.out.print("Please, enter a number to multiple(*):");

        int multiple1 = scanner.nextInt();

        System.out.print("Please, enter a second number(*):");

        int multiple2 = scanner.nextInt();

        int multiResult = multiple1 * multiple2;

        System.out.println("The result is: "+ multiResult + "\n=========================");
        System.out.println("======| THANKS FOR EVERYTHING :) |=====");


    scanner.close();
    }
}