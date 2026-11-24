/*
// Create a Main class
public class Main {
    int x;  // Create a class attribute

    // Create a class constructor for the Main class
    public Main() {
        x = 5;  // Set the initial value for the class attribute x
    }

    public static void main(String[] args) {
        Main myObj = new Main(); // Create an object of class Main (This will call the constructor)
        System.out.println(myObj.x); // Print the value of x
    }
}

// Outputs 5

 */

import java.security.cert.X509Certificate;


/*
public class Constructor_Parameters {
    int x;

    public Constructor_Parameters(int y){
        x = y;
    }

    public static void main(String[] args){
        Constructor_Parameters cons1 = new Constructor_Parameters(5);
        System.out.println(cons1.x);
    }
}

Constructors can also take parameters, which is used to initialize attributes.

The following example adds an int y parameter to the constructor. Inside the constructor we set x to y (x=y).
When we call the constructor, we pass a parameter to the constructor (5), which will set the value of x to 5:


 */


//You can have as many parameters as you want:

public class Constructor_Parameters {
    int modelYear;
    String modelName;

    public Constructor_Parameters(int year , String name){
        modelYear = year;
        modelName = name;
    }

    public static void main(String[] args){
        Constructor_Parameters cons1 = new Constructor_Parameters(1995 , "Ford Mustang");

        System.out.println(cons1.modelYear + " " + cons1.modelName);
    }
}
