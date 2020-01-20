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
    public Item(String name, int weight)
    {
        this.name = name;
        this.weight = weight;
    }
    
    public String getName()
    {
        return name;
    }
}
