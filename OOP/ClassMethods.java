public class ClassMethods{

    public void fullThrottle(){  //created a fullThrottle method
        System.out.println("The car is going fast as it can!");

    }

    public void speed(int maxSpeed){   //created speed method and i added parameter
        System.out.println("The max speed is: " + maxSpeed);
    }


    public static void main(String[]args){
        Main myCar = new Main();  //created object from ClassMethods

        myCar.fullThrottle();  // called the fullThrottle method
        myCar.speed(300); // called the speed method

    }
}