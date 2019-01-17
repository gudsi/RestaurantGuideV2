package com.example.gudrun.test_guide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;



public class MainActivity extends AppCompatActivity {
    Spinner spinner;
    Button search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner=findViewById(R.id.spinner);
        String[] distancevalues= {"5000","10000","15000","20000"};
        ArrayAdapter<String> adapter=
                new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,distancevalues);
        spinner.setAdapter(adapter);
        final String value = (String) spinner.getSelectedItem();

        search=findViewById(R.id.Search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(getApplicationContext(),Connection.class);
                i.putExtra("Value",value);
                startService(i);

                Intent j= new Intent(getApplicationContext()
                        ,List.class);
                startActivity(j);



            }
        });

    }

}
