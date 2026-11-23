import java.util.Scanner;

public class ClassMethods{
    Scanner scanner = new Scanner(System.in);


    public void fullThrottle(){  //created a fullThrottle method
        System.out.println("The car is going fast as it can!");

    }

    public void speed(int maxSpeed){   //created speed method and i added parameter
        System.out.println("The max speed is: " + maxSpeed);
    }

    public boolean stopTheCar(boolean brake){
        if(brake){
            System.out.println("...Hoorey, finally car is going to stop!");
            return true;
        } else {
            System.out.println("Oh Noo, Car is still going fast we need to stop the car!  :(");
            return false;
        }
    }


    public static void main(String[]args){
        ClassMethods myCar = new ClassMethods();  //created object from ClassMethods

        myCar.fullThrottle();  // called the fullThrottle method
        myCar.speed(300); // called the speed method
        myCar.stopTheCar(false);
        myCar.stopTheCar(true); // you can delete 32th line if you dont want to add another method at the end.
    }
}