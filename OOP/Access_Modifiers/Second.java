package Access_Modifiers;
// Asosiy klass (fayl nomi bilan bir xil)
public class Second {
    public static void main(String[] args) {
        // Student klassidan obyekt yaratish
        Student myObj = new Student();

        System.out.println("Name: " + myObj.fname);
        System.out.println("Age: " + myObj.age);
        System.out.println("Graduation Year: " + myObj.graduationYear);
        myObj.study(); // abstract metodni chaqirish
    }
}

// Abstract klass
abstract class Main {
    public String fname = "John";
    public int age = 24;
    public abstract void study(); // abstract metod
}

// Voris klass (Main klassidan meros oladi)
class Student extends Main {
    public int graduationYear = 2018;
    public void study() {
        // abstract metodning tanasi shu yerda yoziladi
        System.out.println("Studying all day long");
    }
}
