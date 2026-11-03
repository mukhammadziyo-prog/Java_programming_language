public class Array2{
    public static void main(String[] args){

        String[] name = {"Ramziddin", "MuhammadAmin", "Suhrob","Bekzod","Abdulloh","Muhammadziyo","Jasur"};

        for(int i = 0;i < name.length;i++) {
            System.out.println(name[i]);
        }

        System.out.println("");
        MyLoop();
    }

    static void MyLoop(){

        String[] ismlar = {"Ramziddin", "MuhammadAmin", "Suhrob","Bekzod","Abdulloh","Muhammadziyo","Jasur"};
        for(String ism : ismlar){
           // Bu yerda : belgisi o'zbekchada "ichidagi har bir" degan ma'noni anglatadi.

            System.out.println(ism);
        }
    }
}