import java.util.*;
/**
 * class Inventory - geef hier een beschrijving van deze class
 *
 * @author (jouw naam)
 * @version (versie nummer of datum)
 */
public class Inventory
{
    // instance variables 
    private ArrayList<Item> inventory;
    /**
     * Constructor voor objects van class Inventory
     */
    public Inventory()
    {
        // geef de instance variables een beginwaarde
        inventory = new ArrayList();
    }

    public void addItem(Item item)
    {
        inventory.add(item);
    }
}
