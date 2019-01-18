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
    
    
    //the new code to replace the above one, i put it so may be u can compare it and see it both are not giving the required result anyways
    TextView lat;
    TextView lon;
    String result="1000";
    TextView showResponse;
    Spinner spinner;
    Button OSMButton;
    JSONObject test = null;
    int radius;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OSMButton = findViewById(R.id.button);
        OSMButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callOSM();
            }
        });
        spinner=(Spinner)findViewById(R.id.spinner);
        String[] distancevalues= {"5000","10000","15000"};
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,distancevalues);
        spinner.setAdapter(adapter);

        int value = Integer.parseInt((String) spinner.getSelectedItem());
        showResponse =findViewById(R.id.testText);
    }

    void callOSM()  {


        final NetworkAsyncTask httpsTask = new NetworkAsyncTask();
        httpsTask.execute();
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    final Object response = httpsTask.get();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showResponse.setText(Objects.toString(response));
                            int value = Integer.parseInt((String) spinner.getSelectedItem());
                            httpsTask.setvalue(value);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    }


}
