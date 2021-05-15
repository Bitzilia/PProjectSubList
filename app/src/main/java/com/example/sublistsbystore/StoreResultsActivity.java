/**
 * This was done by Ali Alrubaye
 */
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
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;
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
    private HashMap<String, Double> dualCanceledStores = new HashMap<>();
    private List<String> shopInput = new ArrayList<>();
    private List<String> notExistItems = new ArrayList<>();
    private List<String> shawsCanceled = new ArrayList<>();
    private List<String> priceChCanceled = new ArrayList<>();
    private List<String> costcoCanceled = new ArrayList<>();
    private double grandT = 0;
    private TextView g;
    private RadioButton oneStore;
    private RadioButton twoStores;
    private RadioButton threeStores;
    public TableLayout table;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_results);
        g = findViewById(R.id.gtot);
        oneStore = findViewById(R.id.oneST);
        twoStores = findViewById(R.id.twoST);
        threeStores = findViewById(R.id.threeST);
        table = findViewById(R.id.table);
        threeStores.setChecked(true);


        shopInputFillFromDB();
        fillInventory();
        prepare1();
        prepare2();
        buildList();


    }

    private void shopInputFillFromDB() {
        if (!StaticData.nameQuantityFrmDB.isEmpty()) {
            for (Map.Entry<String, Integer> entry : StaticData.nameQuantityFrmDB.entrySet()) {
                String key = entry.getKey();
                shopInput.add(key);
            }
        }
    }

    private void fillInventory() {
        shawsInventory.putAll(StaticData.shawsInventoryFrmDB);
        costcoInventory.putAll(StaticData.costcoInventoryFrmDB);
        priceChopperInventory.putAll(StaticData.priceChopperInventoryFrmDB);

    }

    /**
     * button to nav to next page
     *
     * @param view
     */
    public void buttonOver(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }


    //making subLists per store with customer's requests
    private void prepare1() {
        shawsRequest.clear();
        priceChopperRequest.clear();
        costcoRequest.clear();
        notExistItems.clear();
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
            if (!shawsInventory.containsKey(tmp) && !priceChopperInventory.containsKey(tmp) && !costcoInventory.containsKey(tmp)) {
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


    public void prepare3(View v) {
        grandT = 0;
        int shawsSize = shawsRequest.size();
        int priceChSize = priceChopperRequest.size();
        int costcoSize = costcoRequest.size();
        double shawsSTotal = subTotal(shawsRequest);
        double priceChSTotal = subTotal(priceChopperRequest);
        double costcoSTotal = subTotal(costcoRequest);

        if (threeStores.isChecked()) {
            buildList(); //build the whole list
            Toast.makeText(this, "Three stores list selected", Toast.LENGTH_SHORT).show();
        }
        if (twoStores.isChecked()) {
            shawsCanceled.clear();
            priceChCanceled.clear();
            costcoCanceled.clear();
            Toast.makeText(this, "Two stores list selected", Toast.LENGTH_SHORT).show();
            if (shawsSize == priceChSize && priceChSize == costcoSize) {
                //based on individual subtotals
                if (shawsSTotal < priceChSTotal && shawsSTotal < costcoSTotal) {
                    /**START Saving/retrieving items**/
                    for (Map.Entry<String, Double> entry : shawsRequest.entrySet()) {
                        String key = entry.getKey();
                        Double value = entry.getValue();
                        if(priceChopperInventory.containsKey(key)){
                            priceChopperRequest.put(key,priceChopperInventory.get(key));
                        }else if(costcoInventory.containsKey(key)){
                            costcoRequest.put(key,costcoInventory.get(key));
                        }else{
                            shawsCanceled.add(key);
                        }
                    }
                    /**END Saving/retrieving items***/
                    shawsRequest.clear();
                    buildList();
                    //TODO printing canceled items for each#
                    if(!shawsCanceled.isEmpty()){
                        printCanceled2Stores("SHAWS EXCLUSIVE",shawsCanceled);
                    }
                } else if (priceChSTotal < costcoSTotal && priceChSTotal < shawsSTotal) {
                    /**START Saving/retrieving items**/
                    for (Map.Entry<String, Double> entry : priceChopperRequest.entrySet()) {
                        String key = entry.getKey();
                        Double value = entry.getValue();
                        if(shawsInventory.containsKey(key)){
                            shawsRequest.put(key,shawsInventory.get(key));
                        }else if(costcoInventory.containsKey(key)){
                            costcoRequest.put(key,costcoInventory.get(key));
                        }else{
                            priceChCanceled.add(key);
                        }
                    }
                    /**END Saving/retrieving items***/
                    priceChopperRequest.clear();
                    buildList();
                    //TODO printing canceled items for each
                    if(!priceChCanceled.isEmpty()){
                        printCanceled2Stores("P. CHPR EXCLUSIVE",priceChCanceled);
                    }
                } else {
                    /**START Saving/retrieving items**/
                    for (Map.Entry<String, Double> entry : costcoRequest.entrySet()) {
                        String key = entry.getKey();
                        Double value = entry.getValue();
                        if(shawsInventory.containsKey(key)){
                            shawsRequest.put(key,shawsInventory.get(key));
                        }else if(priceChopperInventory.containsKey(key)){
                            priceChopperRequest.put(key,priceChopperInventory.get(key));
                        }else{
                            costcoCanceled.add(key);
                        }
                    }
                    /**END Saving/retrieving items***/
                    costcoRequest.clear();
                    buildList();
                    //TODO printing canceled items for each
                    if(!costcoCanceled.isEmpty()){
                        printCanceled2Stores("COSTCO EXCLUSIVE",costcoCanceled);
                    }
                }
            } else {
                //based on # of items
                if (shawsSize < priceChSize && shawsSize < costcoSize) {
                    /**START Saving/retrieving items**/
                    for (Map.Entry<String, Double> entry : shawsRequest.entrySet()) {
                        String key = entry.getKey();
                        Double value = entry.getValue();
                        if(priceChopperInventory.containsKey(key)){
                            priceChopperRequest.put(key,priceChopperInventory.get(key));
                        }else if(costcoInventory.containsKey(key)){
                            costcoRequest.put(key,costcoInventory.get(key));
                        }else{
                            shawsCanceled.add(key);
                        }
                    }
                    /**END Saving/retrieving items***/
                    shawsRequest.clear();
                    buildList();
                    //TODO printing canceled items for each
                    if(!shawsCanceled.isEmpty()){
                        printCanceled2Stores("SHAWS EXCLUSIVE",shawsCanceled);
                    }
                } else if (priceChSize < costcoSize && priceChSize < shawsSize) {
                    /**START Saving/retrieving items**/
                    for (Map.Entry<String, Double> entry : priceChopperRequest.entrySet()) {
                        String key = entry.getKey();
                        Double value = entry.getValue();
                        if(shawsInventory.containsKey(key)){
                            shawsRequest.put(key,shawsInventory.get(key));
                        }else if(costcoInventory.containsKey(key)){
                            costcoRequest.put(key,costcoInventory.get(key));
                        }else{
                            priceChCanceled.add(key);
                        }
                    }
                    /**END Saving/retrieving items***/
                    priceChopperRequest.clear();
                    buildList();
                    //TODO printing canceled items for each
                    if(!priceChCanceled.isEmpty()){
                        printCanceled2Stores("P. CHPR EXCLUSIVE",priceChCanceled);
                    }
                } else {
                    /**START Saving/retrieving items**/
                    for (Map.Entry<String, Double> entry : costcoRequest.entrySet()) {
                        String key = entry.getKey();
                        Double value = entry.getValue();
                        if(shawsInventory.containsKey(key)){
                            shawsRequest.put(key,shawsInventory.get(key));
                        }else if(priceChopperInventory.containsKey(key)){
                            priceChopperRequest.put(key,priceChopperInventory.get(key));
                        }else{
                            costcoCanceled.add(key);
                        }
                    }
                    /**END Saving/retrieving items***/
                    costcoRequest.clear();
                    buildList();
                    //TODO printing canceled items for each
                    if(!costcoCanceled.isEmpty()){
                        printCanceled2Stores("COSTCO EXCLUSIVE",costcoCanceled);
                    }
                }
            }
        }
        if (oneStore.isChecked()) {

            dualCanceledStores.clear();
            Toast.makeText(this, "One store list selected", Toast.LENGTH_SHORT).show();
            if (shawsSize == priceChSize && priceChSize == costcoSize) {
                //based on individual subtotal
                if (shawsSTotal > priceChSTotal && shawsSTotal > costcoSTotal) {
                    //START Saving/retrieving items
                    dualCanceledStores.putAll(priceChopperRequest);
                    dualCanceledStores.putAll(costcoRequest);
                    for (Map.Entry<String, Double> entry : dualCanceledStores.entrySet()) {
                        String key = entry.getKey();
                        Double value = entry.getValue();
                        if(shawsInventory.containsKey(key)){
                            shawsRequest.put(key,shawsInventory.get(key));
                        }
                    }
                    for (Map.Entry<String, Double> entry : shawsRequest.entrySet()) {
                        String key = entry.getKey();
                        Double value = entry.getValue();
                        if(dualCanceledStores.containsKey(key)){
                            dualCanceledStores.remove(key);
                        }
                    }
                    //END Saving/retrieving items
                    priceChopperRequest.clear();
                    costcoRequest.clear();
                    buildList();
                    //TODO printing canceled items for each
                    Toast.makeText(this, dualCanceledStores.toString(), Toast.LENGTH_SHORT).show();
                } else if (priceChSTotal > costcoSTotal) {
                    //START Saving/retrieving items
                    dualCanceledStores.putAll(shawsRequest);
                    dualCanceledStores.putAll(costcoRequest);
                    for (Map.Entry<String, Double> entry : dualCanceledStores.entrySet()) {
                        String key = entry.getKey();
                        Double value = entry.getValue();
                        if(priceChopperInventory.containsKey(key)){
                            priceChopperRequest.put(key,priceChopperInventory.get(key));
                        }
                    }
                    for (Map.Entry<String, Double> entry : priceChopperRequest.entrySet()) {
                        String key = entry.getKey();
                        Double value = entry.getValue();
                        if(dualCanceledStores.containsKey(key)){
                            dualCanceledStores.remove(key );
                        }
                    }
                    //END Saving/retrieving items
                    costcoRequest.clear();
                    shawsRequest.clear();
                    buildList();
                    //TODO printing canceled items for each
                    Toast.makeText(this, dualCanceledStores.toString(), Toast.LENGTH_SHORT).show();
                } else {
                    //START Saving/retrieving items
                    dualCanceledStores.putAll(priceChopperRequest);
                    dualCanceledStores.putAll(shawsRequest);
                    for (Map.Entry<String, Double> entry : dualCanceledStores.entrySet()) {
                        String key = entry.getKey();
                        Double value = entry.getValue();
                        if(costcoInventory.containsKey(key)){
                            costcoRequest.put(key,costcoInventory.get(key));
                        }
                    }
                    for (Map.Entry<String, Double> entry : costcoRequest.entrySet()) {
                        String key = entry.getKey();
                        Double value = entry.getValue();
                        if(dualCanceledStores.containsKey(key)){
                            dualCanceledStores.remove(key );
                        }
                    }
                    //END Saving/retrieving items
                    priceChopperRequest.clear();
                    shawsRequest.clear();
                    buildList();
                    //TODO printing canceled items for each
                    Toast.makeText(this, dualCanceledStores.toString(), Toast.LENGTH_SHORT).show();
                }
            } else {
                //based on # of items
                if (shawsSize > priceChSize && shawsSize > costcoSize) {
                    //START Saving/retrieving items
                    dualCanceledStores.putAll(priceChopperRequest);
                    dualCanceledStores.putAll(costcoRequest);
                    for (Map.Entry<String, Double> entry : dualCanceledStores.entrySet()) {
                        String key = entry.getKey();
                        Double value = entry.getValue();
                        if(shawsInventory.containsKey(key)){
                            shawsRequest.put(key,shawsInventory.get(key));
                        }
                    }
                    for (Map.Entry<String, Double> entry : shawsRequest.entrySet()) {
                        String key = entry.getKey();
                        Double value = entry.getValue();
                        if(dualCanceledStores.containsKey(key)){
                            dualCanceledStores.remove(key );
                        }
                    }
                    //END Saving/retrieving items
                    priceChopperRequest.clear();
                    costcoRequest.clear();
                    buildList();
                    //TODO printing canceled items for each
                    Toast.makeText(this, dualCanceledStores.toString(), Toast.LENGTH_SHORT).show();
                } else if (priceChSize > costcoSize) {
                    //START Saving/retrieving items
                    dualCanceledStores.putAll(shawsRequest);
                    dualCanceledStores.putAll(costcoRequest);
                    for (Map.Entry<String, Double> entry : dualCanceledStores.entrySet()) {
                        String key = entry.getKey();
                        Double value = entry.getValue();
                        if(priceChopperInventory.containsKey(key)){
                            priceChopperRequest.put(key,priceChopperInventory.get(key));
                        }
                    }
                    for (Map.Entry<String, Double> entry : priceChopperRequest.entrySet()) {
                        String key = entry.getKey();
                        Double value = entry.getValue();
                        if(dualCanceledStores.containsKey(key)){
                            dualCanceledStores.remove(key );
                        }
                    }
                    //END Saving/retrieving items
                    costcoRequest.clear();
                    shawsRequest.clear();
                    buildList();
                    //TODO printing canceled items for each
                    Toast.makeText(this, dualCanceledStores.toString(), Toast.LENGTH_SHORT).show();
                } else {
                    //START Saving/retrieving items
                    dualCanceledStores.putAll(priceChopperRequest);
                    dualCanceledStores.putAll(shawsRequest);
                    for (Map.Entry<String, Double> entry : dualCanceledStores.entrySet()) {
                        String key = entry.getKey();
                        Double value = entry.getValue();
                        if(costcoInventory.containsKey(key)){
                            costcoRequest.put(key,costcoInventory.get(key));
                        }
                    }
                    for (Map.Entry<String, Double> entry : costcoRequest.entrySet()) {
                        String key = entry.getKey();
                        Double value = entry.getValue();
                        if(dualCanceledStores.containsKey(key)){
                            dualCanceledStores.remove(key );
                        }
                    }
                    //END Saving/retrieving items
                    priceChopperRequest.clear();
                    shawsRequest.clear();
                    buildList();
                    //TODO printing canceled items for each
                    Toast.makeText(this, dualCanceledStores.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            //p
        }
        prepare1();
        prepare2();
    }

    private void printCanceled2Stores(String s, List<String> l){
        TableRow space = new TableRow(this.getApplicationContext());
        TextView sp = new TextView(this.getApplicationContext());
        sp.setText("SPACE");
        sp.setTextColor(0x000000ff);
        space.addView(sp);
        table.addView(space);

        TableRow row = new TableRow(this.getApplicationContext());
        TextView viewX = new TextView(this.getApplicationContext());
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        params.span = 3;
        viewX.setText(s);
        viewX.setPadding(0, 0, getResources().getDimensionPixelSize(R.dimen.table_padding), 0);
        viewX.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.table_text_size2));
        viewX.setTextColor(0xff0000ff);
        viewX.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.table_text_size));
        viewX.setLayoutParams(params);
        row.addView(viewX, params);
        table.addView(row);
        for (int i = 0; i < l.size(); i++) {

            TableRow row2 = new TableRow(this.getApplicationContext());
            row2.addView(makeTV("Item(" + (i + 1) + "): "));
            row2.addView(makeTV2(l.get(i)));
            table.addView(row2);

           }
    }




    private void buildList() {
        //TableLayout table = findViewById(R.id.table);
        table.removeAllViews();
        if (!shawsRequest.isEmpty()) {
            subList(shawsRequest, table, "SHAWS");
            TableRow space = new TableRow(this.getApplicationContext());
            TextView s = new TextView(this.getApplicationContext());
            s.setText("SPACE");
            s.setTextColor(0x000000ff);
            space.addView(s);
            table.addView(space);
        }
        if (!priceChopperRequest.isEmpty()) {

            subList(priceChopperRequest, table, "P. CHPR");
            TableRow space = new TableRow(this.getApplicationContext());
            TextView s = new TextView(this.getApplicationContext());
            s.setText("SPACE");
            s.setTextColor(0x000000ff);
            space.addView(s);
            table.addView(space);
        }
        if (!costcoRequest.isEmpty()) {
            subList(costcoRequest, table, "COSTCO");
            TableRow space = new TableRow(this.getApplicationContext());
            TextView s = new TextView(this.getApplicationContext());
            s.setText("SPACE");
            s.setTextColor(0x000000ff);
            space.addView(s);
            table.addView(space);
        }
        //This is the non exist items display
        if (!notExistItems.isEmpty()) {
            TableRow row = new TableRow(this.getApplicationContext());
            row.addView(makeTV("N/A"));
            table.addView(row);
            for (int i = 0; i < notExistItems.size(); i++) {
                row = new TableRow(this.getApplicationContext());
                row.addView(makeTV("Item(" + (i + 1) + "): "));
                //table.addView(row);
                /****/
                //TableRow rowX = new TableRow(this.getApplicationContext());
                TextView viewX = new TextView(this.getApplicationContext());

                TableRow.LayoutParams params = new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT);
                params.span = 3;
                viewX.setText(notExistItems.get(i));
                viewX.setPadding(0, 0, getResources().getDimensionPixelSize(R.dimen.table_padding), 0);
                viewX.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.table_text_size2));
                viewX.setLayoutParams(params);
                row.addView(viewX, params);
                table.addView(row);
                /****/

            }
        }
        DecimalFormat currency = new DecimalFormat("'$'0.00");
        g.setText("GRAND TOTAL: " + currency.format(Math.round(grandT * 100.0) / 100.0));
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
        view.setPadding(0, 0, getResources().getDimensionPixelSize(R.dimen.table_padding), 0);
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.table_text_size2));
        return view;
    }



    private void subList(HashMap<String, Double> sub, TableLayout tab, String str) {
        double total = 0;
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
            total += (value * quantity);
            TableRow row = new TableRow(this.getApplicationContext());
            row.setGravity(Gravity.CENTER);
            CheckBox box = new CheckBox(getApplicationContext());
            box.setText("");
            row.addView(box);
            row.addView(makeTV2("  " + key));
            DecimalFormat currency = new DecimalFormat("'$'0.00");
            row.addView(makeTV2("  "+currency.format(Math.round(value * 100.0) / 100.0)));
            row.addView(makeTV2("        " + quantity));
            box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        row.setBackgroundColor(Color.GRAY);
                    }else{
                        row.setBackgroundColor(Color.TRANSPARENT);
                    }
                }
            });
            tab.addView(row);
        }
        grandT += total;
        TableRow rowT = new TableRow(this.getApplicationContext());
        rowT.addView(makeTV("COST:"));
        DecimalFormat currency = new DecimalFormat("'$'0.00");
        rowT.addView(makeTV(currency.format(Math.round(total * 100.0) / 100.0)));
        tab.addView(rowT);
    }

    private double subTotal(HashMap<String, Double> sub) {
        double total = 0.0;
        for (Map.Entry<String, Double> entry : sub.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();
            int quantity = StaticData.nameQuantityFrmDB.get(key);
            total += (value * quantity);
        }
        return total;
    }
}