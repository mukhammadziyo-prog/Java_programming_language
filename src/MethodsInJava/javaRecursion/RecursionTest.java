public class RecursionTest {

    static void hello(int count){
        System.out.println("Hello Muhamamdziyo");

        if(count <= 1){
            return;
        }
        hello(count - 1);
    }

    public static void main(String[] args){
        hello(3);
    }
}