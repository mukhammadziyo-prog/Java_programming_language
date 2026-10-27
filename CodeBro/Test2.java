public class Test2 {
    public static void main(String[] args){

        boolean isStudent = true;
        boolean isSenior = true;
        double price = 9.99;

        if(isStudent){
            if(isSenior) {
                System.out.println("You got a senior discount of 20%");
                System.out.println("You got a student discount of 10%");
                price *= 0.7;
            }
            else {
                System.out.println("You got a student discount of 10%");
                price *= 0.9;
            }
        } else {
                if(isSenior){
                    System.out.println("You got a senior discount of 20%");
                    price *= 0.8;
                }
                else {
                    price *= 1;
                }
        }
        System.out.println("The price of a ticket is: $" + price);
    }
}