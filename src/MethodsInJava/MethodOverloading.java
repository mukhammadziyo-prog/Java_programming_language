public class MethodOverloading {
    public static void main(String[] args){

        int sum = myMethod(56,99);
        double result = myMethod(55.4,77.3);
        System.out.println(result);
        System.out.println(sum);





    }
    static int myMethod(int a , int b){
        return a + b;
    }
    static double myMethod(double num1 , double num2){
        return num1 + num2;
    }
}

