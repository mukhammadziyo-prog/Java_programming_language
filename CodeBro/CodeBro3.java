import java.util.Scanner;
public class CodeBro3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("What item would you like to buy?: ");
        String item = scanner.nextLine();

        System.out.print("What is the price for each?: ");
        double price = scanner.nextDouble();


        System.out.print("How many would you like?: ");
        int amount = scanner.nextInt();

        double sum = price * amount;

        System.out.println("You have bought "+ amount + " "+ item);
        System.out.println("Total price is: "+"$"+ sum);


        scanner.close();
    }
}