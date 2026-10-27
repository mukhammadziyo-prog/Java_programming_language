import java.util.Scanner;
public class test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Your Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your age: ");
        int age = scanner.nextInt();

        System.out.println("Are you a student(True/False): ");
        boolean isStudent = scanner.nextBoolean();
        if(name.isEmpty()){
            System.out.println("You did not enter your name 😡");
        } else {
            System.out.println("Hello "+ name +"!");
        }

        //group 2
        if(age >= 65){
            System.out.println("You are a senior 🧓");
        }
        else if(age >=18) {
            System.out.println("You are an adult 🧑");

        } else if(age < 0){
            System.out.println("You have not been born yet 🤰");
        }

        else if (age == 0){
            System.out.println("You are a baby 🐣");
        }
        else {
            System.out.println("You are a child");
        }


        if(isStudent){
            System.out.println("You are a student😊");
        }else{
            System.out.println("You are not a student🤦‍♂️");
        }

        scanner.close();
    }
}