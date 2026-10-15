import java.util.Scanner;
public class boolean{
    public static void main(String[]args) {
        
        Scanner scanner = new Scanner (System.in);
        int votingAge = 18;
        

            System.out.print("Enter your age: ");
        int myAge = scanner.nextInt();

        votingAge = 44;
        
           if(myAge >= votingAge) {
               System.out.println("You can vote");
           } else {
               System.out.println("You cannot vote");
           }
    }
}


/*

int myAge = 25;
int votingAge = 18;

if (myAge >= votingAge) {
  System.out.println("Old enough to vote!");
} else {
  System.out.println("Not old enough to vote.");
}

*/

