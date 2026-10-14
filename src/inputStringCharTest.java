import java.util.Scanner;

public class code {
    public static void main(String[]args) {
    
    Scanner input = new Scanner(System.in);

        System.out.print("Enter Your Name: ");

        String name = input.nextLine();

        System.out.print("Enter Your Age: ");

        int age = input.nextInt();

        System.out.print("Please , Enter Your Grade: ");

        char grade = input.next().charAt(0);

    System.out.println("Hello! "+ name + " You Are " + age + " Years Old!!!");

    System.out.println("Your Grade Is: "+ grade + ". Congratulation You Are Successfuly Joined Our Team Group.");

input.close();
                
    }
}
