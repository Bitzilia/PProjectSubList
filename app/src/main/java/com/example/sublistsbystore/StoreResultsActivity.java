package com.example.sublistsbystore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoreResultsActivity extends AppCompatActivity {

    private HashMap<String, Double> shawsInventory = new HashMap<>();
    private HashMap<String, Double> priceChopperInventory = new HashMap<>();
    private HashMap<String, Double> costcoInventory = new HashMap<>();
    private HashMap<String, Double> shawsRequest = new HashMap<>();
    private HashMap<String, Double> priceChopperRequest = new HashMap<>();
    private HashMap<String, Double> costcoRequest = new HashMap<>();
    private List<String> shopInput = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_results);

        //  from customer
        shopInput.add("milk");
        shopInput.add("bread");
        shopInput.add("rice");
        shopInput.add("oil");


        fillInventory();
        prepare1();
        prepare2();
        buildList();

    }

    private void fillInventory() {
        shawsInventory.put("milk", 3.99);
        shawsInventory.put("eggs", 3.99);
        shawsInventory.put("bread", 3.99);
        shawsInventory.put("berry", 1.99);


        priceChopperInventory.put("milk", 4.99);
        priceChopperInventory.put("eggs", 3.99);
        priceChopperInventory.put("bread", 2.99);
        priceChopperInventory.put("rice", 2.99);


        costcoInventory.put("milk", 5.99);
        costcoInventory.put("eggs", 1.99);
        costcoInventory.put("oil", 3.99);
        costcoInventory.put("rice", 3.99);


    }

    /**
     * button to nav to next page
     * @param view
     */
    public void buttonOver(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    //making subLists per store with customer's requests
    private void prepare1() {

        for (int i = 0; i < shopInput.size(); i++) {
            String tmp = shopInput.get(i);
            if (shawsInventory.containsKey(tmp)) {
                shawsRequest.put(tmp, shawsInventory.get(tmp));
            }
            if (priceChopperInventory.containsKey(tmp)) {
                priceChopperRequest.put(tmp, priceChopperInventory.get(tmp));
            }
            if (costcoInventory.containsKey(tmp)) {
                costcoRequest.put(tmp, costcoInventory.get(tmp));
            }
        }
    }


    //finding/creating cheapest shopping list
    private void prepare2() {

        for (int i = 0; i < shopInput.size(); i++) {

            String tmp = shopInput.get(i);
            Double shaws = null;
            Double chopper = null;
            Double costco = null;

            if (shawsRequest.containsKey(tmp)) {
                shaws = shawsRequest.get(tmp);
            }
            if (priceChopperRequest.containsKey(tmp)) {
                chopper = priceChopperRequest.get(tmp);
            }
            if (costcoRequest.containsKey(tmp)) {
                costco = costcoRequest.get(tmp);
            }

            if (shaws != null && chopper != null && costco != null) {
                if (shaws <= chopper) {
                    priceChopperRequest.remove(tmp);
                    if (shaws <= costco) {
                        costcoRequest.remove(tmp);
                    } else {
                        shawsRequest.remove(tmp);
                    }
                } else {
                    shawsRequest.remove(tmp);
                    if (chopper <= costco) {
                        costcoRequest.remove(tmp);
                    } else {
                        priceChopperRequest.remove(tmp);
                    }
                }
            } else if (shaws != null && chopper != null && costco == null) {
                if (shaws <= chopper) {
                    priceChopperRequest.remove(tmp);
                } else {
                    shawsRequest.remove(tmp);
                }
            } else if (shaws != null && chopper == null && costco != null) {
                if (shaws <= costco) {
                    costcoRequest.remove(tmp);
                } else {
                    shawsRequest.remove(tmp);
                }
            } else if (shaws == null && chopper != null && costco != null) {
                if (chopper <= costco) {
                    costcoRequest.remove(tmp);
                } else {
                    priceChopperRequest.remove(tmp);
                }
            }
        }
    }


    private void buildList() {

        TableLayout table = findViewById(R.id.table);
        table.removeAllViews();

        if (!shawsRequest.isEmpty()) {
            subList(shawsRequest, table, "Shaws Items:");
            //need to create new line in layout
        }
        if (!priceChopperRequest.isEmpty()) {
            subList(priceChopperRequest, table, "Price Chopper Items:");
            //need to create new line in layout
        }
        if (!costcoRequest.isEmpty()) {
            subList(costcoRequest, table, "Costco Items:");
        }
    }

    private TextView makeTV(String word) {

        TextView view = new TextView(this.getApplicationContext());
        view.setText(word);
        view.setPadding(0, 0, getResources().getDimensionPixelSize(R.dimen.table_padding), 0);
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.table_text_size));

        return view;
    }

    private void subList(HashMap<String, Double> sub, TableLayout tab, String str) {
        TableRow firstRow = new TableRow(this.getApplicationContext());
        firstRow.addView(makeTV(str));
        tab.addView(firstRow);
        for (Map.Entry<String, Double> entry : sub.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();
            TableRow row = new TableRow(this.getApplicationContext());
            row.addView(makeTV("Item: " + key + "  -  Price: " + value));
            tab.addView(row);
        }
    }
}
