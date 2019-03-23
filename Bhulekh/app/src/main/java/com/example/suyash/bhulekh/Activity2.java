package com.example.suyash.bhulekh;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Activity2 extends AppCompatActivity {


    TextView textView9, textView10, textView11, textView13, textView14, textView15, textView12, textView16, textView17, textView18, textView19, textView20;
    EditText editText;
    Button button;
    ProgressDialog progressDialog;
    DatabaseHelper db;
    StringBuilder buffer =null;
    Webservice1 wb;
    int i, c=0;

    static ArrayList<String> kn = new ArrayList<String>();
    static ArrayList<String> ar = new ArrayList<String>();
    static ArrayList<String> on = new ArrayList<String>();
    static ArrayList<String> fa = new ArrayList<String>();
    static ArrayList<String> ad = new ArrayList<String>();


    @Override
    protected void onStop() {
        super.onStop();
        this.deleteDatabase(DatabaseHelper.DATABASE_NAME);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        progressDialog = new ProgressDialog(Activity2.this);

        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);




        db = new DatabaseHelper(this);


        button =(Button)findViewById(R.id.button);
        editText = (EditText)findViewById(R.id.editText);

        textView9 = (TextView)findViewById(R.id.textView9);
        textView10 = (TextView)findViewById(R.id.textView10);
        textView11= (TextView)findViewById(R.id.textView11);
        textView13= (TextView)findViewById(R.id.textView13);
        textView14= (TextView)findViewById(R.id.textView14);
        textView15= (TextView)findViewById(R.id.textView15);
        textView12 = (TextView)findViewById(R.id.textView12);
        textView16 = (TextView)findViewById(R.id.textView16);
        textView17 = (TextView)findViewById(R.id.textView17);
        textView18 = (TextView)findViewById(R.id.textView18);
        textView19 = (TextView)findViewById(R.id.textView19);
        textView20 = (TextView)findViewById(R.id.textView20);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                showData_Khata_Table();
                showData_Gata_Table();
                showData_Owner_Table();


            }
        });


    }




    @Override
    protected void onStart() {
        super.onStart();

        wb = new Webservice1();
       wb.execute();


    }


    public  void showData_Khata_Table(){
        i = Integer.parseInt(editText.getText().toString());
        Cursor res = db.getData_Khata_Table();
        res.moveToPosition(i-1);
        textView9.setText(res.getString(0));
        textView10.setText(res.getString(1));
        textView11.setText(res.getString(2));
        textView12.setText(res.getString(3));
        textView13.setText(MainActivity.district_name_list.get(MainActivity.d));
        textView14.setText(MainActivity.tehsil_name_list.get(MainActivity.t));
        textView15.setText(MainActivity.village_name_list.get(MainActivity.v));
    }

    public void showData_Gata_Table(){

        Cursor res = db.getData_Gata_Table();

        kn.clear();
        ar.clear();



        while ((res.moveToNext())){

            int j = Integer.parseInt(res.getString(0));
            if(j==i){
                kn.add(res.getString(1));
                ar.add(res.getString(3));
                for(int i=0;i<=10;i++){
                    res.moveToNext();
                    if(Integer.parseInt(res.getString(0))==j){
                        kn.add(res.getString(1));
                        ar.add(res.getString(3));
                    }

                }
            }

        }

        textView19.setText("");
        for (int j = 0; j < kn.size(); j++){
            textView19.append(kn.get(j)+", ");
        }
        textView20.setText("");
        for (int j = 0; j < kn.size(); j++){
            textView20.append(ar.get(j)+", ");
        }

    }

    public void showData_Owner_Table(){

        Cursor res = db.getData_Owner_Table();


        on.clear();
        fa.clear();
        ad.clear();


        while ((res.moveToNext())){
            int j = Integer.parseInt(res.getString(0));
            if(j==i){
                on.add(res.getString(1));
                fa.add(res.getString(2));
                ad.add(res.getString(3));

                for(int i=0;i<=10;i++){
                    res.moveToNext();
                    if(Integer.parseInt(res.getString(0))==j){
                        on.add(res.getString(1));
                        fa.add(res.getString(2));
                        ad.add(res.getString(3));
                    }

                }
            }

        }

        textView16.setText("");
        for (int j = 0; j < on.size(); j++){
            textView16.append(on.get(j)+", ");
        }
        textView17.setText("");
        for (int j = 0; j < fa.size(); j++){
            textView17.append(fa.get(j)+", ");
        }
        textView18.setText("");
        for (int j = 0; j < ad.size(); j++){
            textView18.append(ad.get(j)+", ");
        }

    }

    public class Webservice1 extends AsyncTask<Void, Void, Void> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();


        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.hide();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;
            buffer = new StringBuilder();
            try {
                URL url = new URL("http://164.100.230.206/Khatauni_App/service?village="+ MainActivity.village_code_list.get(MainActivity.v)+"&khata=all&api_key=apikey");
                try {
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();


                    InputStream stream = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));

                    String line=null;


                    while ((line = reader.readLine()) != null) {

                        buffer.append(line).append("\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } finally {
                if (connection != null)
                    connection.disconnect();

                try {
                    if (reader != null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try{
                JSONObject json = new JSONObject(buffer.toString());
                JSONObject json2 = json.getJSONObject("DATA");
                JSONArray jsonx = json2.getJSONArray("khata");

                for(int i=0;i<jsonx.length();i++){
                    JSONObject json4 = jsonx.getJSONObject(i);

                    db.insertData_Khata_Table(json4.getString("khata_number"), json4.getString("part"), json4.getString("fasli_year"), json4.getString("land_type"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }



            connection = null;
            reader = null;
            buffer = new StringBuilder();
            try {
                URL url = new URL("http://164.100.230.206/Khatauni_App/service?village="+ MainActivity.village_code_list.get(MainActivity.v)+"&gata=all&api_key=apikey");
                try {
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();

                    InputStream stream = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));

                    String line=null;


                    while ((line = reader.readLine()) != null) {

                        buffer.append(line).append("\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } finally {
                if (connection != null)
                    connection.disconnect();
                try {
                    if (reader != null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try{
                JSONObject json = new JSONObject(buffer.toString());
                JSONObject json2 = json.getJSONObject("DATA");
                JSONArray jsony = json2.getJSONArray("gata");

                for(int i=0;i<jsony.length();i++){
                    JSONObject json4 = jsony.getJSONObject(i);
                    db.insertData_Gata_Table(json4.getString("khata_number"), json4.getString("khasra_no"), json4.getString("seq_no"), json4.getString("area"), json4.getString("yr_co_ten"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }



            connection = null;
            reader = null;
            buffer = new StringBuilder();
            try {
                URL url = new URL("http://164.100.230.206/Khatauni_App/service?village="+ MainActivity.village_code_list.get(MainActivity.v)+"&owner=all&api_key=apikey");
                try {
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();

                    InputStream stream = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));

                    String line=null;


                    while ((line = reader.readLine()) != null) {

                        buffer.append(line).append("\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } finally {
                if (connection != null)
                    connection.disconnect();
                try {
                    if (reader != null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try{
                JSONObject json = new JSONObject(buffer.toString());
                JSONObject json2 = json.getJSONObject("DATA");
                JSONArray jsony = json2.getJSONArray("owner");

                for(int i=0;i<jsony.length();i++){
                    JSONObject json4 = jsony.getJSONObject(i);
                    db.insertData_Owner_Table(json4.getString("khata_number"), json4.getString("name"), json4.getString("father"), json4.getString("address"), json4.getString("owner_no"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;

        }
    }



}












