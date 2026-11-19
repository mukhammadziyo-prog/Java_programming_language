package exercise3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numberOfPets = readNumber(scanner);

        List<Animal> pets = new ArrayList<>();

        IntStream.range(0, numberOfPets)
                .forEach(i -> readPet(scanner, pets));

        pets.stream()
                .map(pet -> {
                    if (pet.getAge() > 10) {
                        int newAge = pet.getAge() + 1;

                        if (pet instanceof Dog) {
                            return new Dog(pet.getName(), newAge);
                        } else {
                            return new Cat(pet.getName(), newAge);
                        }
                    }

                    return pet;
                })
                .forEach(System.out::println);
    }

    private static void readPet(Scanner scanner, List<Animal> pets) {
        String type = scanner.next();

        if (!type.equals("dog") && !type.equals("cat")) {
            System.out.println("Incorrect input. Unsupported pet type");
            return;
        }

        String name = scanner.next();
        int age = readNumber(scanner);

        if (age <= 0) {
            System.out.println("Incorrect input. Age <= 0");
            return;
        }

        if (type.equals("dog")) {
            pets.add(new Dog(name, age));
        } else {
            pets.add(new Cat(name, age));
        }
    }

    private static int readNumber(Scanner scanner) {
        if (!scanner.hasNextInt()) {
            System.out.println(
                    "Could not parse a number. Please, try again"
            );

            scanner.next();
            return readNumber(scanner);
        }

        return scanner.nextInt();
    }
}