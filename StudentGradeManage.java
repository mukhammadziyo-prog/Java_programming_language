import java.util.Scanner;
import java.util.Arrays;
public class StudentGradeManage {

    static String name = "Not Included";
    static int age = 0;
    static int[] grade = { };
    static double average = 0;

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("======= Student Grade Manager =======\n");

            boolean programIsRunning = true;
            while(programIsRunning){

                int readOption = showMenu(scanner);
                switch(readOption) {
                    case 1:
                        enterStudentInformation(scanner);
                        break;

                    case 2:
                        showStudentInformation();
                        break;

                    case 3:
                        calculateAverageGrade(scanner);
                        break;

                    case 4:
                        maxGrade();
                        break;

                    case 5:
                        minGrade();
                        break;

                    case 6:
                        checkPassOrFail();
                        break;

                    case 7:
                        System.out.println("===== Goodbye =====");
                        programIsRunning = false;
                        break;

                        default:
                        System.out.println("Please, enter a valid choice!\n");
                }
            }


        scanner.close();
    }

    static int showMenu(Scanner scanner){
        System.out.println("1. Enter student information");
        System.out.println("2. Show student information");
        System.out.println("3. Calculate average grade");
        System.out.println("4. Show highest grade");
        System.out.println("5. Show lowest grade");
        System.out.println("6. Check pass/fail");
        System.out.println("7. Exit\n");

        System.out.print("Choose an option: ");
        int option = scanner.nextInt();
        scanner.nextLine();
        return option;
    }

    static void enterStudentInformation(Scanner scanner2){

    double sum = 0;

        System.out.println("======= Enter Student Information =======");
        System.out.print("Please Enter Name: ");
        name = scanner2.nextLine();
        System.out.print("Please Enter Age: ");
        age = scanner2.nextInt();
        System.out.print("Enter Number of subjects: ");
        int subjectsCount = scanner2.nextInt();

            grade = new int[subjectsCount];

            for(int i = 0;i < subjectsCount;i++) {
                System.out.print((i + 1) + "-subject grade (1-100): ");
                int inputGrade = scanner2.nextInt();


                while (inputGrade < 0 || inputGrade > 100){
                    System.out.println("invalid Grade");
                    System.out.print("Enter grade again: ");
                    inputGrade = scanner2.nextInt();
                }
                grade[i] = inputGrade;
            }
            average = 0;
        System.out.println("Information has been saved!\n");
    }

    static void showStudentInformation(){

        System.out.println("===== Student Information =====");
        System.out.println("Name: "+ name);
        System.out.println("Age: "+ age);
        System.out.println("Grade: "+ Arrays.toString(grade));
        System.out.println("---------------------------\n");

    }

    static void calculateAverageGrade(Scanner scanner3){
        System.out.println("===== Calculate Average Grade =====");

        if(grade.length == 0) {
            System.out.print("No Grades Available. Please Enter Student Information First!\n\n");
            return;
        }
        double sum = 0;
        for(int i = 0; i < grade.length;i++){
            sum = sum + grade[i];
        }
        average = sum / grade.length;
        System.out.printf("The average grade is: %.2f\n", average);
        System.out.println("-----------------------------------\n");

    }
    static void maxGrade(){

        if(grade.length == 0){
            System.out.println("No grades available. Please Enter Student information first!\n");
            return;
        }

        int max = grade[0];

            for(int numMax : grade){
                if(numMax > max) {
                    max = numMax;
                }
            }
        System.out.printf("The highest grade is: %d\n\n",max);
    }

    static void minGrade(){
        if(grade.length == 0){
            System.out.println("No grades available. Please Enter Student information first!\n");
            return;
        }

        int min = grade[0];
        for(int numMin : grade){
            if(numMin < min){
                min = numMin;
            }
        }
        System.out.printf("The lowest grade is: %d\n\n",min);
    }

    static void checkPassOrFail(){
        System.out.println("======= Check Pass/Fail =======");
        if(grade.length == 0){
            System.out.println("No grades available. Please Enter Student Information First!\n");
            return;
        }
        if(average == 0){
            double sum = 0;
            for(int g : grade){
                sum = sum + g;
            }
            average = sum / grade.length;
        }
        System.out.printf("Current average grade is: %.2f\n", average);

        if(average >= 60){
            System.out.println("Result: PASSED!\n");
        } else {
            System.out.println("Result: FAILED\n");
        }
        System.out.println("---------------------------\n");
    }

}