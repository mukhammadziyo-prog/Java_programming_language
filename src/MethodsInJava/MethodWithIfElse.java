public class MethodWithIfElse {

    static void checkAge(int age){

        if(age >= 18){
            System.out.println("Your old is enough to vote");
        } else {
            System.out.println("Your age is not enough to vote. Please be back when you you 18");
        }
    }
    public static void main (String[] args){
        checkAge(30);
    }
}