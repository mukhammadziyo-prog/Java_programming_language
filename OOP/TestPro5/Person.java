public class Person{
    private String name;
    private int age;

    public Person(String name , int age){
        this.name = name;
        this.age = age;
    }

    public String introduce(){
        return "Hi, my name is " + name + " I am " + age + " years old";
    }

}

    class Main{
    public static void main(String[]args){

        Person person = new Person("Muhamamdziyo" , 19);
        System.out.println(person.introduce());
    }
}