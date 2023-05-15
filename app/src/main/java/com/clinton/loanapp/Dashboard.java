package com.clinton.loanapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

//THIS CLASS IS HOME ACTIVITY
//DISPLAYS CUSTOMER INSTALLATIONS
//THE MODEL IS 'installmentInfo.java
//ADAPTER IS 'installmentInfoAdapter.java'

public class Dashboard extends AppCompatActivity {

    //get preference to select specific login customer
    String SHARED_PREF_XML="user_details_xml";

    //instantiate model and adapter
    private List<InstallmentInfoModel> list;
    private  InstallmentInfoAdapter adapter;
    private RecyclerView recyclerView;

    //warning alert dialog
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //initialize alert dialog
        builder = new AlertDialog.Builder(this);

        //if the user does not login, redirect to login
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_XML, MODE_PRIVATE);
        String customerNumber = sharedPreferences.getString("phone_number", null);
        if (customerNumber == null)
        {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();
        }
        //set action bar behaviours
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        getSupportActionBar().setSubtitle("Dashboard");
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_baseline_dashboard_24);

        //initializations
        recyclerView = findViewById(R.id.recycle_installment);
        list = new ArrayList<>();
        adapter = new InstallmentInfoAdapter(this, list);

        //re-structure recycle
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        //provide dialog
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("is loading ....");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        //send post request to API to fetch data
        StringRequest request = new StringRequest(Request.Method.POST, "https://192.168.88.241/debt/installments.php", new Response.Listener<String>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(String response) {
                dialog.dismiss();

                //handle connection exception issues
                try {
                    JSONArray array = new JSONArray(response);

                    //loop  incoming data
                    for (int loop = 0; loop < array.length(); loop++) {
                        JSONObject object = array.getJSONObject(loop);
                        list.add(new InstallmentInfoModel(object.getString("installed_amount"),
                                object.getString("control_number_fk"),
                                object.getString("date")));

                    }
                    adapter.notifyDataSetChanged();

                }

                //put exception objects to this block
                catch (Exception e) {
                    dialog.dismiss();
                    Toast.makeText(Dashboard.this, "No internet connection", Toast.LENGTH_LONG).show();
                }
            }
            //if response is failure give user the message
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                dialog.dismiss();
                builder.setCancelable(false);
                builder.setIcon(R.drawable.baseline_warning_24);
                builder.setMessage("no internet connection")
                        .setCancelable(false)
                        .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                                Toast.makeText(getApplicationContext(),"closed",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Continue", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(),"Empty",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Error");
                alert.show();


            }
        })

                //send this key to API, to select specific login customer
                //get current customer form shared preference
                //get customer control number as a key
                //assign control number as 'username' variable

        {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREF_XML,MODE_PRIVATE);
                String username=sharedPreferences.getString("control_number",null);
                params.put("username",username);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);

    }
//these two methods, 1: set options menu  2: listen selected item
    //for 'logout' item selected then clear current login customer
    //after clearing, redirect to login
    //then disable on back actions

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_file,menu);
        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREF_XML,MODE_PRIVATE);
        MenuItem menuItem=menu.findItem(R.id.profile_detail);
        menuItem.setTitle("welcome "+sharedPreferences.getString("fullname",null));
        return true;
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout:
                //clear shared preference when user logout
                SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREF_XML,MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.clear();
                editor.apply();
                //redirect to login activity
                Toast.makeText(getApplicationContext(), "you have successful logout ", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
                return  true;

            case R.id.installment:
                Intent intent2=new Intent(getApplicationContext(),accountInnfo.class);
                startActivity(intent2);
                return  true;

            case R.id.change_password:
                Intent intent3=new Intent(getApplicationContext(),changepassword.class);
                startActivity(intent3);
                return  true;

            case R.id.home:
                Intent intent4=new Intent(getApplicationContext(),Dashboard.class);
                startActivity(intent4);
                return  true;
            case R.id.support:
                Intent intent5=new Intent(getApplicationContext(),Support.class);
                startActivity(intent5);
                return  true;
            default:
                return  super.onOptionsItemSelected(item);

        }
    }

    public void onBackPressed() {
        // super.onBackPressed();
        Toast.makeText(getApplicationContext(),"There is no back action",Toast.LENGTH_LONG).show();
        return;
    }
}