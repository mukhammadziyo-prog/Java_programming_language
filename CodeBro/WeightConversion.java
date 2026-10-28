import java.util.Scanner;
public class WeightConversion {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        double weight;
        double newWeight;
        int choice;

        System.out.println("Weight conversion program");
        System.out.println("1. Convert lbs to kgs");
        System.out.println("2. Convert kgs to lbs");

        System.out.print("Choose an option: ");
        choice = scanner.nextInt();

        if(choice == 1){
            System.out.print("Enter the weight in lbs: ");
            weight = scanner.nextDouble();
            newWeight = weight * 0.4536;
            System.out.println("The new weight in kgs is: "+ newWeight);
        }
        if(choice == 2){
            System.out.print("Enter a weight in kgs: ");
            weight = scanner.nextDouble();
            newWeight = weight * 2.2046;
            System.out.println("The new weight in lbs is: "+ newWeight);
        }
            else {
            System.out.println("Enter the valid choice!");
        }
            scanner.close();
    }
}
