package TestPro3;
public class DeclareMain {
    public static void main(String[] args){
        Car myCar1 = new Car();
        System.out.println("The model: " + myCar1.carName);
        System.out.println("The price of the car: " + myCar1.carPrice);

        myCar1.isRunning(false);

    }
}