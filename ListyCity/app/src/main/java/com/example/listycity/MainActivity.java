package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;

    private ArrayList<String> cities = new ArrayList<>();
    private String selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //Set up the list view
        cityList = findViewById(R.id.city_list);
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, cities);
        addCity("Edmonton");
        addCity("Vancouver");
        cityList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        cityList.setAdapter(cityAdapter);

        //Set up the buttons
        View confirm = findViewById(R.id.confirm);
        View bottomblock = findViewById(R.id.bottomblock);
        View addCity = findViewById(R.id.addCity);
        View deleteCity = findViewById(R.id.deleteCity);
        TextView selectionText = (TextView) findViewById(R.id.selection);
        TextInputLayout entry = findViewById(R.id.editText);

        confirm.setVisibility(View.INVISIBLE);
        bottomblock.setVisibility(View.INVISIBLE);
        entry.setVisibility(View.INVISIBLE);
        selectionText.setVisibility(View.INVISIBLE);

        //Add City
        addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm.setVisibility(View.VISIBLE);
                bottomblock.setVisibility(View.VISIBLE);
                entry.setVisibility(View.VISIBLE);
                //Hide other UI if applicable
                selectionText.setVisibility(View.INVISIBLE);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCity(String.valueOf(entry.getEditText().getText()));
                confirm.setVisibility(View.INVISIBLE);
                bottomblock.setVisibility(View.INVISIBLE);
                entry.setVisibility(View.INVISIBLE);
                entry.getEditText().setText("");
            }
        });

        //Delete City
        deleteCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectionText.setVisibility(View.INVISIBLE);
                removeCity(selected);
                selected = null;
            }
        });

        //Selection detection for the list
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                selected = (String) adapter.getItemAtPosition(position);
                selectionText.setText(" Selected " + (String) selected);
                selectionText.setVisibility(View.VISIBLE);
                //Hide other UI if applicable
                confirm.setVisibility(View.INVISIBLE);
                bottomblock.setVisibility(View.INVISIBLE);
                entry.setVisibility(View.INVISIBLE);
            }
        });

        //set up the view
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void addCity(String name) {
        cities.add(name);
        cityAdapter.notifyDataSetChanged();
    }

    public void removeCity(String name) {
        cities.remove(name);
        cityAdapter.notifyDataSetChanged();
    }
}

