public class MultipleObjects {
    String name = "Muhammadziyo";
    boolean isStudent = true;
    int age = 19;

    public static void main(String[] args){
        MultipleObjects myObj1 = new MultipleObjects();
        MultipleObjects myObj2 = new MultipleObjects();
        MultipleObjects myObj3 = new MultipleObjects();

        System.out.println("Name: " + myObj1.name + "\n" + "Age: "+ myObj1.age + "\n"+ "Is a Student: "+ myObj1.isStudent);
        System.out.println(myObj2.name + " is " + myObj2.age + " " + myObj2.isStudent);
        System.out.println("Name: "+myObj3.name);
    }
}