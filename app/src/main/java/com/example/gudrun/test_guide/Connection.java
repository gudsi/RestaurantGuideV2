package com.example.gudrun.test_guide;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class Connection extends Service  implements LocationListener {
    String radius;
    double pLong;
    double pLat;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NetworkAsyncTask  net= new NetworkAsyncTask ();
        Toast.makeText(getApplicationContext(),""+net,Toast.LENGTH_SHORT).show();
        radius= intent.getStringExtra("Value");

        this.stopSelf();
        return START_STICKY;
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location!=null){
            pLat=location.getLatitude();
            pLong=location.getLongitude();
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    class NetworkAsyncTask extends AsyncTask {

        String response = "";

        @Override
        protected Object doInBackground(Object[] objects) {
            URL url = null;

            try {
                url = new URL("https://lz4.overpass-api.de/api/interpreter");
                HttpsURLConnection httpsCon = (HttpsURLConnection) url.openConnection();
                httpsCon.setDoOutput(true);
                httpsCon.setRequestMethod("PUT");
                OutputStreamWriter out = new OutputStreamWriter(
                        httpsCon.getOutputStream());

                out.write("<osm-script>\n" +
                        "  <query type=\"node\">\n" +
                        "    <has-kv k=\"amenity\" v=\"restaurant\"/>\n" +
                        "<has-kv k=\"wheelchair\" v=\"yes\"/>\n"+"[out:json]"+
                        "<around radius ="+radius+" lat="+pLat+" lon="+pLong+"/>\n" +
                        "  </query>\n" +
                        "  <print/>\n" +
                        "</osm-script>");
                out.close();

                int responseCode=httpsCon.getResponseCode();
                System.out.println(httpsCon.getContentType());
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br=new BufferedReader(new InputStreamReader(httpsCon.getInputStream()));
                    while ((line=br.readLine()) != null) {
                        response+=line;
                    }
                }
                else {
                    response="";
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

    }
}
