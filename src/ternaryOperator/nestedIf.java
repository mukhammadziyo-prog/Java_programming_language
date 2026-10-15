public class nestedIf{
    public static void main(String[]args) {

        int ball = 91;

        if(ball > 50) {
            System.out.println("Siz imtihondan ottingiz!");

            if(ball >= 90)
                System.out.println("Siz grand yuttingiz!");

        } else {
            System.out.println("imtihondan ota olmadingiz");
        }
    }
}