import java.util.*;
/**
 * class Items - geef hier een beschrijving van deze class
 *
 * @author (jouw naam)
 * @version (versie nummer of datum)
 */
public class Item
{
    // instance variables - vervang deze door jouw variabelen
    public int weight ;
    public String name ;
    public String description ;
    /**
     * Constructor voor objects van class Items
     */

    /**
     * Constructor voor objects van class Items
     */
    public Item(String name, int weight, String description)
    {
        this.description = description;
        this.weight = weight;
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }
    
    public int getWeight()
    {
        return weight;
    }
    
    public String getName()
    {
        return name;
    }
}

