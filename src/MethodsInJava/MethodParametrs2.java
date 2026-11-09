import java.util.Scanner;
public class MethodParametrs2{
    public static void main(String[]args){
        Scanner scanner = new Scanner(System.in);
        returnValue(scanner);



        scanner.close();
    }


    static void returnValue(Scanner scanner){
        System.out.print("Enter yout name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your age: ");
        int age = scanner.nextInt();
        System.out.println(name + " " + age);

    }
}