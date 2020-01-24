import java.time.LocalTime;
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
    private Boolean finished;
    private int value;
    /**
     * Create the game and initialise its internal map.
     */
    public static void main(final String[] args) {
        new Menu().runMenu();
    }

    public Game() {
        this.finished = false;
        createRooms();
        parser = new Parser();
        timer = new Timer();
        menu = new Menu();
        value = -1;
    }

    /**
     * Create all the rooms and link their exits together.
     * Also sets items in the rooms.
     */
    private void createRooms() {
        ArrayList<Item> itemsWH = new ArrayList();
        Room White_House, Black_Villa, Trump_Tower, Akons_Cellar, Statue_Of_Liberty, The_Sphinx, Secret_Room;

        // create the rooms
        White_House = new Room("in the home of president Trump, The White House.");
        Black_Villa = new Room("in your own home, The Black Villa.");
        Trump_Tower = new Room("in one of many properties owned by president Trump, The Trump Tower.");
        Akons_Cellar = new Room("In the cellar of one of your many properties.");
        Statue_Of_Liberty = new Room("at the Statue of Liberty, a great statue representing freedom");
        The_Sphinx = new Room("at the Sphinx, a great statue representing communism");
        Secret_Room = new Room("in Trumps secret room");

        // initialise room exits
        White_House.setExit("north", Trump_Tower);
        White_House.setExit("east", Black_Villa);
        White_House.setExit("south", Statue_Of_Liberty);

        Black_Villa.setExit("north", Akons_Cellar);
        Black_Villa.setExit("west", White_House);
        Black_Villa.setExit("south", The_Sphinx);

        Trump_Tower.setExit("south", White_House);
        Trump_Tower.setExit("east", Akons_Cellar);
        Trump_Tower.setExit("north", Secret_Room);

        Akons_Cellar.setExit("south", Black_Villa);
        Akons_Cellar.setExit("west", Trump_Tower);

        Statue_Of_Liberty.setExit("north", White_House);
        Statue_Of_Liberty.setExit("east", The_Sphinx);

        The_Sphinx.setExit("north", Black_Villa);
        The_Sphinx.setExit("west", Statue_Of_Liberty);

        //initialise items
        White_House.setItem(new Item("key", 5, "Wonder where it brings me."));
        White_House.setItem(new Item("book", 6, "50 Shades Of Grey."));
        White_House.setItem(new Item("poster", 3, "Obama poster, Trump must secretly really like him."));
        White_House.setItem(new Item("twister", 6, "A game, usually played by kids."));
        White_House.setItem(new Item("wig", 4, "Huh, sure looks a lot like Trump's hair."));

        Black_Villa.setItem(new Item("picture", 3, "It shows me and my friends after we raided area 51"));
        Black_Villa.setItem(new Item("car", 1000, "Fiat Multipla"));
        Black_Villa.setItem(new Item("television", 70, "I bought this new televion a few months ago, it's really big."));
        Black_Villa.setItem(new Item("painting", 7, "A giant painting displaying the Swedish pop group Abba."));
        Black_Villa.setItem(new Item("mixtape", 5, "A compilation of music, typically from multiple sources, recorded onto a medium."));

        Trump_Tower.setItem(new Item("souvenir", 3, "A small keychain of the Trump Tower."));
        Trump_Tower.setItem(new Item("statue", 35, "A statue displaying Trump."));

        Akons_Cellar.setItem(new Item("tape", 5, "Used for tying garmets, binding seams or carpets etc. "));
        Akons_Cellar.setItem(new Item("glasses", 3, "Used for reading books."));

        Statue_Of_Liberty.setItem(new Item("statue_of_liberty", 10000, "The famous Statue of Liberty."));
        Statue_Of_Liberty.setItem(new Item("rope", 5, "Maybe we can use this to tie something or someone down."));
        Statue_Of_Liberty.setItem(new Item("hotdog", 5, "Probably fell out of a bun, from the hotdogstand nearby."));

        The_Sphinx.setItem(new Item("block",5 ,"Blocks are the basic units of structure in Minecraft that can be directly placed in the game world."));
        The_Sphinx.setItem(new Item("pickaxe",8 , "Pickaxes, or picks, are probably the tool that people use the most in Minecraft."));
        The_Sphinx.setItem(new Item("sandstone",5 , "Sandstone is pretty easy to find in Minecraft. Find a desert and start digging"));
        The_Sphinx.setItem(new Item("sand",3 , "Sand is a common physic-obeying block that can be found in desert biomes. "));
        The_Sphinx.setItem(new Item("diamond", 10, "Diamonds are a rare mineral obtained from diamond ore or loot chests. "));
        The_Sphinx.setItem(new Item("hoe", 5, "Hoes are tools used to till dirt and grass blocks into farmland blocks. "));

        currentRoom = Black_Villa; // start game at black villa
    }

    /**
     * Main menu.
     */
    public void menu(){
        menu.runMenu();
    }

    /**
     * Main play routine. Loops until end of play.
     */
    public void play() {
        printWelcome();
        prevLocation = new Stack();
        timer.calculator();
        // Enter the main command loop. Here we repeatedly read commands and
        // execute them until the game is over.

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
        System.out.println("Your inventory can contain a max weight of 80kg");
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
    private boolean goRoom(final Command command) {
        int value = LocalTime.now().compareTo(timer.endTime);
        while(value == 0 || value > 0){
            System.out.println("Your time is up, you will have to start all over again.");
            System.out.println("Type quit to go back to the menu.");
            return true;
        }
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Fill in a cardinal direction i.e. north.");
            return false;
        }

        final String direction = command.getSecondWord();
        // Try to leave current room.
        final Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is nothing there, try another cardinal direction.");
        } 
        else if (nextRoom.getLongDescription().contains("secret room")){
            String output = "";
            for(String itemName : inventory.keySet()){
                output += inventory.get(itemName).getName();
            }

            if(output.contains("key") && (output.contains("rope")&& (output.contains("tape"))))
            {
                System.out.println("I went through the hidden door.");
                prevLocation.push(currentRoom);
                currentRoom = nextRoom;
                System.out.println(currentRoom.getLongDescription());
                System.out.println("You hear the door close behind you.");
                System.out.println("Looks like I can't go back now.");

                return false;
            }
            else {
                System.out.println("I see a strange door but I can't open it");
                System.out.println("I will need the correct items to open this door");
            }
            //System.out.println("hier zit de secret shit");
        }

        else 
        {
            prevLocation.push(currentRoom);
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            return false;
        }

        return false;
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

    /**
     * Back command, will put the player to the previous room.
     * If there isn't a previous room it will say so.
     */

    private boolean goBack(final Command command) {
        int value = LocalTime.now().compareTo(timer.endTime);
        while(value == 0 || value > 0){
            System.out.println("Your time is up, you will have to start all over again.");
            System.out.println("Type quit to go back to the menu.");
            return true;
        }
        if(LookPrepare().contains(("secret room."))){
            System.out.println("I can't go back anymore");
            return false;
        }
        else if(prevLocation.empty()) {
            System.out.println("You can't go back any further");
            return false;
        } 
        else {
            currentRoom = prevLocation.pop();
            System.out.println(currentRoom.getLongDescription());
            
        }
        return false;
    }

    /**
     * makes a String of the current room and description.
     */
    private String LookPrepare() {
        kamer = currentRoom.getLongDescription();
        return kamer;
    }

    /**
     * The look command will check in which room the player is.
     * Then it will print what the player sees and give some extra information
     */
    private boolean look(final Command command) 
    {
        int value = LocalTime.now().compareTo(timer.endTime);
        while(value == 0 || value > 0){
            System.out.println("Your time is up, you will have to start all over again.");
            System.out.println("Type quit to go back to the menu.");
            return true;
        }
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
            System.out.println("Currently I'm at the White House, home of president Trump.");
            System.out.println("There are several items I could take with me on my adventure");
            System.out.println("Looks like Trump isn't here currently");
            System.out.println("");
        }else if(LookPrepare().contains("in your own home, The Black Villa")){
            System.out.println("                  / \\ ");
            System.out.println("                 //_\\\\");
            System.out.println("                //(_)\\\\");
            System.out.println("                 |/^\\|");
            System.out.println("       ,%%%%     // \\\\    ,@@@@@@@,");
            System.out.println("     ,%%%%/%%%  //   \\\\ ,@@@\\@@@@/@@,");
            System.out.println(" @@@%%%\\%%//%%%// === \\\\ @@\\@@@/@@@@@");
            System.out.println("@@@@%%%%\\%%%%%// =-=-= \\\\@@@@\\@@@@@@;%#####,");
            System.out.println("@@@@%%%\\%%/%%//   ===   \\\\@@@@@@/@@@%%%######,");
            System.out.println("@@@@@%%%%/%%//|         |\\\\@\\\\//@@%%%%%%#/####");
            System.out.println("'@@@@@%%\\\\/%~ |         | ~ @|| %\\\\//%%%#####;");
            System.out.println("  @@\\\\//@||   |  __ __  |    || %%||%%'######");
            System.out.println("   '@||  ||   | |  |  | |    ||   ||##\\//####");
            System.out.println("     ||  ||   | | -|- | |    ||   ||'#||###'");
            System.out.println("     ||  ||   |_|__|__|_|    ||   ||  ||");
            System.out.println("     ||  ||_/`  =======  `\\__||_._||  ||");
            System.out.println("   __||_/`      =======            `\\_||___");
            System.out.println("This is my home, a nice place out in the woods.");
            System.out.println("Perhaps there are some interesting items I could take with me." );
            System.out.println("");

        }else if(LookPrepare().contains("in one of many properties owned by president Trump, The Trump Tower")){
            System.out.println("                           ___,");
            System.out.println("                    o___.-' /");
            System.out.println("                    |      _\\_");
            System.out.println("                    |___.-'   `");
            System.out.println("                    |");
            System.out.println("                    |");
            System.out.println("            _   _   j   _   _");
            System.out.println("           [_]_[_]_[_]_[_]_[_]");
            System.out.println("           [__j__j__j__j__j__]");
            System.out.println("             [_j__j__j__j__]");
            System.out.println("             [__j__j__j__j_]");
            System.out.println("             [_j__j/V\\_j__j]");
            System.out.println("             [__j_// \\\\__j_]");
            System.out.println("             [_j__|   |_j__]");
            System.out.println("             [__j_|___|__j_]");
            System.out.println("             [_j__j__j__j__]");
            System.out.println("             [__j__j__j__j_]");
            System.out.println("  _   _   _  [_j__j__j__j__]  _   _   _   _");
            System.out.println("_[_]_[_]_[_]_[__j__j__j__j_]_[_]_[_]_[_]_[_]_");
            System.out.println("  _j__j__j__j[_j__j__j__j__]j__j__j__j__j_");
            System.out.println("     j  j  j [  j  j  j  j ] j  j  j  j    "); 
            System.out.println("I'm at the Trump Tower, there is one very interesting thing here.");
            System.out.println("There is a big door that says DONALD ONLY, I wonder where that would bring me.");
            System.out.println("Unfortunately it's locked, for now...");
            System.out.println("Perhaps if I came back with the right set of items, and tried to look again, I could try to open the door.");

            String output = "";
            for(String itemName : inventory.keySet()){
                output += inventory.get(itemName).getName();
            }

            if(output.contains("key") && (output.contains("rope")&& (output.contains("tape"))))
            {
                System.out.println("I could try opening the door now!");
                //final Room nextRoom = Secret_Room;
            }
            else {
                System.out.println("We don't know where this door leads.");
                System.out.println("I should take some items with me to make sure i'll be okay.");
                System.out.println("A key to open the door.");
                System.out.println("A rope in case I have to escape.");
                System.out.println("Some tape to help me fix things.");
            }
        }else if(LookPrepare().contains("In the cellar of one of your many properties")){
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa,");
            System.out.println("8                           8\"b,    \"Ya");
            System.out.println("8                           8  \"b,    \"Ya");
            System.out.println("8                    aaaaaaa8,   \"b,    \"Ya");
            System.out.println("8                    8\"b,    \"Ya   \"8\"\"\"\"\"\"8");
            System.out.println("8                    8  \"b,    \"Ya  8      8");
            System.out.println("8             aaaaaaa8,   \"b,    \"Ya8      8");
            System.out.println("8   A         8\"b,    \"Ya   \"8\"\"\"\"\"\"\"      8");
            System.out.println("8             8  \"b,    \"Ya  8             8");
            System.out.println("8      aaaaaa88,   \"b,    \"Ya8         B   8");
            System.out.println("8      8\"b,    \"Ya   \"8\"\"\"\"\"\"\"             8");
            System.out.println("8      8  \"b,    \"Ya  8                    8");
            System.out.println("8aaaaaa8,   \"b,    \"Ya8                    8");
            System.out.println("8\"b,    \"Ya   \"8\"\"\"\"\"\"\"                    8");
            System.out.println("8  \"b,    \"Ya  8                           8");
            System.out.println("8,   \"b,    \"Ya8                           8");
            System.out.println("\"Ya   \"8\"\"\"\"\"\"\"                           8");
            System.out.println("\"Ya  8                                  8");
            System.out.println("\"Ya8                                  8");
            System.out.println("\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"");
            System.out.println("A cellar, the only way to reach this place is by going down some stairs.");
            System.out.println("For now, this isn't the most interesting of places.");
            System.out.println("Maybe later we can stash something (or someone here)");
            System.out.println("");

        }else if(LookPrepare().contains("at the Statue of Liberty, a great statue representing freedom")){
            System.out.println(" ( ");
            System.out.println("(_)");
            System.out.println("###");
            System.out.println(" #");
            System.out.println("( c     .\\|/.");
            System.out.println(" \\/\\    wWWWw");
            System.out.println(" |  \\  (/. .\\)");
            System.out.println(" |   \\  \\ = /");
            System.out.println(" \\    \\__) (___");
            System.out.println("  `\\    \\\\ //  `\\");
            System.out.println("    `\\   \\^/  _  \\");
            System.out.println("      \\   ` //^\\  \\");
            System.out.println("       ;    \\\\  |  |");
            System.out.println("       |    .-'|-' /");
            System.out.println("       |    '--\\_.'");
            System.out.println("       |    .:|");
            System.out.println("       |    .:|");
            System.out.println("       ||    ||");
            System.out.println("       | |;.::|");
            System.out.println("       |,  .::|");
            System.out.println("       | ,| .;|");
            System.out.println("     __|__,_.:|__");
            System.out.println("    [            ]");
            System.out.println("    |            |");
            System.out.println("   _|            |_");
            System.out.println("  /=--------------=\\");
            System.out.println("There is a big statue in front of me, personally, I don't see why this is such a big deal.");
            System.out.println("They should see The Great Sfinx build for me.");
            System.out.println("");

        }else if(LookPrepare().contains("at the Sphinx, a great statue representing communism")){
            System.out.println("               ___ ");
            System.out.println("             .\"///\".");
            System.out.println("            /|<> <>!\\");
            System.out.println("           /-|  ^  !-\\");
            System.out.println("          /-- \\_=_/ --\\");
            System.out.println("          )---| W |---(");
            System.out.println("         /-\\--| W |--/-\\");
            System.out.println("        (_-_--|_-_|--___)");
            System.out.println("       (-___  -_-- _-- -_)");
            System.out.println("       )-_ _--_ _ ___--__|");
            System.out.println("       (___ --__  __ __--(");
            System.out.println("      /-_  / __ -_ -__  \\_\\");
            System.out.println("     _>/  -- /|___| _ \\ -_ )");
            System.out.println("    /--  _ - _/ _ \\>\\ -  -- \\");
            System.out.println("   ( / / /   > |~l \\   \\ \\ \\_)");
            System.out.println("   | |-' | |/  \"\"\"  \\| |   |_|");
            System.out.println("   L_|_|_|_/         L_L_|_l_)");
            System.out.println("Now this, this is a big, amazing, fancy, great, cool, fabulous, titanic, remarkable"); 
            System.out.println("superior, noble, outstanding, glorious, prominent, renowned statue.");
            System.out.println("");
            System.out.println("I wonder if there are more interesting things here");
        }else if(LookPrepare().contains(("secret room.")))
        {   
            //code van foto van trump 
            System.out.println("");
            System.out.println("I see president Trump in front of me");
            System.out.println("I quickly use my rope on him and tie him up");
            System.out.println("I use my tape to tape his mouth shut");
            System.out.println("I got him I got president Trump, now let's get out of here");
            System.out.println("");
            System.out.println("Congratulations, you have captured president Trump.");
            System.out.println("type quit to go back to the main menu");
            return true;
        }else{
            System.out.println("That`s weird I can`t see anything, perhaps I should try again later");
        }
        return false;
    }
 

    /**
     * With the get command the player is able to pick-up items that are on the ground.
     * It checks if the item is in the room or not.
     * Then if the item is in the room the player puts it in it's inventory.
     */
    private boolean getItem(Command command) 
    {
        int value = LocalTime.now().compareTo(timer.endTime);
        while(value == 0 || value > 0){
            System.out.println("Your time is up, you will have to start all over again.");
            System.out.println("Type quit to go back to the menu.");
            return true;
        }

        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to pickup..
            System.out.println("Take what?");
            return false;
        }

        String itemName = command.getSecondWord();
        Item newItem = currentRoom.getItem(itemName);

        if(newItem == null){
            System.out.println(itemName +" is not here.");
        }
        else{
            inventory.put(itemName, newItem);
            currentRoom.removeItem(itemName);
            System.out.println("Picked up: " + itemName);   
            if (getTotalWeight() > 80   ){
                System.out.println("I can't carry that!");
                inventory.remove(itemName);
                currentRoom.setItem(newItem);
                System.out.println("Inventory weight: " + getTotalWeight()); 
            }
            else
            {
                System.out.println("New inventory weight: " + getTotalWeight() + "/80kg");
            }
        }
        return false;
    }

    /**
     * The drop item command is for the player to drop an item that is in it's inventory.
     * The code will check if the named item is in the inventory, if so it will drop it.
     */
    private boolean dropItem(Command command) 
    {
        int value = LocalTime.now().compareTo(timer.endTime);
        while(value == 0 || value > 0){
            System.out.println("Your time is up, you will have to start all over again.");
            System.out.println("Type quit to go back to the menu.");
            return true;
        }
        
        if(LookPrepare().contains(("secret room."))){
            System.out.println("I can't leave any traces behind here.");
            return false;
        }
        
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to drop...
            System.out.println("Wait a minute drop what?");
            return false;
        }
        
        String itemName = command.getSecondWord();
        Item newItem = inventory.get(itemName);
        if(newItem == null){
            System.out.println("I can't do that.");
        }
        else{
            inventory.remove(itemName);
            currentRoom.setItem(newItem);
            System.out.println("Dropped: " + itemName); 
            System.out.println("New inventory weight: " + getTotalWeight() + "/80kg");

        }
        return false;
    }

    /** With the inventory command the player can look what item(s) it has picked up.
     * It also tells the total amount of weight it is carrying and what it can maxium hold.
     *
     */
    private boolean printInventory(){
        String output = "";
        int value = LocalTime.now().compareTo(timer.endTime);

        while(value == 0 || value > 0){
            System.out.println("Your time is up, you will have to start all over again.");
            System.out.println("Type quit to go back to the menu.");
            return true;
        }
        for(String itemName : inventory.keySet()){
            output += inventory.get(itemName).getName()+ " : " + inventory.get(itemName).getDescription() + "--- Weight: " + inventory.get(itemName).getWeight() + "kg\n";
        }
        System.out.println("you are carrying:");
        System.out.println(output);
        System.out.println("Total: " + getTotalWeight() + "/80kg");
        return false;
    }

    /**
     * The totalWeight method will calculate how much kg the player is carrying and returns it as an integer.
     */
    private int getTotalWeight(){
        int output = 0;
        for(String itemName : inventory.keySet()){
            output = output + inventory.get(itemName).getWeight();
        }
        return output;
    }

}