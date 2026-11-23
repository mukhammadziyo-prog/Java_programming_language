package TestPro3;
public class Car {
    String carName;
    double carPrice;

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
