import java.util.InputMismatchException;
import java.util.Scanner;

public class MainInterface {

    public void mainMenu(){
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("|                   WELCOME TO CAFETERIA ORDERING SYSTEM                  |");
        System.out.println("--------------------------------------------------------------------------");
        System.out.println();
        System.out.println("Before proceed, please choose from the following: ");
        System.out.println();
        System.out.println(String.format("%-15s", "1. Admin")+String.format("%-15s", "2. Customer"));
        System.out.println(String.format("%-15s", "3. Vendor")+String.format("%-15s", "4. Runner"));
        System.out.println();
        System.out.println("Please select to log in.");
    }

    public int userInput(){
        Scanner input = new Scanner(System.in);
        boolean status = true;
        int choice=0;

        while(status){
            System.out.print("Enter the number:");
            try {
                choice = input.nextInt();
                status = false;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid input.");
                status = true;
            }
        }
        return choice;
    }

    public void exitApplication(){
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("|              THANK YOU FOR USING CAFETERIA ORDERING SYSTEM             |");
        System.out.println("--------------------------------------------------------------------------");
    }
}
