public class Car{
    String make = "Mercedes";
    String model = "GLE Coupe";
    double price = 110000.99;
    int year = 2006;
    boolean isRunning = true;

    void start(){
        isRunning = true;
        System.out.println("You start the engine");
    }
    void stop(){
        isRunning = false;
        System.out.println("Car is Stopped");
    }
    void drive(){
        System.out.println("You drive "+ make + " " + model);
    }
    void pricee(){
        System.out.println("The cost of "+ model + " is " + price);
    }

}