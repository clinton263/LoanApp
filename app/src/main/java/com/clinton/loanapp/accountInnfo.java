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
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Adapter;
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

public class accountInnfo extends AppCompatActivity {
    private List<AccountInfoModel> list;
    private AccountInfoAdapter adapter;
    private RecyclerView recyclerView;
    String SHARED_PREF_XML = "user_details_xml";

    //warning alert dialog
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_innfo);

        //initialize alert dialog
        builder = new AlertDialog.Builder(this);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setSubtitle("My ccount");
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_baseline_info_24);

        //then initialize adapter and recycleView

        recyclerView = findViewById(R.id.recycle_info);
        list = new ArrayList<>();
        adapter = new AccountInfoAdapter(this, list);

        //then format the recycle view

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        //but also show progress, user to be patient while loading data from server

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("loading....");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        //then receive data from php file, then response can be success or failure

        StringRequest request = new StringRequest(Request.Method.POST, "https://192.168.88.241/debt/acount_info.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
             //make sure database exceptional are handled
                try {
                    JSONArray array = new JSONArray(response);
                    for (int loop = 0; loop < array.length(); loop++) {
                        JSONObject object = array.getJSONObject(loop);
                        list.add(new AccountInfoModel(object.getString("fullname"),
                                object.getString("resident"),
                                object.getString("phone_number"),
                                object.getString("loan_amount"),
                                object.getString("interest_percentage"),
                                object.getString("direct_cost"),
                                object.getString("taken_amount"),
                                object.getString("loan_date"),
                                object.getString("limit_date"),
                                object.getString("control_number"),
                                object.getString("actual_debt"),
                                object.getString("status"),
                                object.getString("interest_amount"),
                                object.getString("penalt"),
                                object.getString("actual_debt_penalt"),
                                object.getString("totalInstallments"),
                                object.getString("remain_amount")));

                    }
                    adapter.notifyDataSetChanged();

                }
               //whatever Error happens, then throw here in catch block
                catch (Exception e) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
                    alertDialog.setTitle("SERVER RESPONSE TIMEOUT");
                    alertDialog.setMessage("ERROR: " + e.getMessage());
                    alertDialog.show();
                    Intent intent = new Intent(getApplicationContext(), ActivityNotFound.class);
                    startActivity(intent);
                    finish();
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
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_XML, MODE_PRIVATE);
                String username = sharedPreferences.getString("control_number", null);
                // String username="1";
                params.put("username", username);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);

        // RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(request);

    }
    //finally, put error message

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_file, menu);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_XML, MODE_PRIVATE);
        MenuItem menuItem = menu.findItem(R.id.profile_detail);
        menuItem.setTitle("Welcome: " + sharedPreferences.getString("fullname", null));
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Logout:
                //clear shared preference when user logout
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_XML, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear().commit();
                editor.apply();
                //redirect to login activity
                Toast.makeText(getApplicationContext(), "you have successful logout ", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.installment:
                Intent intent2 = new Intent(getApplicationContext(), accountInnfo.class);
                startActivity(intent2);
                return true;

            case R.id.change_password:
                Intent intent3 = new Intent(getApplicationContext(), changepassword.class);
                startActivity(intent3);
                return true;

            case R.id.home:
                Intent intent4 = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent4);
                return true;
            case R.id.support:
                Intent intent5 = new Intent(getApplicationContext(), Support.class);
                startActivity(intent5);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onBackPressed() {
        // super.onBackPressed();
        Toast.makeText(getApplicationContext(), "There is no back action", Toast.LENGTH_LONG).show();
        return;
    }
}