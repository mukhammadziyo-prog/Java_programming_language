import java.util.Scanner;
public class BankingProgram {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args){

        double balance = 0;
        double deposit = 0;
        double withdraw = 0;
        boolean isRunning = true;

        while (isRunning){

        System.out.println("***************");
        System.out.println("Banking Program");
        System.out.println("***************");

        System.out.println("1. Show balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Exit");
        System.out.println("***************");

        System.out.print("Enter your choice(1-4): ");
        int choice = scanner.nextInt();

            switch(choice) {
                case 1 -> showBalance(balance);
                case 2 -> balance = balance + deposit();
                case 3 -> balance = balance - withdraw(balance);
                case 4 -> isRunning = false;
                default -> System.out.println("Invalid Choice");
            }
            }

        System.out.println("********************************");
        System.out.println("Thank you! Have a wonderful day!");
        System.out.println("********************************");

            scanner.close();
    }

        static void showBalance(double balance){
            System.out.printf("$%.2f\n",balance);
    }

        static double deposit(){
        double amount;

            System.out.print("Enter an amount to be deposited: ");
            amount = scanner.nextDouble();

            if(amount < 0){
                System.out.println("Amount cannot be negative");
                return 0;

            } else {
                return amount;
            }

        }

        static double withdraw(double balance) {

            double amount;
            System.out.print("Enter amount to be withdrawn: ");
            amount = scanner.nextDouble();

            if (amount > balance) {
                System.out.println("insufficient funds");
                return 0;
            } else if (amount < 0) {
                System.out.println("Amount cannot be negative");
                return 0;
            } else {
                return amount;
            }

    }
}