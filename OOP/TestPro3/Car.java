package TestPro3;
import java.util.Scanner;
public class Car {
    Scanner scanner = new Scanner(System.in);

    String carName = "GLE Coupe";
    double carPrice = 110000.99;

    public boolean isRunning(boolean isEngineRunning){
        if(isEngineRunning){
            System.out.println("You can drive the car, engine is running");
            return true;
        } else {
            System.out.println("Nice car, but engine is not running you cant drive a car");
            return false;
        }
    }
}
