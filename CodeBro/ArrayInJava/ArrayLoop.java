public class ArrayLoop{
    public static void main(String[] args){

        int[] numbers = {12,33,44,2,34,5};
        int sum = 0;
        for(int i = 0;i < numbers.length;i++){
            sum += numbers[i];
        }
        System.out.printf("The sum is: %d ",sum);
    }
}