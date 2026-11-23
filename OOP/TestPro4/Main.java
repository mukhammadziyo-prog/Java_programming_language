package TestPro4;

         class Car {
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

public class Main {
    public static void main(String[]args){
        Car car = new Car();


        //System.out.println(car.make);
        //System.out.println(car.model);
        //System.out.println(car.year);
        //System.out.println(car.price);
        //System.out.println(car.isRunning);

        //System.out.println(car.isRunning);
        //car.start();
        //System.out.println(car.isRunning);
        //car.stop();
        //System.out.println(car.isRunning);


        car.start();
        car.drive();
        car.pricee();
        car.stop();
    }
}