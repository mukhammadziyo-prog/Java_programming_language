package HarvestProject;

import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static Fruits product_obj = new Fruits();
    static Vegetables vegetables_obj = new Vegetables();

    static void pickFruit() {
        System.out.print("Please, Pick One Fruit(only accept numbers): ");
        int pickFruit = scanner.nextInt();
        if (pickFruit == 1) {
            System.out.println("Accepted. You picked: " + product_obj.fruits[0]);
        } else if (pickFruit == 2) {
            System.out.println("Accepted. You picked: " + product_obj.fruits[1]);
        } else if (pickFruit == 3) {
            System.out.println("Accepted. You picked: " + product_obj.fruits[2]);
        } else if (pickFruit == 4) {
            System.out.println("Accepted. You picked: " + product_obj.fruits[3]);
        } else if (pickFruit == 5) {
            System.out.println("Accepted. You picked: " + product_obj.fruits[4]);
        } else if (pickFruit == 6){
            System.out.println("Accepted. You picked: " + product_obj.fruits[5]);
        }else {
            System.out.println("Please, Pick Valid Fruit! ");
        }
    }


    static void pickVegetables() {
        System.out.print("Please, Pick One Vegetable(only accept numbers): ");
        int pickVegetable = scanner.nextInt();
        if (pickVegetable == 1) {
            System.out.println("Accepted. You picked: " + vegetables_obj.vegetables[0]);
        } else if (pickVegetable == 2) {
            System.out.println("Accepted. You picked: " + vegetables_obj.vegetables[1]);
        } else if (pickVegetable == 3) {
            System.out.println("Accepted. You picked: " + vegetables_obj.vegetables[2]);
        } else if (pickVegetable == 4) {
            System.out.println("Accepted. You picked: " + vegetables_obj.vegetables[3]);
        } else if (pickVegetable == 5) {
            System.out.println("Accepted. You picked: " + vegetables_obj.vegetables[4]);
        } else if (pickVegetable == 6){
            System.out.println("Accepted. You picked: " + vegetables_obj.vegetables[5]);
        } else if(pickVegetable == 7) {
            System.out.println("Accepted. You picked: "+ vegetables_obj.vegetables[6]);
        }else if(pickVegetable == 8){
            System.out.println("Accepted. You picked: " + vegetables_obj.vegetables[7]);
        } else {
            System.out.println("Please, choose a valid Vegetable");
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
        for (int i = 0; i < product_obj.fruits.length; i++) {
            System.out.println((i + 1) + ". " + product_obj.fruits[i]);
        }
    }

    static void showVegetables() {
        for(int i = 0;i < vegetables_obj.vegetables.length; i++){
            System.out.println((i + 1) + ". " + vegetables_obj.vegetables[i]);
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
                    System.out.println("\nHere Are Our Vegetables: ");

                    showVegetables();
                    pickVegetables();
                    Box();

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
