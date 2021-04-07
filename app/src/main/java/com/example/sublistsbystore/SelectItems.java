package com.example.sublistsbystore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class SelectItems extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_items);

        buildTable();
    }

    /**
     * creates list of items and adds items to array
     * @param item
     */
    public void buildList(String item) {
        //    TODO: add functionality
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
//            TODO: finish build table
        }
    }

    public void nextPageButton(View view) {
        //FIXME: replace class name for 'how many stops' page --
        startActivity(new Intent(getApplicationContext(), null));
    }

    /**
     * clears the list of all items
     * @param view
     */
    public void clearListBtn(View view) {
        //    TODO: add clear list button
    }

    /**
     * refreshes list of items in display
     */
    public void buildTable() {
        //      TODO: finish makeDeleteItemButton()
    }

    /**
     * adds delete button to each item on list
     * @return
     */
    private Button makeDeleteItemButton() {
        //    TODO: add  delete button -- individual items, identify how we are going to store 'list of items'
        return null;
    }

    private TextView makeTV(String item) {
        //    TODO: set resource file for text size and padding
        return null;
    }


//    TODO: research 'bottom nav bar' design--> [<]Backward [Home/CurrentList] Forward[>]

//    TODO: create intent that leads to how many stops page

//    TODO: Implement popup after user clicks 'removeBtn' or 'clearListBtn'


}