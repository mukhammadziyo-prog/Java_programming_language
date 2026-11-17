
package exercise4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Scanner scanner = new Scanner(System.in);

        long programStart = System.nanoTime();

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

            if (type.equals("dog")) {
                pets.add(new Dog(name, age));
            } else {
                pets.add(new Cat(name, age));
            }
        }

        CountDownLatch ready = new CountDownLatch(pets.size());
        CountDownLatch startSignal = new CountDownLatch(1);

        List<Thread> threads = new ArrayList<>();

        for (Animal pet : pets) {

            Thread thread = new Thread(() -> {

                ready.countDown();

                try {
                    startSignal.await();

                    double startTime =
                            (System.nanoTime() - programStart) / 1_000_000_000.0;

                    System.out.printf(
                            "%s started walking at %.3f seconds%n",
                            pet, startTime
                    );

                    long walkTimeMillis =
                            Math.round(pet.goToWalk() * 1000);

                    TimeUnit.MILLISECONDS.sleep(walkTimeMillis);

                    double endTime =
                            (System.nanoTime() - programStart) / 1_000_000_000.0;

                    System.out.printf(
                            "%s finished walking at %.3f seconds%n",
                            pet, endTime
                    );

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

            });

            threads.add(thread);
            thread.start();
        }

        ready.await();

        startSignal.countDown();

        for (Thread thread : threads) {
            thread.join();
        }

        scanner.close();
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
