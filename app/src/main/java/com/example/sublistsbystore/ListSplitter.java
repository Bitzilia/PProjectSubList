package com.example.sublistsbystore;

import com.example.sublistsbystore.requestedItem.RequestedItem;
import com.example.sublistsbystore.retailitem.RetailItem;

public class ListSplitter {
    /** The list of items **/
    private RequestedItem groceryList;
    private int numStores;

    public void setGroceryList(RequestedItem groceryList) {
        this.groceryList = groceryList;
    }

    public RequestedItem getGroceryList() {
        return this.groceryList;
    }


    public int getNumStores() {
        return 0;
    }

    void setNumStores(int numStores) {
        this.numStores = numStores;
    }

    private double itemPriceCompare(RetailItem item) {
        return 0;
    }

    /**
     * @return number of items
     **/
    public int getTotal() {
        return 0;
    }
}
