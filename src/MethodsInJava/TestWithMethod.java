import java.util.Scanner;
public class TestWithMethod{
    public static void main(String[]args){
        Scanner scanner = new Scanner(System.in);

        System.out.println("===== Welcome =====");

        int weekDay;
        System.out.print("Enter weekday (1-7): ");
        weekDay = scanner.nextInt();

        switchWeekDay(weekDay);
        myPlan(weekDay);

        scanner.close();
    }
    static void switchWeekDay(int weekDay){
        switch(weekDay){
            case 1: System.out.println("Monday"); break;
            case 2: System.out.println("Tuesday"); break;
            case 3: System.out.println("Wednesday"); break;
            case 4: System.out.println("Thursday"); break;
            case 5: System.out.println("Friday"); break;
            case 6: System.out.println("Saturday"); break;
            case 7: System.out.println("Sunday"); break;

            default: System.out.println("Please, enter valid weekday!");
        }
    }


    static void myPlan(int weekDay){
        if(weekDay == 1){
            System.out.println("Your Plan is: Going to University");
        } else if (weekDay == 2){
            System.out.println("Your Plan is: Playing Chess");
        } else if (weekDay == 3) {
            System.out.println("Your Plan is: Playing Football");
        } else if (weekDay == 4){
            System.out.println("Your plan is: Meeting with friends");
        } else if (weekDay == 5){
            System.out.println("Your Plan is: Reading books");
        } else if (weekDay == 6) {
            System.out.println("Your Plan is: Cooking");
        } else if (weekDay == 7){
            System.out.println("Your Plan is: House works, Have a nice day!");
        }
    }
}