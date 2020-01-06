/**
 * "GetTrump".
 * The meaning of the game is to get the U.S. to extradite Trump from the U.S.
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Stijn Wolthuis en Teun de Jong
 * @version 1.0
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;

    /**
     * Create the game and initialise its internal map.
     */
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
        Room WhiteHouse, BlackVilla, TrumpTower, Cellar, StatueOfLiberty, TheSfinx;

        // create the rooms
        WhiteHouse = new Room("The home of president Trump");
        BlackVilla = new Room("The home of president Aliaune Damala Bouga Time Puru");
        TrumpTower = new Room("One of many properties owned by president Trump");
        Cellar = new Room("in a computing lab");
        StatueOfLiberty = new Room("A great statue representing freedom");
        TheSfinx = new Room("A great statue representing democracy");

        // initialise room exits
        WhiteHouse.setExit("north", TrumpTower);
        WhiteHouse.setExit("east", BlackVilla);
        WhiteHouse.setExit("south" , StatueOfLiberty);

        BlackVilla.setExit("north", Cellar);
        BlackVilla.setExit("west", WhiteHouse);
        BlackVilla.setExit("south" , TheSfinx);

        TrumpTower.setExit("south", WhiteHouse);
        TrumpTower.setExit("east", Cellar);

        Cellar.setExit("south", BlackVilla);
        Cellar.setExit("west", TrumpTower);

        StatueOfLiberty.setExit("north", WhiteHouse);
        StatueOfLiberty.setExit("east", TheSfinx);

        TheSfinx.setExit("north", BlackVilla);
        TheSfinx.setExit("west", StatueOfLiberty);

        //outside.setExit("east", theater);
        //outside.setExit("south", lab);
        //outside.setExit("west", pub);

        //theater.setExit("west", outside);

        //pub.setExit("east", outside);

        //lab.setExit("north", outside);
        //lab.setExit("east", office);

        //office.setExit("west", lab);

        //currentRoom = outside;  // start game outside
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
        System.out.println("Welcome!");
        System.out.println("You'll be playing the president of the fictional country Bambicules, ");
        System.out.println("called Aliaune Damala Bouga Time Puru Nacka Lu Lu Lu Badara Akon Thiam");
        System.out.println("Your goal will be to extradite president Trump from the U.S. of America.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        //System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        // else command not recognised.
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
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
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
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
