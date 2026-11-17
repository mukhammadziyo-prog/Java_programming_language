package exercise1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int numberOfPets = readNumber(scanner);

        List<Animal> pets = new ArrayList<>();

        for (int i = 0; i < numberOfPets; i++) {

            String type = scanner.next();

            if (!type.equals("dog") && !type.equals("cat")) {
                System.out.println("Incorrect input. Unsupported pet type");
                continue;
            }

            String name = scanner.next();

            int age = readNumber(scanner);

            if (age <= 0) {
                System.out.println("Incorrect input. Age <= 0");
                continue;
            }

            double weight = readDouble(scanner);

            if (weight <= 0) {
                System.out.println("Incorrect input. Mass <= 0");
                continue;
            }

            if (type.equals("dog")) {
                pets.add(new Dog(name, age, weight));
            } else {
                pets.add(new Cat(name, age, weight));
            }
        }

        for (Animal pet : pets) {
            System.out.println(pet);
        }

        scanner.close();
    }

    private static int readNumber(Scanner scanner) {

        while (true) {
            try {
                return Integer.parseInt(scanner.next());
            } catch (NumberFormatException e) {
                System.out.println(
                        "Could not parse a number. Please, try again"
                );
            }
        }
    }

    private static double readDouble(Scanner scanner) {

        while (true) {
            try {
                return Double.parseDouble(scanner.next());
            } catch (NumberFormatException e) {
                System.out.println(
                        "Could not parse a number. Please, try again"
                );
            }
        }
    }
}