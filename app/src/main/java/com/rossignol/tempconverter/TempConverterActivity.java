package com.rossignol.tempconverter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

public class TempConverterActivity extends AppCompatActivity implements TextView.OnEditorActionListener {

    private TextView celciusDegrees;
    private EditText fahrenheitDegrees;

    private SharedPreferences savedValues;

    private String fahrenheitString = "";
//comment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_converter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fahrenheitDegrees = (EditText) findViewById(R.id.fahrenheitText);
        celciusDegrees = (TextView) findViewById(R.id.celciusText);


        //set the listener
        fahrenheitDegrees.setOnEditorActionListener(this);

        //saved state
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
    }

    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        if(actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {

            calculateAndDisplay();

        }

        //hides soft keyboard
        return false;
    }

    private void calculateAndDisplay() {

        fahrenheitString = fahrenheitDegrees.getText().toString();
        float fahrenheit;
        if(fahrenheitString.equals("")){
            fahrenheit = 0;
        } else {
            fahrenheit = Float.parseFloat(fahrenheitString);
        }
        float celcius;
        celcius = (fahrenheit - 32) * 5/9;
        NumberFormat degrees = NumberFormat.getIntegerInstance();
        celciusDegrees.setText(degrees.format(celcius));
    }

    @Override
    protected void onPause() {

        SharedPreferences.Editor editor = savedValues.edit();
        editor.putString("fahrenheitString", fahrenheitString);
        editor.commit();

        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        fahrenheitString = savedValues.getString("fahrenheitString", "");
        fahrenheitDegrees.setText(fahrenheitString);
        calculateAndDisplay();
    }
}
