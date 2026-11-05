public class MultiDimensionalArray{
    public static void main(String[]args){

        String[][] cars = new String[3][3];

        cars[0][0] = "Camaro";
        cars[0][1] = "Tesla";
        cars[0][2] = "Corvette";

        cars[1][0] = "Byd";
        cars[1][1] = "Mustang";
        cars[1][2] = "Ferrari";

        cars[2][0] = "Lambo";
        cars[2][1] = "Ford";
        cars[2][2] = "Genesys";

        for(int i = 0;i< cars.length;i++){
            for(int j = 0;j < cars[i].length;j++){
                System.out.print(cars[i][j] + " ");
            }
            System.out.println();
        }
    }
}