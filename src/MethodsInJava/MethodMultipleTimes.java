// a method can also be called multiple times.

public class MethodMultipleTimes {
    static void multipleTimes(){
        System.out.println("I just got executed");
    }
    public static void main(String[] args){
        multipleTimes();
        multipleTimes();
        multipleTimes();
    }
}