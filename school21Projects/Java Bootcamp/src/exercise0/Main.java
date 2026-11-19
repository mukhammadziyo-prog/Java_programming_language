package exercise0;

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

            int age = readAge(scanner);

            if (age <= 0) {
                System.out.println("Incorrect input. Age <= 0");
                continue;
            }

            if (type.equals("dog")) {
                pets.add(new Dog(name, age));
            } else {
                pets.add(new Cat(name, age));
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
                System.out.println("Could not parse a number. Please, try again");
            }
        }
    }

    private static int readAge(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.next());
            } catch (NumberFormatException e) {
                System.out.println("Could not parse a number. Please, try again");
            }
        }
    }
}