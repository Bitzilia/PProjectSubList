package com.example.sublistsbystore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class SelectItemsActivity extends AppCompatActivity {

    private List<Item> itemList = new ArrayList<>();
    float scale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_items);
        scale = getApplicationContext().getResources().getDisplayMetrics().density;
        buildItemTable();
    }

    /**
     * creates list of items and adds items to array
     * @param name
     */
    public void addItem(String name) {
        Item i = new Item();
        i.setName(name);
        itemList.add(i);
    }

    /**
     * inserts a new item to shopping list
     * @param view
     */
    public void addItemBtnClicked(View view) {
        EditText input = findViewById(R.id.user_text_input);

        if (input.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Field cannot be empty!", Toast.LENGTH_SHORT).show();
        } else {

            addItem(input.getText().toString());

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

            input.setText("");
            input.setEnabled(true);
            buildItemTable();

            Toast.makeText(getApplicationContext(), R.string.successful_add, Toast.LENGTH_SHORT).show();
        }
    }

    public void nextPageButton(View view) {
        //FIXME: replace class name for 'how many stops' page --
        startActivity(new Intent(getApplicationContext(), StoresAndSavingsActivity.class));
    }

    /**
     * clears the list of all items
     * @param view
     */
    public void clearListBtn(View view) {
        Iterator<Item> i = itemList.iterator();
        while (i.hasNext()) {
            i.next();
            i.remove();
        }
        buildItemTable();
    }

    /**
     * refreshes list of items in display
     */
    public void buildItemTable() {
        TableLayout table = findViewById(R.id.current_items_table);

        table.setStretchAllColumns(true);
        table.removeAllViews(); // clear existing items

//      for each item on list, create a delete button and add textView
        for (Item item : itemList) {
            TableRow row = new TableRow(this.getApplicationContext());
            row.addView(makeDeleteItemButton(item));
            row.addView(makeQuantityInput(item));
            row.addView(makeTV(item.getName()));
            table.addView(row);
        }

    }

    /**
     * adds delete button to each item on list
     * @return
     */
    private Button makeDeleteItemButton(Item item) {
        Button dltButton = new Button(this.getApplicationContext());
        dltButton.setText("Remove");
        dltButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.remove(item);
                buildItemTable();
            }
        });
        return dltButton;
    }

    private TextView makeTV(String item) {
        //    TODO: set resources for text size and padding
        TextView listView = new TextView(this.getApplicationContext());
        listView.setText(item);
        listView.setTextSize(16);
        listView.setPadding(1, 0, 0, 0);
        return listView;
    }

    private LinearLayout makeQuantityInput(Item item) {
        LinearLayout container = new LinearLayout(this.getApplicationContext());
//        container.setOrientation(LinearLayout.VERTICAL);

        TextView np = new TextView(this.getApplicationContext());
        np.setText(item.getQuantity() + "");

        Button inc = new Button(this.getApplicationContext());
        inc.setText("+");
        inc.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            np.setText(item.getQuantity() + "");
        });
        Button dec = new Button(this.getApplicationContext());
        dec.setText("-");
        dec.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {

                item.setQuantity(item.getQuantity() - 1);
                np.setText(item.getQuantity() + "");
            }
        });


        container.addView(dec);
        container.addView(np);
        container.addView(inc);


        // credit: https://stackoverflow.com/a/5255256
        int pixels = (int) (41 * scale + 0.5f);

        // TODO how to make increment/decrement/remove buttons narrower programmatically
        // TODO or is there a better way to be building each row? (TableRow as Fragment? Separate Class?)
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(pixels, pixels);
        params.weight = 0.5f;
        inc.setMaxWidth(pixels);
        inc.setWidth(pixels);
        inc.setLayoutParams(params);
        dec.setMaxWidth(pixels);
        dec.setWidth(pixels);
        dec.setLayoutParams(params);

        return container;
    }


//    TODO: research 'bottom nav bar' design--> [<]Backward [Home/CurrentList] Forward[>]

//    TODO: create intent that leads to how many stops page

//    TODO: Implement popup after user clicks 'removeBtn' or 'clearListBtn'


}