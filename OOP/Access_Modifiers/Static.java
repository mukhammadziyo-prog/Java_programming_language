package Access_Modifiers;
public class Static {
    // Static method
    static void myStaticMethod() {
        System.out.println("Static methods can be called without creating objects");
    }

    // Main method
    static void main(String[] args) {
        myStaticMethod();         // Call the static method
        Static.myStaticMethod();    // Or call it using the class name
    }
}