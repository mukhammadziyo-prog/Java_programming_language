package testPro2;

public class Car {
    String brand = "Mercedes";
    int speed = 0;
    boolean isEngineOn = false;

    void startEngine() {
        isEngineOn = true;
    }

    void accelerate(int increment) {
        if (isEngineOn) {
            speed += increment;
            System.out.println("The speed has incremented. Speed: " + speed);
        } else {
            System.out.println("Please start the engine first");
        }
    }

    void brake(int decrement) {
        speed -= decrement;
        if (speed <= 0) {
            speed = 0;
            System.out.println("Car is stopped, current speed is 0");
        } else {
            System.out.println("Car slowed down, current speed is " + speed);
        }
    }
} // <--- CAR KLASSI SHU YERDA TUGAYDI

    class Main {
    public static void main(String[] args) {
        Car car1 = new Car();

        car1.accelerate(20); // Motor o'chiq -> ogohlantirish chiqadi
        car1.startEngine();  // Motor yondi
        car1.accelerate(50); // Tezlik 50 bo'ldi
        car1.brake(20);      // Tezlik 30 ga tushdi
        car1.brake(40);      // Tezlik 0 bo'ldi (to'xtadi)
    }
}
