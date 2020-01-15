/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    //  private Stack previousRooms; moet er wel in
    /**
     * Create the game and initialise its internal map.
     */
    public static void main(String[] args) {
        
    }
    
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room White_House, Black_Villa, Trump_Tower, Akons_Cellar, Statue_Of_Liberty, The_Sfinx;

        // create the rooms
        White_House = new Room("in the home of president Trump, The White House.");
        Black_Villa = new Room("in your own home, The Black Villa.");
        Trump_Tower = new Room("in one of many properties owned by president Trump, The Trump Tower.");
        Akons_Cellar = new Room("In the cellar of one of your many properties.");
        Statue_Of_Liberty = new Room("at the Statue of Liberty, a great statue representing freedom");
        The_Sfinx = new Room("at the Sfinx, a great statue representing communism");

        // initialise room exits
        White_House.setExit("north", Trump_Tower);
        White_House.setExit("east", Black_Villa);
        White_House.setExit("south" , Statue_Of_Liberty);

        Black_Villa.setExit("north", Akons_Cellar);
        Black_Villa.setExit("west", White_House);
        Black_Villa.setExit("south" , The_Sfinx);

        Trump_Tower.setExit("south", White_House);
        Trump_Tower.setExit("east", Akons_Cellar);

        Akons_Cellar.setExit("south", Black_Villa);
        Akons_Cellar.setExit("west", Trump_Tower);

        Statue_Of_Liberty.setExit("north", White_House);
        Statue_Of_Liberty.setExit("east", The_Sfinx);

        The_Sfinx.setExit("north", Black_Villa);
        The_Sfinx.setExit("west", Statue_Of_Liberty);

        currentRoom = Black_Villa;  // start game at black villa
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Hello !");
        System.out.println("You'll be playing as the president of the fictional country Bambicules. ");
        System.out.println("Your name is Aliaune Damala Bouga Time Puru Nacka Lu Lu Lu Badara Akon Thiam. Or in short, Akon.");
        System.out.println("Your goal is to extradite president Trump from the United States of America.");
        System.out.println("You can do this by moving from location to location. This can be done by typing 'go' and a cardinal direction like 'north'. ");
        System.out.println("Good luck and have fun!");
        System.out.println();
        System.out.println("For more help, type 'help'.");
        System.out.println();
        System.out.println("You'll start at your own home.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
            System.out.println("I don't know what you mean...");
            break;

            case HELP:
            printHelp();
            break;

            case GO:
            goRoom(command);
            break;

            case QUIT:
            wantToQuit = quit(command);
            break;

            case BACK:
            goBack(command);
            break;
            
            case ABOUT:
            about(command);
            break;

            
            //moet nog case voor back bij!
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("Try to extradite Trump from the U.S.");
        System.out.println("You can do this by traveling to places to see if something is going on there.");
        System.out.println("You can do this by moving from location to location. This can be done by typing 'go' and a cardinal direction like 'north'. ");
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Fill in a cardinal direction i.e. north.");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is nothing there, try another route.");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("To quit, type 'quit'");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    private void goBack(Command command) //   moet er ook wel in
    {
        System.out.println("Hier moet de code om terug te gaan!");
    }
    
    private void about(Command command) //   moet er ook wel in
    {
        System.out.println("Dit spel is gemaakt door Teun en Stijn, wij hebben hier met veel plezier aan gewerkt :) ");
    }
}

