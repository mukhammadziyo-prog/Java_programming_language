import java.util.Scanner;
public class TernaryOper{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);


        int hour;
        System.out.print("Enter an hour to see is it AM or PM: ");
        hour = scanner.nextInt();

        String timeOfDay = (hour < 12)? "A.M" : "P.M";
        System.out.println(timeOfDay);
    }
}