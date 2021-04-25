package com.example.sublistsbystore;

import java.util.ArrayList;

public class ListSplitter {
    /** The list of items **/
    private GroceryList groceryList;
    private int numStores;

    public void setGroceryList(GroceryList groceryList) {
        this.groceryList = groceryList;
    }

    public GroceryList getGroceryList() {
        return this.groceryList;
    }

    /** gets the list of groceries separated by store **/
    public SeparatedList getSeparatedList() {
        return null;
    }

    public int getNumStores() {
        return 0;
    }

    void setNumStores(int numStores) {
        this.numStores = numStores;
    }

    private double itemPriceCompare(Item item) {
        return 0;
    }

    /**
     * @return number of items
     **/
    public int getTotal() {
        return 0;
    }
}
