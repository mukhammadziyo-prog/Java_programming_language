import java.util.Scanner;
public class TimeConverter{

    static int hours;
    static int minutes;
    static int seconds;

    public static void main(String[]args) {
        Scanner scanner = new Scanner(System.in);

        int totalSeconds = readSeconds(scanner);

        if(totalSeconds < 0) {
            System.out.println("Incorrect time.");
        } else {
            calculateTime(totalSeconds);
            printTime();
        }

        scanner.close();
    }

    public static int readSeconds(Scanner scanner) {
        while(!scanner.hasNextInt()){
            System.out.println("Could not parse a number. Please try again");
            scanner.next();
        }

        return scanner.nextInt();
    }
    public static void calculateTime(int totalSecond){

        hours = totalSecond / 3600;
        minutes = (totalSecond % 3600) /60;
        seconds = totalSecond % 60;

    }
    public static void printTime() {
        System.out.printf("%02d:%02d:%02d\n", hours,minutes,seconds);
    }
}