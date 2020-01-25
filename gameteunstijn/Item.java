import java.util.*;
/**
 * This class is a part of the "Trumpnation" application. 
 * "Trumpnation" is a simple, text based adventure game.
 * class Items - This class holds the items, descriptions weight and name of those items
 *
 * @author Teun de Jong & Stijn Wolthuis
 * @version 2020.01.25
 */
public class Item
{
    
    public int weight ;
    public String name ;
    public String description ;

    /**
     * Constructor voor objects van class Items
     */
    public Item(String name, int weight, String description)
    {
        this.description = description;
        this.weight = weight;
        this.name = name;
    }
    /**
     * Puts the description of the item in a String and gives it back
     * @return String with item description
     */
    public String getDescription()
    {
        return description;
    }
    /**
     * Gives the weight of the item back as an integer
     * @return int with item weight
     */
    public int getWeight()
    {
        return weight;
    }
    /**
     * gives the name of the item back as a string
     * @return String with item name
     */
    public String getName()
    {
        return name;
    }
}

