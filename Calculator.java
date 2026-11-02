import java.util.Scanner;
public class Calculator{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int option;
        System.out.println("===== Welcome To Calculator =====");
        System.out.println("1.Multiplication");
        System.out.println("2.Division");
        System.out.print("Choose an option: ");
            option = scanner.nextInt();

            if(option == 1){
                Multiplication(scanner);
            }
            if(option == 2){
                Division(scanner);
            } else {
                System.out.println("Please, choose a valid option");
            }

            scanner.close();
    }

    static void Multiplication(Scanner scanner2) {
        double number1;
        double number2;
        double result;
        System.out.print("Enter number to Multiple numbers: ");
        number1 = scanner2.nextDouble();
        System.out.print("Enter second number: ");
        number2 = scanner2.nextDouble();

        result = number1 * number2;
        System.out.println("The result is: " + result);

    }

    static void Division(Scanner scanner3){

        double number3;
        double number4;
        double resultOfDivision;
        System.out.print("Enter a number for Devision: ");
        number3 = scanner3.nextDouble();
        System.out.print("Enter second number: ");
        number4 = scanner3.nextDouble();
            resultOfDivision = number3 / number4;
        System.out.println("The result is: "+ resultOfDivision);

    }
}