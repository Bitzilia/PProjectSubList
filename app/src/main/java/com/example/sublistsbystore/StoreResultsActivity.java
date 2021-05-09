package com.example.sublistsbystore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sublistsbystore.item.ItemDAO;
import com.example.sublistsbystore.requestedItem.RequestedItemDAO;

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
    private List<String> notExistItems = new ArrayList<>();
    private ItemDAO itemDAO;
    private RequestedItemDAO requestedItemDAO;
    private double grandT=0;
    private TextView g;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_results);

        g =  findViewById(R.id.gtot);



        //  from customer
        /*shopInput.add("milk");
        shopInput.add("bread");
        shopInput.add("rice");
        shopInput.add("oil");
        shopInput.add("Bicycle");
        shopInput.add("book");*/


        shopInputFillFromDB();



        fillInventory();
        prepare1();
        prepare2();
        buildList();

    }

    private void shopInputFillFromDB(){
        if(!StaticData.nameQuantityFrmDB.isEmpty()) {
        for (Map.Entry<String, Integer> entry : StaticData.nameQuantityFrmDB.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();
            shopInput.add(key);
           }
        }
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
            if(!shawsInventory.containsKey(tmp) && !priceChopperInventory.containsKey(tmp) && !costcoInventory.containsKey(tmp)){
                notExistItems.add(tmp);
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

             subList(shawsRequest, table, "SHAWS" );
            TableRow space = new TableRow(this.getApplicationContext());
            TextView s = new TextView(this.getApplicationContext());
            s.setText("SPACE");
            s.setTextColor(0x000000ff);
            space.addView(s);
            table.addView(space);

        }
        if (!priceChopperRequest.isEmpty()) {

             subList(priceChopperRequest, table,"P. CHPR" );
            TableRow space = new TableRow(this.getApplicationContext());
            TextView s = new TextView(this.getApplicationContext());
            s.setText("SPACE");
            s.setTextColor(0x000000ff);
            space.addView(s);
            table.addView(space);
        }
        if (!costcoRequest.isEmpty()) {

             subList(costcoRequest, table,"COSTCO" );
            TableRow space = new TableRow(this.getApplicationContext());
            TextView s = new TextView(this.getApplicationContext());
            s.setText("SPACE");
            s.setTextColor(0x000000ff);
            space.addView(s);
            table.addView(space);
        }

        //This is the non exist items display
        if(!notExistItems.isEmpty()){
            TableRow row = new TableRow(this.getApplicationContext());
            row.addView(makeTV("N/A" ));
            table.addView(row);
        for (int i = 0; i < notExistItems.size(); i++) {
            row = new TableRow(this.getApplicationContext());
            row.addView(makeTV("Item("+(i+1)+"): "));
            row.addView(makeTV2(notExistItems.get(i) ));
            table.addView(row);
           }
        }

        g.setText("GRAND TOTAL: $ "+Math.round(grandT * 100.0) / 100.0);

    }

    private TextView makeTV(String word) {

        TextView view = new TextView(this.getApplicationContext());
        view.setText(word);
        view.setTextColor(0xff0000ff);
         //view.setPadding(0, 0, getResources().getDimensionPixelSize(R.dimen.table_padding), 0);
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.table_text_size));


        return view;
    }

     private TextView makeTV2(String word) {

        TextView view = new TextView(this.getApplicationContext());
        view.setText(word);
        //view.setTextColor(0x000000ff);
         view.setPadding(0, 0, getResources().getDimensionPixelSize(R.dimen.table_padding), 0);
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.table_text_size2));
        return view;
    }

    private void subList(HashMap<String, Double> sub, TableLayout tab, String str) {
        double total=0;
         TableRow firstRow = new TableRow(this.getApplicationContext());
        firstRow.addView(makeTV(str));
        tab.addView(firstRow);
        TableRow secondRow = new TableRow(this.getApplicationContext());
        secondRow.addView(makeTV(" CHK "));
        secondRow.addView(makeTV(" NAME       "));
        secondRow.addView(makeTV(" PRICE       "));
        secondRow.addView(makeTV(" QUANTITY "));
        tab.addView(secondRow);
        for (Map.Entry<String, Double> entry : sub.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();

            int quantity = StaticData.nameQuantityFrmDB.get(key);
            total+= (value*quantity);
            TableRow row = new TableRow(this.getApplicationContext());
             row.setGravity(Gravity.CENTER);



            CheckBox box = new CheckBox(getApplicationContext());
            box.setText("");
            row.addView(box);
            row.addView(makeTV2("   "+key));
            row.addView(makeTV2("    "+value));
            row.addView(makeTV2("        "+quantity));

            box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    row.setBackgroundColor(Color.GRAY);

                }
            });

            tab.addView(row);

        }
        grandT+=total;
        TableRow rowT = new TableRow(this.getApplicationContext());
        rowT.addView(makeTV("COST: $ "));
        rowT.addView(makeTV(String.valueOf(Math.round(total * 100.0) / 100.0 )));
        tab.addView(rowT);

    }

}
