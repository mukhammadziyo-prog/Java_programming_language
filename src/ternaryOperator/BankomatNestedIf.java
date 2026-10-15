import java.util.Scanner;
public class BankomatNestedIf{
    public static void main(String[]args) {

        Scanner scanner = new Scanner(System.in);

        double balance = 100000.00;
        double limit = 50000.00;

        System.out.println("===== Welcome =====");
        System.out.print("Please, enter the amount to withdraw:");
            double summa = scanner.nextDouble();

            if(summa <= balance) {

                if (summa <= limit) {
                    System.out.println("pul yechildi");
                } else {
                    System.out.println("Limitdan oshdi. Limit: " + limit);
                }
            } else {
                    System.out.println("Mablag yetarli emas");
                }
            }
    }