package com.clinton.loanapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//THIS CLASS PERFORMS LOGIN

public class MainActivity extends AppCompatActivity {

    //declare views
    TextInputLayout username, password;
    Button login;
    ProgressDialog progressDialog;

    //create/session shared preference
    String SHARED_PREF_XML = "user_details_xml";

    //date

    public static String formated_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //date

        Date date= Calendar.getInstance().getTime();
        SimpleDateFormat df=new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        formated_date=df.format(date);

        //define action bar behaviours
        getSupportActionBar().setTitle("");
        getSupportActionBar().setSubtitle(formated_date);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_baseline_login_24);

        //initialize views
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.btn_login);
        progressDialog = new ProgressDialog(this);

        //if the customer has already logged-in, then skip this activity.

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_XML, MODE_PRIVATE);
        String customerNumber = sharedPreferences.getString("phone_number", null);
        if (customerNumber != null) {
            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
            startActivity(intent);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();
        }

        //when button is clicked, validate data
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }

    //ALL METHODS START HERE:
    //validation
    public void validate() {
        if (username.getEditText().getText().toString().trim().equals("")) {
            username.setError("username is required");
            username.setHintEnabled(false);
        } else if (password.getEditText().getText().toString().trim().equals("")) {
            password.setError("password is required");
            password.setHintEnabled(false);
        } else {
            //call login method
            progressDialog.setMessage("please wait...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            login_method();
        }
    }

    public void login_method() {
        String username2 = username.getEditText().getText().toString();
        String password2 = password.getEditText().getText().toString();

        Call<ServerResponse> call = ServerConnection.getServerConnection().create(useroperations.class).login_method(username2, password2);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("ok")) {
                        if (response.body().getResultCode() == 1) {
                            //save user to shared pref
                            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_XML, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            // save the user
                            editor.putString("phone_number", response.body().getPhone_number());
                            editor.putString("fullname", response.body().getFullname());
                            editor.putString("control_number", response.body().getControl_number());
                            editor.apply();

                            progressDialog.dismiss();

                            Toast.makeText(MainActivity.this, "welcome " + response.body().getFullname(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                            startActivity(intent);
                            finish();
                        } else {
                            //username or password is not correct
                            Toast.makeText(getApplicationContext(), "Incorrect username or password", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            username.setError("invalid username");
                            password.setError("invalid password");
                            username.setHintEnabled(false);
                            password.setHintEnabled(false);
                        }
                    } else {
                        //status is not OK
                        Toast.makeText(getApplicationContext(), "server error", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        Intent intent = new Intent(getApplicationContext(), ActivityNotFound.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();

                    }
                } else {
                    //no connection
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });
    }
    public void onBackPressed() {
        // super.onBackPressed();
        Toast.makeText(getApplicationContext(),"There is no back action",Toast.LENGTH_LONG).show();
        return;
    }
}