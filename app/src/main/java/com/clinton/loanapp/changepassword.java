package com.clinton.loanapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class changepassword extends AppCompatActivity {

    //user session

    String SHARED_PREF_XML="user_details_xml";
   //define Views

    TextInputLayout newPassword,confirmPassword;
    Button changePassword;
    ProgressDialog showProgress;
    LinearLayout notificationLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);

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
        //define ActionBar
        getSupportActionBar().setTitle("");
        getSupportActionBar().setSubtitle("Password");
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_baseline_screen_lock_portrait_24);

        //initialize Views

        showProgress=new ProgressDialog(this);
        newPassword=(TextInputLayout)findViewById(R.id.newpassword);
        confirmPassword=(TextInputLayout)findViewById(R.id.confirmpassword);
        changePassword=(Button) findViewById(R.id.change_password_btn);
        notificationLayout=(LinearLayout)findViewById(R.id.linearLayout_changepasword);

        //get action click  listener

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //validate empty fields
                dataValidation();
            }
        });
    }

    //data validation segment

    public  void dataValidation()
    {
        String newPassword_=newPassword.getEditText().getText().toString().trim();
        String confirmPassword_=confirmPassword.getEditText().getText().toString().trim();

        //secure password and escape string special characters
        String securedPassword = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        if (!newPassword_.matches(securedPassword)) {
            newPassword.setError("password is too weak");
            changePassword.setText("Retry...");
        }

        else if(!newPassword_.equals(confirmPassword_))
        {
            displayMessage("Passwords did not match");
            confirmPassword.setError("Error !");
            changePassword.setText("Retry...");

        }
        else if(newPassword_.equals("") || confirmPassword_.equals(""))
        {
            displayMessage("all fields are required");
            changePassword.setText("Retry...");
        }
        else
        {

            //keep user patient
            showProgress.setMessage("please wait...");
            showProgress.setCanceledOnTouchOutside(false);
            showProgress.show();
            //send POST request to API
            sendRequest();
        }
    }
    //message notification segement

    public  void displayMessage(String putMessage)
    {
        Snackbar.make(notificationLayout,putMessage,Snackbar.LENGTH_LONG).show();
    }
//send request to API
    public void sendRequest()
    {
        //instantiate instance variables

        String newPassword_=newPassword.getEditText().getText().toString().trim();

        //get current login customer

        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREF_XML,MODE_PRIVATE);
        String username=sharedPreferences.getString("control_number",null);

        Call<ServerResponse>
                call=ServerConnection.getServerConnection().create(useroperations.class)
                .changepassword_method(newPassword_,username);

        //listen response from API  serverResponse

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response)
            {
                //check server state

                if(response.code()==200)
                {
                    if(response.body().getStatus().equals("ok"))
                    {
                        if(response.body().getResultCode()==1)
                        {
                            displayMessage("new password must not be the same as installed one");
                            showProgress.dismiss();
                        }
                        else if(response.body().getResultCode()==0)
                        {
                            displayMessage("You have successful changed old password");
                            showProgress.dismiss();


                        }
                    }
                }
                else
                {
                    displayMessage("SERVER RESPONSE FAILURE");
                    showProgress.dismiss();
                    Intent intent=new Intent(getApplicationContext(),ActivityNotFound.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });
    }

    //set menu options  item selected
    //set logout on item clicked
    //shift activity

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_file,menu);
        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREF_XML,MODE_PRIVATE);
        MenuItem menuItem=menu.findItem(R.id.profile_detail);
        menuItem.setTitle("Welcome: "+sharedPreferences.getString("fullname",null));
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
    //end menu options ---
    public void onBackPressed() {
        // super.onBackPressed();
        Toast.makeText(getApplicationContext(),"There is no back action",Toast.LENGTH_LONG).show();
        return;
    }
}