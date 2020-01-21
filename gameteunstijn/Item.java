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
    private int weight ;
    private String name ;
    private String ItemDescription ;
    /**
     * Constructor voor objects van class Items
     */
    public Item(String name)
    {
        this.name = name;
        weight = 0;
    }

    /**
     * Constructor voor objects van class Items
     */
    public Item(String name, int weight, String ItemDescription)
    {
        this.name = name;
        this.weight = weight;
        this.ItemDescription = ItemDescription;
    }

    public String getName()
    {
        return name;
    }

    public String getItemDescription(){
        return ItemDescription;
    }
    
    public int getWeight(){
        return weight;
    }
    
}
