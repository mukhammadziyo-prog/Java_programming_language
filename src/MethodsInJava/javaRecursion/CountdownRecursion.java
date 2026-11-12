public class CountdownRecursion {
    public static void main(String[] args){

        countDown(10);


    }
    public static void countDown(int number){
        if(number > 0){
            System.out.print(number + " ");
            countDown(number - 1);
        }
    }
}