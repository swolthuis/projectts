import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.
 * 
 * This class holds the menu of the game
 * It is used the menu to give the player options what to do
 *
 * @author  Stijn Wolthuis en Teun de Jong
 * @version 1
 */
public class Menu {

    private Game game;

    boolean exit;

    public static void main(String[] args) {
        Menu menu = new Menu();
        Game game = new Game();
        menu.runMenu();

    }

    /**
     * Runs the menu 
     * @author Stijn Wolthuis
     */
    public void runMenu() {
        printHeader();
        while (!exit) {
            printMenu();
            int choice = getMenuChoice();
            performAction(choice);
        }
    }

    /**
     * Prints the menu header
     * @author Stijn Wolthuis
     */
    private void printHeader() {
        System.out.println("+-----------------------------------+");
        System.out.println("|           TRUMPNATION             |");
        System.out.println("|           GO GET TRUMP!           |");
        System.out.println("+-----------------------------------+");
    }

    /**
     * Prints a text with the options to choose
     * @author Stijn Wolthuis
     */
    private void printMenu() {
        displayHeader("Please make a selection, by typing the number");
        System.out.println("1) Play");
        System.out.println("2) About");
        System.out.println("0) Exit");
    }
    /**
     * Checks if the input is correct
     * @author Stijn Wolthuis
     */
    private int getMenuChoice() {
        Scanner keyboard = new Scanner(System.in);
        int choice = -1;
        do {
            System.out.print("Enter your choice: ");
            try {
                choice = Integer.parseInt(keyboard.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid selection. Choose between 1, 2 and 0.");
            }
            if (choice < 0 || choice > 2) {
                System.out.println("That number is not listed, try again");
            }
        } while (choice < 0 || choice > 2);
        return choice;
    }
    /**
     * Procceses the input if 0 it quits
     * if 1 starts the game
     * if 2 prints the about page
     * @author Stijn Wolthuis
     */
    private void performAction(int choice) {
        Game game = new Game();
        switch (choice) {
            case 0:
            System.out.println("Thanks for playing, see you next time.");
            System.exit(0);
            break;
            case 1:
            game.play();
            //System.exit(0);

            break;
            case 2:
            System.out.println("");
            System.out.println("About: Trumpnation ");
            System.out.println("Authors: Stijn Wolthuis & Teun de Jong ");
            System.out.println("ITV1H");
            System.out.println("2020");
            System.out.println("");
            break;

        }
    }
    /**
     * Checks the input if it has a corresponding number if so gives back response
     * else it will say to try again
     * @author Stijn Wolthuis
     * @param String question, List<String> answers
     * @return String response 
     */
    private String askQuestion(String question, List<String> answers) {
        String response = "";
        Scanner keyboard = new Scanner(System.in);
        boolean choices = ((answers == null) || answers.size() == 0) ? false : true;
        boolean firstRun = true;
        do {
            if (!firstRun) {
                System.out.println("Invalid selection. Please try again.");
            }
            System.out.print(question);
            if (choices) {
                System.out.print("(");
                for (int i = 0; i < answers.size() - 1; ++i) {
                    System.out.print(answers.get(i) + "/");
                }
                System.out.print(answers.get(answers.size() - 1));
                System.out.print("): ");
            }
            response = keyboard.nextLine();
            firstRun = false;
            if (!choices) {
                break;
            }
        } while (!answers.contains(response));
        return response;
    }
    /**
     * 
     * @author Stijn Wolthuis
     * @param String message
     */
    private void displayHeader(String message){
        System.out.println();
        int width = message.length() + 6;
        StringBuilder sb = new StringBuilder();
        sb.append("+");
        for(int i = 0; i < width; ++i){
            sb.append("-");
        }
        sb.append("+");
        System.out.println(sb.toString());
        System.out.println("|   " + message + "   |");
        System.out.println(sb.toString());
    }

}