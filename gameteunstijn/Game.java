
import java.util.*;
/**
 *  This class is the main class of the "Trumpnation" application. 
 *  "Trumpnation" is a simple, text based adventure game.  Users 
 *  can walk around the world and pick up items. That's all. The goal is 
 *  to extradite Trump.
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Stijn Wolthuis & Teun de Jong
 * @version 2020.01.10
 */ 
public class Game 
{
    private final Parser parser;
    private Room currentRoom;
    private String kamer;
    private Stack<Room> prevLocation;
    private HashMap <String,Item> inventory = new HashMap<String, Item>();
    private Timer timer;
    private Menu menu;
    /**
     * Create the game and initialise its internal map.
     */
    public static void main(final String[] args) {
        new Menu().runMenu();
    }

    public Game() {
        createRooms();
        parser = new Parser();
        timer = new Timer();
        menu = new Menu();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms() {
        ArrayList<Item> itemsWH = new ArrayList();
        Room White_House, Black_Villa, Trump_Tower, Akons_Cellar, Statue_Of_Liberty, The_Sfinx, Secret_Room;

        // create the rooms
        White_House = new Room("in the home of president Trump, The White House.");
        Black_Villa = new Room("in your own home, The Black Villa.");
        Trump_Tower = new Room("in one of many properties owned by president Trump, The Trump Tower.");
        Akons_Cellar = new Room("In the cellar of one of your many properties.");
        Statue_Of_Liberty = new Room("at the Statue of Liberty, a great statue representing freedom");
        The_Sfinx = new Room("at the Sfinx, a great statue representing communism");
        Secret_Room = new Room("Trapped, you have no idea where you are");
        //System.out.println(White_House.getItems()   );

        // initialise room exits
        White_House.setExit("north", Trump_Tower);
        White_House.setExit("east", Black_Villa);
        White_House.setExit("south", Statue_Of_Liberty);

        Black_Villa.setExit("north", Akons_Cellar);
        Black_Villa.setExit("west", White_House);
        Black_Villa.setExit("south", The_Sfinx);

        Trump_Tower.setExit("south", White_House);
        Trump_Tower.setExit("east", Akons_Cellar);

        Akons_Cellar.setExit("south", Black_Villa);
        Akons_Cellar.setExit("west", Trump_Tower);

        Statue_Of_Liberty.setExit("north", White_House);
        Statue_Of_Liberty.setExit("east", The_Sfinx);

        The_Sfinx.setExit("north", Black_Villa);
        The_Sfinx.setExit("west", Statue_Of_Liberty);

        //initialise items
        White_House.setItem(new Item("key", 5, "Wonder where it brings me"));
        White_House.setItem(new Item("book", 10, "50 Shades Of Grey"));
        White_House.setItem(new Item("poster", 3, "Obama poster, Trump must secretly really like him"));

        Black_Villa.setItem(new Item("picture", 3, "It shows me and my friends after we raided area 51"));
        Black_Villa.setItem(new Item("car", 1000, "Fiat Multipla"));

        Trump_Tower.setItem(new Item("souvenir", 3, "A small statue of the Trump Tower."));

        Akons_Cellar.setItem(new Item("tape", 5, "Esed for tying garmets, binding seams or carpets etc. "));

        Statue_Of_Liberty.setItem(new Item("rope", 5, "Maybe we can use this to tie something or someone down"));

        currentRoom = Black_Villa; // start game at black villa
    }

    /**
     * Main play routine. Loops until end of play.
     */
    public void menu(){
        menu.runMenu();
    }

    public void play() {
        printWelcome();
        prevLocation = new Stack();
        
        //timer.run();
        // Enter the main command loop. Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (!finished) {
            final Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Hello and welcome to Trumpnation!");
        System.out.println("You'll be playing as the president of the fictional country Bambicules. ");
        System.out.println(
            "Your name is Aliaune Damala Bouga Time Puru Nacka Lu Lu Lu Badara Akon Thiam. Or in short, Akon.");
        System.out.println("Your goal is to extradite (or kidnap) president Trump from the United States of America.");
        System.out.println(
            "You can do this by moving from location to location. This can be done by typing 'go' and a cardinal direction like 'north'. ");
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
     * 
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(final Command command) {
        boolean wantToQuit = false;

        final CommandWord commandWord = command.getCommandWord();

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

            case LOOK:
            look(command);
            break;

            case TAKE:
            getItem(command);
            break;

            case DROP:
            dropItem(command);
            break;

            case INVENTORY:
            printInventory();
            break;

            case TIME:
            timer.getTime();
            break;

        }
        return wantToQuit;
    }

    /**
     * Print out some help information. Here we print some stupid, cryptic message
     * and a list of the command words.
     */
    private void printHelp() {
        System.out.println("Try to extradite Trump from the U.S.");
        System.out.println("You can do this by traveling to places to see if something is going on there.");
        System.out.println("Travel from location to location and pick up items you deem necessary for your adventure"); 
        System.out.println("To get you started, type 'go' and a cardinal direction e.g. 'north'.");
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /**
     * Try to go in one direction. If there is an exit, enter the new room,
     * otherwise print an error message.
     */
    private void goRoom(final Command command) {

        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Fill in a cardinal direction i.e. north.");
            return;
        }

        final String direction = command.getSecondWord();

        // Try to leave current room.
        final Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is nothing there, try another cardinal direction.");
        } else {
            prevLocation.push(currentRoom);
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }

    }

    public
    void finalRoom(Command command){
        
        String output = "";
        for(String itemName : inventory.keySet()){
            output += inventory.get(itemName).getDescription() + " Weight: " + inventory.get(itemName).getWeight() + "\n";
        }
       
        if(output.contains("Book, Poster, Weird key"))
        {
            System.out.println("KAAS");
        }
    }

    /**
     * "Quit" was entered. Check the rest of the command to see whether we really
     * quit the game.
     * 
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(final Command command) {
        if (command.hasSecondWord()) {
            System.out.println("To quit, type 'quit'");
            return false;
        } else {
            return true; // signal that we want to quit
        }
    }

    private void goBack(final Command command) {
        {
            if (prevLocation.empty()) {
                System.out.println("You can't go back any further");
            } else {
                currentRoom = prevLocation.pop();
                System.out.println(currentRoom.getLongDescription());

            }
        }
    }

    private String LookPrepare() {
        kamer = currentRoom.getLongDescription();
        return kamer;
    }

    private void look(final Command command) 
    {
        if(LookPrepare().contains(("in the home of president Trump, The White House")))
        {
            System.out.println("                    _ _.-'`-._ _");
            System.out.println("                   ;.'________'.;");
            System.out.println("        _________n.[____________].n_________");
            System.out.println("       |''_''_''_''||==||==||==||''_''_''_'']");
            System.out.println("       |LI LI LI LI||LI||LI||LI||LI LI LI LI|");
            System.out.println("       |.. .. .. ..||..||..||..||.. .. .. ..|");
            System.out.println("       |LI LI LI LI||LI||LI||LI||LI LI LI LI|");
            System.out.println("    ,,;;,;;;,;;;,;;;,;;;,;;;,;;;,;;,;;;,;;;,;;,,");
            System.out.println("   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
            System.out.println("Currently I'm at the White House, there are several items I could try to interact with.");
            System.out.println("");
        }else if(LookPrepare().contains("in your own home, The Black Villa")){
            System.out.println("I'm home, maybe there are some interesting items I could take with me." );

        }else if(LookPrepare().contains("in one of many properties owned by president Trump, The Trump Tower")){
            System.out.println("I'm at the Trump Tower, there are a few interesting things I see.");
            System.out.println("There is a big door that says DONALD ONLY, I wonder where that would bring me.");
            System.out.println("Unfortunately it's locked, for now...");

        }else if(LookPrepare().contains("In the cellar of one of your many properties")){
            System.out.println("A cellar, that would be a great place for Trump.");
            System.out.println("For now, this isn't the most interesting of places.");

        }else if(LookPrepare().contains("at the Statue of Liberty, a great statue representing freedom")){
            System.out.println("There is a big statue in front of me, personally, I don't see why this is such a big deal.");
            System.out.println("They should see The Great Sfinx build for me.");

        }else if(LookPrepare().contains("at the Sfinx, a great statue representing communism")){
            System.out.println("Now this, this is a big, amazing, fancy, great, cool, fabulous, titanic, remarkable"); 
            System.out.println("superior, noble, outstanding, glorious, prominent, renowned statue.");
            System.out.println("I wonder if there are more interesting things here");

        }else{
            System.out.println("That`s weird I can`t see anything, perhaps I should try again later");
        }
    }

    private void getItem(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to pickup..
            System.out.println("Get what?");
            return;
        }

        String itemName = command.getSecondWord();
        Item newItem = currentRoom.getItem(itemName);

        if(newItem == null){
            System.out.println("There is no item in this room");
        }
        else{
            inventory.put(itemName, newItem);
            currentRoom.removeItem(itemName);
            System.out.println("Picked up: " + itemName);   
            if (getTotalWeight() > 50   ){
                System.out.println("I can't carry that!");
                inventory.remove(itemName);
                currentRoom.setItem(newItem);
            }
            else
            {
                System.out.println(" New inventory weight: " + getTotalWeight());
            }
        }
    }

    private void dropItem(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to drop...
            System.out.println("Wait a minute drop what?");
            return;
        }

        String itemName = command.getSecondWord();
        Item newItem = inventory.get(itemName);
        if(newItem == null){
            System.out.println("You cant drop something you dont have");
        }
        else{
            inventory.remove(itemName);
            currentRoom.setItem(newItem);
            System.out.println("Dropped: " + itemName); 
            System.out.println("New inventory weight: " + getTotalWeight());
        }
    }

    private void printInventory(){
        String output = "";
        for(String itemName : inventory.keySet()){
            output += inventory.get(itemName).getName()+ " : " + inventory.get(itemName).getDescription() + "--- Weight: " + inventory.get(itemName).getWeight() + "\n";
        }
        System.out.println("you are carrying:");
        System.out.println(output);
    }

    private int getTotalWeight(){
        int output = 0;
        for(String itemName : inventory.keySet()){
            output = output + inventory.get(itemName).getWeight();
        }
        return output;
    }

}