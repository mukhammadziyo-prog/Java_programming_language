
import java.util.Scanner;

public class TrianglePerimeter {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        double x1 = readNumber(scanner);
        double y1 = readNumber(scanner);
        double x2 = readNumber(scanner);
        double y2 = readNumber(scanner);
        double x3 = readNumber(scanner);
        double y3 = readNumber(scanner);

        double ab = Math.sqrt(
                Math.pow(x2 - x1, 2) +
                        Math.pow(y2 - y1, 2)
        );

        double bc = Math.sqrt(
                Math.pow(x3 - x2, 2) +
                        Math.pow(y3 - y2, 2)
        );

        double ca = Math.sqrt(
                Math.pow(x1 - x3, 2) +
                        Math.pow(y1 - y3, 2)
        );

        if (ab + bc > ca && ab + ca > bc && bc + ca > ab) {
            double perimeter = ab + bc + ca;
            System.out.printf("Perimeter: %.3f%n", perimeter);
        } else {
            System.out.println("It's not a triangle");
        }

        scanner.close();
    }

    public static double readNumber(Scanner scanner) {
        while (!scanner.hasNextDouble()) {
            System.out.println(
                    "Could not parse a number. Please try again"
            );
            scanner.next();
        }

        return scanner.nextDouble();
    }
}
