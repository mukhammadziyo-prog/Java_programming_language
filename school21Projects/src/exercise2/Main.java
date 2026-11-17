
package exercise2;

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

            if (!type.equals("dog")
                    && !type.equals("cat")
                    && !type.equals("hamster")
                    && !type.equals("guinea")) {

                System.out.println("Incorrect input. Unsupported pet type");
                continue;
            }

            String name = scanner.next();
            int age = readNumber(scanner);

            if (age <= 0) {
                System.out.println("Incorrect input. Age <= 0");
                continue;
            }

            switch (type) {
                case "dog":
                    pets.add(new Dog(name, age));
                    break;
                case "cat":
                    pets.add(new Cat(name, age));
                    break;
                case "hamster":
                    pets.add(new Hamster(name, age));
                    break;
                case "guinea":
                    pets.add(new GuineaPig(name, age));
                    break;
            }
        }

        List<Animal> herbivores = new ArrayList<>();
        List<Animal> omnivores = new ArrayList<>();

        for (Animal pet : pets) {
            if (pet instanceof Herbivore) {
                herbivores.add(pet);
            } else if (pet instanceof Omnivore) {
                omnivores.add(pet);
            }
        }

        for (Animal pet : herbivores) {
            System.out.println(pet);
        }

        for (Animal pet : omnivores) {
            System.out.println(pet);
        }
    }

    private static int readNumber(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println(
                    "Could not parse a number. Please, try again"
            );
            scanner.next();
        }

        return scanner.nextInt();
    }
}
