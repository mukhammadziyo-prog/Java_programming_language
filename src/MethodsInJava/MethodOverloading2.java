public class MethodOverloading2 {
    public static void main(String[] args){
        int sum = plusMethodint(56 , 55 , 5);
        double result = plusMethodDouble(45 , 9.8 , 5.3);

        System.out.println("The sum of Int: " + sum);
        System.out.printf("The sum of Double: %.2f" ,result);

    }

    static int plusMethodint(int a , int b){
        return a * b;

    }
    static int plusMethodint(int a , int b, int c) {
        return a * b + c;

    }

    static double plusMethodDouble(double num1 , double num2){
        return num1 * num2;
    }
    static double plusMethodDouble(double num1 , double num2 , double num3){
        return num1 * num1 * num3;
    }
}