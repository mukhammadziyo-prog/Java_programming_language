import java.util.Scanner;
public class FactorialRecursion {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter factorial number: ");
        int num1 = scanner.nextInt();
        System.out.printf("The factorial of %d is: %d%n", num1, factorial(num1));


        scanner.close();
    }

    public static int factorial(int k){
        if(k == 1){
            return 1;
        } else {
            return k * factorial(k - 1);
        }
    }
}