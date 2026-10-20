import java.util.Scanner;
public class SwitchRestaurant {
    public static void main(String[]args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("===== Welcome to Restaurant =====");
        System.out.println("What you want to eat:\n1.Pizza\n2.Burger\n3.Pasta");

        System.out.println("=== Choose a number ===");
        int number = scanner.nextInt();

        switch(number) {
            case 1: System.out.println("Your choice is Pizza.\nIts going to be ready in 10 minutes");
                break;
            case 2: System.out.println("Your choice is Burger.\nIts going to be ready in 5 minutes");
                break;
            case 3: System.out.println("Your choice is Pasta.\nIts going to be ready in 5 minutes");
                break;

            default:
                System.out.println("Please, enter a valid number!");
                scanner.close();
                System.exit(0);
        }
        System.out.println("===== Did you like our servise? =====\n1.Yes  2.No");
        int yesNo = scanner.nextInt();

        switch(yesNo) {
            case 1:
                System.out.println("===== Congratulations you have a 15% discount for your next order!!! ======");
                break;
            case 2:
                System.out.println("==== Please note that why you did not like our servise. We will try our best THANK YOU! =====");
                break;
            default:
                System.out.println("Please, enter valid number!");

                scanner.close();
                System.exit(0);
        }

        /* scanner.close(); buni ham qoshish mumkin agar tepada scanner closa va System.exit bolmasa yani ,
        agar program yana qoshilsa va xato kiritilsa ham davom etishi kerek bolsa scanner 42 qator ochiladi
                va scanner.exit(0) olib tashlanadi.
        */
    }
}