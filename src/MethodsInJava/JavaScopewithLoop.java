public class JavaScopewithLoop {
    public static void main(String[] args){

        for (int i = 0;i <= 5;i++){
            System.out.println(i);
        }
        System.out.println();
        for (int i = 0;i <=5;i++){ // i is accessible here
            System.out.println(i);
        }
        // i is not accessible here

        //The for loop has its own block ({ ... }).
        //The variable i declared in the loop header (int i = 0) is only accessible inside that loop block.
                //Once the loop ends, i is destroyed, so you can't use it outside.
    }
}