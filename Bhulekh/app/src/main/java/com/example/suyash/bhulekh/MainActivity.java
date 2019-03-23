package com.example.suyash.bhulekh;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {


    static int d, t, p, v;

   static ProgressDialog progressDialog;


    StringBuilder buffer =null;

    static  ArrayList<String> district_name_list = new ArrayList<>();
    static ArrayList<String>tehsil_name_list = new ArrayList<>();
    static ArrayList<String>pargana_name_list = new ArrayList<>();
    static ArrayList<String>village_name_list = new ArrayList<>();

    ArrayList<String>district_code_list = new ArrayList<>();
    ArrayList<String>tehsil_code_list = new ArrayList<>();
    ArrayList<String>pargana_code_list = new ArrayList<>();
    static ArrayList<String>village_code_list = new ArrayList<>();


    Webservice wb=null;



    TextView textView2, textView3, textView4;
    Spinner spinner1, spinner2, spinner3, spinner4;
    Button button1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("This may take a moment");
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.actionbar_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.app_bar_switch:

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("Contact Information");

                builder.setIcon(R.drawable.ic_phone_black_24dp);
                builder.setMessage("Computer Cell\n" +
                        "Board Of Revenue\n" +
                        "Lucknow, Uttar Pradesh\n" +
                        "✉ borlko@nic.in\n" +
                        "✆ 0522-2217155");
                builder.setPositiveButton("OK", null);
                builder.setCancelable(true);
                builder.show();
                return true;
            case R.id.app_bar_switch2:
                String url = "http://upbhulekh.gov.in/public/public_ror/Public_ROR.jsp";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_main);

        if(!isNetworkAvailable(this)){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setIcon(R.drawable.ic_warning);
            builder.setTitle("No Internet Connectivity");
            builder.setMessage("Please check your Internet Connection");
            builder.show();

        }

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        spinner4 = (Spinner) findViewById(R.id.spinner4);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);


        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), Activity2.class);
                finish();
                startActivity(intent);


            }
        });

        Data_Spinner1();

    }




    // function returns true if internet is active else false
    public boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public void Data_Spinner1(){
        district_name_list.clear();
        district_code_list.clear();
        wb = new Webservice();

        try {
            wb.execute("http://164.100.230.206/WebService_Edistrict/service?district=all&api_key=apikey&type=json").get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        try {
            JSONObject json = new JSONObject(buffer.toString());
            JSONObject json2 = json.getJSONObject("DATA");
            JSONArray jsonArray = json2.getJSONArray("district");
            for (int i = 0; i <= jsonArray.length(); i++) {
                JSONObject finalObject = jsonArray.getJSONObject(i);
                String dn = finalObject.getString("district_name_hindi");
                String j = finalObject.getString("district_code_census");
                district_name_list.add(dn);
                district_code_list.add(j);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                d = i;

                    Data_Spinner2();



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, district_name_list);
        spinner1.setAdapter(adapter);
    }

    public void Data_Spinner2() {
        wb = new Webservice();
        tehsil_code_list.clear();
        tehsil_name_list.clear();
        try {
            wb.execute("http://164.100.230.206/WebService_Edistrict/service?district=" + district_code_list.get(d) + "&tehsil=all&api_key=apikey&type=json").get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        try {
            JSONObject json = new JSONObject(buffer.toString());
            JSONArray json2 = json.getJSONArray("DATA");

            for (int i = 0; i <= json2.length(); i++) {
                JSONObject json3 = json2.getJSONObject(i);
                JSONObject json4 = json3.getJSONObject("tehsil");
                String tn = json4.getString("tname");
                tehsil_name_list.add(tn);
                String j = json4.getString("tehsil_code_census");
                tehsil_code_list.add(j);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                t = i;
                Data_Spinner3();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, tehsil_name_list);
        spinner2.setAdapter(adapter);

    }

    public void Data_Spinner3() {
        wb = new Webservice();
        pargana_code_list.clear();
        pargana_name_list.clear();

        try {
            wb.execute("http://164.100.230.206/WebService_Edistrict/service?district="+district_code_list.get(d)+"&tehsil="+tehsil_code_list.get(t)+"&pargana=all&api_key=apikey&type=json").get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        try {
            JSONObject json = new JSONObject(buffer.toString());
            JSONArray json2 = json.getJSONArray("DATA");

            for (int i = 0; i <=json.length(); i++) {
                JSONObject json3 = json2.getJSONObject(i);
                JSONObject json4 = json3.getJSONObject("pargana");
                String tn = json4.getString("pname");
                pargana_name_list.add(tn);
                String j = json4.getString("pargana_code_new");
                pargana_code_list.add(j);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                p=i;
                Data_Spinner4();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, pargana_name_list);
        spinner3.setAdapter(adapter);

    }

    public void Data_Spinner4(){
        village_name_list.clear();
        village_code_list.clear();
        wb = new Webservice();

        try {
            wb.execute("http://164.100.230.206/WebService_Edistrict/service?district="+district_code_list.get(d)+"&tehsil="+tehsil_code_list.get(t)+"&pargana="+pargana_code_list.get(p)+"&village=all&api_key=apikey&type=json").get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        try {
            JSONObject json = new JSONObject(buffer.toString());
            JSONArray json2 = json.getJSONArray("DATA");

            for (int i = 0; i <=json2.length(); i++) {
                JSONObject json3 = json2.getJSONObject(i);
                JSONObject json4 = json3.getJSONObject("village");
                String tn = json4.getString("vname");
                village_name_list.add(tn);
                String j = json4.getString("village_code_census");
                village_code_list.add(j);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                v=i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, village_name_list);
        spinner4.setAdapter(adapter);
        progressDialog.hide();
    }



    public class Webservice extends AsyncTask<String, Void, Void> {




        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Void doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            buffer = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
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
            return null;

        }

        @Override
        protected void onPostExecute(Void s) {

        super.onPostExecute(s);


        }
    }
}


