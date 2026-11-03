public class PrintfMethod{
    public static void main(String[]args){

        String name = "Muhammadziyo";
        char firstLetter = 'M';
        int age = 33;
        double height = 188;
        boolean isEmployed = true;

        System.out.printf("Your name is %s\n", name);
        System.out.printf("The first Letter is %c\n", firstLetter);
        System.out.printf("You are %d years old\n",age);
        System.out.printf("Your are %.1f cm\n", height);
        System.out.printf("Employed: %b\n", isEmployed);
    }
}