package HarvestProject;

import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static Fruits product = new Fruits();

    static void pickFruit(){
        System.out.print("Please, Pick One Fruit(only accept numbers): ");
        int pickFruit = scanner.nextInt();
            if(pickFruit == 1){
                System.out.println("Accepted. You picked: " + product.fruits[0]);
            } else if(pickFruit == 2){
                System.out.println("Accepted. You picked: "+ product.fruits[1]);
            } else if(pickFruit == 3){
                System.out.println("Accepted. You picked: "+ product.fruits[2]);
            } else if(pickFruit == 4){
                System.out.println("Accepted. You picked: " + product.fruits[3]);
            } else {
                System.out.println("Please, Pick Valid Fruit! ");
            }
    }


    static void Box() {
        while (true) {
            System.out.print("Enter How Many Boxes Do You Need: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.next();
                continue;
            }

            int boxes = scanner.nextInt();

            if (boxes > 5) {
                System.out.println("Too many boxes, please enter 5 or less.");
            } else if (boxes <= 0) {
                System.out.println("Please enter at least 1 box.");
            } else {
                System.out.println("Accepted! You have got " + boxes + " boxes.");
                break;
            }
        }
    }

    static void showFruit() {
        for (int i = 0; i < product.fruits.length; i++) {
            System.out.println((i + 1) + ". " + product.fruits[i]);
        }
    }

    public static void main(String[] args) {
        System.out.println("****----------------****");
        System.out.println("      --WELCOME--");
        System.out.println("  Fruit Harvest Season");
        System.out.println("   **-------------**\n");

        System.out.println("What Would You Like To Collect?");
        System.out.println("1. -- Fruits -->");
        System.out.println("2. -- Vegetables -->");
        System.out.println("3. -- Exit -->");

        boolean isRunning = true;

        while (isRunning) {
            System.out.print("Enter Your Choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Please, enter a valid number (1, 2, or 3)!");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\nHere Are Our Fruits: ");

                    showFruit();
                    pickFruit();
                    Box();
                    isRunning = false;
                    break;
                case 2:
                    System.out.println("Vegetables are coming soon...");
                    isRunning = false;
                    break;
                case 3:
                    System.out.println("Goodbye! :)");
                    isRunning = false;
                    break;
                default:
                    System.out.println("Please, enter valid choice!");
            }
        }


        scanner.close();
    }
}
