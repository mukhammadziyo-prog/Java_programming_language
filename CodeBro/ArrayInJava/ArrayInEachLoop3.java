// Create a program that keeps track of the highest and lowest values in an array:

public class ArrayInEachLoop3 {
    public static void main(String[] args){

        int[] numbers = {34,1,656,45,33,776,49};

        int max = numbers[0];
        int min = numbers[0];

        for(int number : numbers){
            if(number > max) {
                max = number;
            }
            if(number < min) {
                min = number;
            }
        }
        System.out.println("The max value is: " + max);
        System.out.println("The min value is: " + min);
    }
}