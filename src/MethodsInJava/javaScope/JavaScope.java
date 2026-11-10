public class JavaScope {
    public static void main(String[] args) {

        {
            int a = 1;
            System.out.println(a);
            System.out.println();
        }
        int a = 5;
        System.out.println(a);
        System.out.println();
        {
            for(int i = 0; i <=5; i++){
                System.out.println(i);
            }
            System.out.println();
        }
        for(int i = 0;i<=10;i++){
            System.out.println(i);
        }
    }
}