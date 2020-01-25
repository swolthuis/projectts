import java.util.*;
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Teun de Jong & Stijn Wolthuis
 * @version 2020.01.25
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private HashMap <String,Item> itemsInRoom = new HashMap<String, Item>();
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return String Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "You can go :";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        returnString += "\nItems on this location: \n";
        returnString += getRoomItems() + " \n";
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return String The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }

    /**
     * Gives all the item names in the current room
     * @param item name.
     * @return String All items in the current room.
     */
    public Item getItem(String name){
        return itemsInRoom.get(name);
    }

    /**
     * Removes the items from the current room
     * @param item name.
     */
    public void removeItem(String itemName){
        itemsInRoom.remove(itemName);

    }

    /**
     * Puts a new item in the current room
     * @param Item newname. 
     */
    
    public void setItem(Item newitem){
        itemsInRoom.put(newitem.name, newitem);
    }
    /**
     * Looks for every item in the current room
     * puts all items with its name description and weight in the string output
     * @return String output with a list with all items in the current room
     */
    public String getRoomItems(){
        String output = "";
        for(String itemName : itemsInRoom.keySet()){
            output += itemsInRoom.get(itemName).name + " : " +  itemsInRoom.get(itemName).getDescription() + " ---" +itemsInRoom.get(itemName).getWeight() +" kg \n" ;
        }
        return output;
    }

}

