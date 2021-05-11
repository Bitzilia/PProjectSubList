package com.example.sublistsbystore;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.sublistsbystore.item.Item;
import com.example.sublistsbystore.item.ItemDAO;
import com.example.sublistsbystore.requestedItem.RequestedItem;
import com.example.sublistsbystore.requestedItem.RequestedItemDAO;
import com.example.sublistsbystore.retailitem.RetailItemDAO;
import com.example.sublistsbystore.store.StoreDAO;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;


public class MainActivity extends AppCompatActivity {
    RequestedItemDAO requestedItemDAO;
    ItemDAO itemDAO;
    StoreDAO storeDAO;
    RetailItemDAO retailItemDAO;
    ShoparoundDB db;
    float scale;
    String itemName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = Room.databaseBuilder(getApplicationContext(), ShoparoundDB.class, "usershoparound.db")
                .createFromAsset("shoparound.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        requestedItemDAO = db.requestedItemDAO();
        itemDAO = db.itemDAO();
        scale = getApplicationContext().getResources().getDisplayMetrics().density;

        EditText itemNameInput = findViewById(R.id.user_text_input);
        itemNameInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                addItemBtnClicked(v);
                return false;
            }
        });

        buildItemTable();
    }

    /**
     * creates list of items and adds items to array
     * @param name
     */
    public void
    addItem(String name) {
        if (itemDAO.get(name) == null) {
            itemDAO.insertItem(new Item(name));
        }
        RequestedItem i = new RequestedItem(itemDAO.get(name).getItemID(), 1);
        requestedItemDAO.addRequestedItem(i);

        buildItemTable();
    }

    /**
     * inserts a new item to shopping list
     * @param view
     */
    public void addItemBtnClicked(View view) {
        EditText input = findViewById(R.id.user_text_input);

        if (input.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.unsuccessful_add, Toast.LENGTH_SHORT).show();
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
        StaticData.nameQuantityFrmDB.clear();

        for (RequestedItem r : requestedItemDAO.getAllRequestedItems()) {
            itemName = itemDAO.get(r.getItemID()).getItemName();
            int itemQuantity = r.getQuantity();
            StaticData.nameQuantityFrmDB.put(itemName, itemQuantity);
        }
        startActivity(new Intent(getApplicationContext(), StoreResultsActivity.class));
    }

    /**
     * clears the list of all items
     * @param view
     */
    public void clearListBtn(View view) {
        // credit: https://stackoverflow.com/a/5127506
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.confirm_clear_list_text))
                .setMessage(getString(R.string.confirm_clear_list_message))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        List<RequestedItem> itemsToDelete = requestedItemDAO.getAllRequestedItems();
                        itemsToDelete.forEach(item -> requestedItemDAO.removeRequestedItem(item));
                        buildItemTable();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    /**
     * refreshes list of items in display
     */
    public void buildItemTable() {
        TableLayout table = findViewById(R.id.current_items_table);

        // set the label column to stretch
        // credit: https://stackoverflow.com/questions/1666685/android-stretch-columns-evenly-in-a-tablelayout
        table.setColumnStretchable(2, true);
        table.removeAllViews(); // clear existing items

//      for each item on list, create a delete button and add textView
        for (RequestedItem item : requestedItemDAO.getAllRequestedItems()) {
            TableRow row = new TableRow(this.getApplicationContext());
            row.setGravity(Gravity.CENTER);
            row.addView(makeDeleteItemButton(item));
            row.addView(makeQuantityInput(item));
            row.addView(makeTV(itemDAO.get(item.getItemID()).getItemName()));
            table.addView(row);
        }
    }

    /**
     * adds delete button to each item on list
     * @param item
     * @return
     */
    private ImageButton makeDeleteItemButton(RequestedItem item) {
        ImageButton dltButton = new ImageButton(this.getApplicationContext());
        dltButton.setForegroundGravity(Gravity.CENTER);
        dltButton.setBackgroundColor(Color.TRANSPARENT);
        dltButton.setPadding((int) (5 * scale + 0.5f), 0, (int) (10 * scale + 0.5f), 0);
        dltButton.setMinimumHeight(MATCH_PARENT);
        Drawable deleteImage = getApplicationContext().getResources().getDrawable(R.drawable.ic_baseline_delete_24);
        dltButton.setImageDrawable(deleteImage);
        dltButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptDeleteItem(item, view);
            }
        });
        return dltButton;
    }

    private void promptDeleteItem(RequestedItem request, View view) {
        // credit: https://stackoverflow.com/a/5127506
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.confirm_delete_item_text))
                //See: https://developer.android.com/guide/topics/resources/string-resource#formatting-strings
                .setMessage(getString(R.string.confirm_delete_item_message, itemDAO.get(request.getItemID()).getItemName()))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        requestedItemDAO.removeRequestedItem(request);
                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        buildItemTable();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    private TextView makeTV(String item) {
        //    TODO: set resources for text size and padding
        TextView listView = new TextView(this.getApplicationContext());
        listView.setTextColor(getResources().getColor(R.color.black));
        listView.setText(item);
        listView.setTextSize(20);
        listView.setPadding(1, 0, 0, 0);
        return listView;
    }

    private LinearLayout makeQuantityInput(RequestedItem item) {
        LinearLayout container = new LinearLayout(this.getApplicationContext());

        //np: number picker
        TextView np = new TextView(this.getApplicationContext());
        np.setText(item.getQuantity() + "");

        Button inc = new Button(this.getApplicationContext());
        inc.setText("+");
        inc.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            this.requestedItemDAO.updateRequestedItem(item);
            np.setText(item.getQuantity() + "");
        });
        Button dec = new Button(this.getApplicationContext());
        dec.setText("-");
        dec.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {

                item.setQuantity(item.getQuantity() - 1);
                this.requestedItemDAO.updateRequestedItem(item);
                np.setText(item.getQuantity() + "");
            }
        });

        container.setPadding(0, 0, 0, 0);

        // calculate pixels from dps
        // credit: https://stackoverflow.com/a/5255256
        int pixels = (int) (30 * scale + 0.5f);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(pixels, pixels);
        params.height = MATCH_PARENT;
        container.setVerticalGravity(Gravity.CENTER_VERTICAL);
        params.setMargins(0, 0, 0, 0);

        np.setWidth(pixels);
        np.setGravity(Gravity.CENTER);
        inc.setPadding(0, 0, 0, 0);
        dec.setPadding(0, 0, 0, 0);

        container.addView(dec, params);
        container.addView(np, params);
        container.addView(inc, params);
        inc.setMaxWidth(pixels);
        dec.setMaxWidth(pixels);
        inc.setWidth(pixels);
        dec.setWidth(pixels);

        return container;
    }
}