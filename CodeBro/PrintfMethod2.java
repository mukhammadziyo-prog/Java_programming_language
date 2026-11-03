public class PrintfMethod2 {
    public static void main (String[] args){

        //width

        int id1 = 1;
        int id2 = 23;
        int id3 = 456;
        int id4 = 7890;

        System.out.printf("%d\n",id1);
        System.out.printf("%d\n",id2);
        System.out.printf("%d\n",id3);
        System.out.printf("%d\n\n",id4);


        int id5 = 1;
        int id6 = 23;
        int id7 = 456;
        int id8= 7890;

        System.out.printf("%04d\n",id5);
        System.out.printf("%04d\n",id6);
        System.out.printf("%04d\n",id7);
        System.out.printf("%04d\n\n",id8);


        int id9 = 1;
        int id10 = 23;
        int id11 = 456;
        int id12 = 7890;

        System.out.printf("%4d\n",id9);
        System.out.printf("%4d\n",id10);
        System.out.printf("%4d\n",id11);
        System.out.printf("%4d\n\n",id12);


        int id13 = 1;
        int id14 = 23;
        int id15 = 456;
        int id16 = 7890;

        System.out.printf("%-4d\n",id13);
        System.out.printf("%-4d\n",id14);
        System.out.printf("%-4d\n",id15);
        System.out.printf("%-4d\n",id16);

    }
}