import java.util.Scanner;
public class RegisterBook {
    public static void main(String[]args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Have you registered before(true/false)?: ");
        boolean isRegistered = scanner.nextBoolean();

        System.out.print("Do you have a Premuim(true/false)?: ");
        boolean isPremium = scanner.nextBoolean();

        System.out.print("Do your book is free(true/false)?: ");
        boolean isfree = scanner.nextBoolean();

        System.out.print("Have ever been blocked by Application(true/false)?: ");
        boolean isBlocked = scanner.nextBoolean();

        boolean canDownload = isRegistered && (isPremium || isfree) && !isBlocked;

        System.out.println("Can i dowload the book: "+ canDownload);
scanner.close();
    }
}
