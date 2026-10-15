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


/*Boolean Values
A boolean type is declared with the boolean keyword and can only take the values true or false:

boolean isJavaFun = true;
boolean isFishTasty = false;

System.out.println(isJavaFun);    // Outputs true
System.out.println(isFishTasty);  // Outputs false

*/

