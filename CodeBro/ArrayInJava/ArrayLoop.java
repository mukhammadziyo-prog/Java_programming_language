public class ArrayLoop{
    public static void main(String[] args){

        int[] numbers = {12,33,44,2,34,5};
        int sum = 0;
        for(int i = 0;i < numbers.length;i++){
            sum += numbers[i];
        }
        System.out.printf("The sum is: %d \n",sum);


        int[] sonlar = {63,55,93,10};
        int result = 0;
            for(int son : sonlar){ // : sonlar massividagi har bir son uchun

                result += son; // yigindi = yigindi + son;
            }
        System.out.println("The result is: " + result);
    }
}