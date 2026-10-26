import java.util.Scanner;
public class CodeBro2{
    public static void main(String[] args){

        //  MAD LIBS GAME

        Scanner scanner = new Scanner(System.in);

        String adjective1;
        String noun1;
        String adjective2;
        String verb1;
        String adjective3;

        System.out.print("Enter an adjective(describtion): ");
        adjective1 = scanner.nextLine();
        System.out.print("Enter a noun(animal or person): ");
        noun1 = scanner.nextLine();
        System.out.print("Enter an adjective(description): ");
        adjective2 = scanner.nextLine();
        System.out.print("Enter a verb end with -ing(action): ");
        verb1 = scanner.nextLine();
        System.out.print("Enter a adjective(describtions): ");
        adjective3 = scanner.nextLine();


        System.out.println("Today i went to a "+ adjective1 + " zoo.");
        System.out.println("In an exibit i saw "+ noun1 + ".");
        System.out.println(noun1 + " was "+ adjective2 + " and "+ verb1 + "!");
        System.out.println("i was "+ adjective3 + "!");




        scanner.close();
    }
}