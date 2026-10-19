import java.util.Scanner;
public class LogicOper {
    public static void main(String[]args) {
		System.out.println("========== Welcome to Theatre ==========");		
        Scanner scanner = new Scanner(System.in);
                System.out.print("Please enter your age: ");
            
        int age = scanner.nextInt();

                System.out.print("Do you come here with your parents(true/false): ");
        
        boolean hasParents = scanner.nextBoolean();
                System.out.print("Do you have a ticket(true/false)?: ");
        boolean hasTicket = scanner.nextBoolean();

        boolean canEnter = (age >= 18 || hasParents) && hasTicket;
            System.out.println("Sizning kirish imkoningiz: "+ canEnter);
    }
}
