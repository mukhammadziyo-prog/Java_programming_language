package TestPro3;

import java.util.Scanner;

public class DeclareMain {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        Car myCar1 = new Car();

        System.out.print("Enter your Car name: ");
        myCar1.carName = scanner.nextLine();
        System.out.println("The model: " + myCar1.carName);

        System.out.print("Enter your Car Price: ");
        myCar1.carPrice = scanner.nextDouble();
        System.out.println("The price of the car: " + myCar1.carPrice);

        myCar1.isRunning(false);



    }
}