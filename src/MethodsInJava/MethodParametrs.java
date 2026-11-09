public class MethodParametrs {
    public static void main(String[] args){

        multiParametr("Muhammadziyo", 33 , true);

    }

    static void multiParametr(String name , int age , boolean isStudent){
        System.out.println(name + " "+ age + " years old");
        if(isStudent){
            System.out.println(name + " is student");
        } else {
            System.out.println(name + " is not student");
        }
    }
}