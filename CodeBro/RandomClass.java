import java.util.Random;

public class RandomClass {
    public static void main(String[]args){
        Random random = new Random();

        int number;
        int number2;
        int number3;

        number = random.nextInt(1,10 );
        number2 = random.nextInt(10,20);
        number3 = random.nextInt(20,30);


        System.out.println(number);
            if(number > 5){
                System.out.println("The number is greater than 1");
            } else {
                System.out.println("The number is less than 10");
            }

        System.out.println(number2);
            if(number2 > 15){
                System.out.println("The number is greater than 10");
            } else {
                System.out.println("The number is less than 20");
            }

        System.out.println(number3);
            if(number3 > 25){
                System.out.println("The number is greater than 20");
            } else {
                System.out.println("The number is less than 30");
            }
        }
    }

    /*
    public class Main{
    public static void main(String[] args){
        Random random = new Random()
                boolean isHeads;

        isHeads = random.nextBoolean();

        if(isHeads){
            System.out.println("HEADS");
        } else {
            System.out.println("TAILS");
        }
    }
}

     */