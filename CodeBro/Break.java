public class Break{
    public static void main(String[]args){


                // this is combining break and continue
        int[] numbers = {3,-1,7,0,9};

        for(int i = 0;i<6;i++){
            if(i == 2){
                continue;
            }
            if(i == 4){
                break;
            }
            System.out.println(i);
        }
    }
}


/*

This example stops the loop when i is equal to 4:
for (int i = 0; i < 10; i++) {
  if (i == 4) {
    break;
  }
  System.out.println(i);
}





Continue
The continue statement breaks one iteration (in the loop), if a specified condition occurs, and continues with the next iteration in the loop.

This example skips the value of 4:


for (int i = 0; i < 10; i++) {
  if (i == 4) {
    continue;
  }
  System.out.println(i);
}


 */