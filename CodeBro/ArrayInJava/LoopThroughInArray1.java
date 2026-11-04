//To demonstrate a practical example of using arrays, let's create a program that calculates the average of different ages:

// An array storing different ages
public class LoopThroughInArray1 {
    public static void main(String[] args) {
        int ages[] = {20, 22, 18, 35, 48, 26, 87, 70};

        float avg, sum = 0;

// Get the length of the array
        int length = ages.length;

// Loop through the elements of the array
        for (int age : ages) {
            sum += age;
        }

// Calculate the average by dividing the sum by the length
        avg = sum / length;

// Print the average
        System.out.println("The average age is: " + avg);
    }
}