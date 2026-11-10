public class JavaScope {
    public static void main(String[] args) {

        {
            int a = 1;
            System.out.println(a);
        }
        int a = 5;
        System.out.println(a);
    }
}