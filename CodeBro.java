import java.util.Scanner;
public class CodeBro {
    public static void main(String[]args){

        Scanner scanner = new Scanner(System.in);

        double width = 0;
        double height = 0;
        double area = 0;

        System.out.print("Enter width: ");
        width = scanner.nextDouble();

        System.out.print("Enter ");
        height = scanner.nextDouble();

        area = width * height;
        System.out.println("The are is: "+ area);

        scanner.close();
    }
}