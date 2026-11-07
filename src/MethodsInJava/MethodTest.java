public class MethodTest {
    public static void main(String[] args) {

        String name = "Muhammadziyo";
        int age = 20;

        happyBirthday(name , age);
        happyBirthday(name, age);
        happyBirthday(name,age);
    }

        static void ageCheck(int age){
            if(age > 18){
                System.out.println("Your are adult");
            } else {
                System.out.println("You are not adult");
            }


    }
    static void happyBirthday(String name , int age){
        System.out.printf("Happy birthday to you %s!\n", name);
        System.out.println("Happy birthday dear you!");
        System.out.printf("You are %d years old!\n", age );
        System.out.println("Happy birthday to you!\n");
    }
}