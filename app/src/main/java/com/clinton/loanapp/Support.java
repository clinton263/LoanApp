package com.clinton.loanapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Support extends AppCompatActivity {

    //shared preference to send control number to db
    String SHARED_PREF_XML = "user_details_xml";
    //view declaration
    EditText heading;
    TextInputLayout message, opinion;
    Button send;
    LinearLayout linearLayout;
    TextView warning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

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


        //define action bar behaviours
        getSupportActionBar().setTitle("");
        getSupportActionBar().setSubtitle("Support");
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_baseline_app_registration_24);

//initialize views
        heading = findViewById(R.id.heading);
        message = findViewById(R.id.message);
        opinion = findViewById(R.id.opinion);
        send = findViewById(R.id.sendMessage);
        linearLayout = findViewById(R.id.linearLayout_message);
        warning = findViewById(R.id.warning);

//when heading is clicked prompt dialog
       heading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });


//when snd button is clicked, then validate data
       send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }

    //ALL METHODS START HERE:
    //validation method
    @SuppressLint("SetTextI18n")
    void validate()
{
    String heading_=heading.getText().toString();
    String message_=message.getEditText().getText().toString();

    if(message_.equals(""))
    {
        message.setError("Required");
    }
    else if(heading_.equals(""))
    {
        heading.setError("required");
    }
    else
    {
        send.setText("sending...");
        send.setClickable(false);
        sendMessage();
    }
}
//custom notification method
    public  void displayMessage(String putMessage)
    {
        Snackbar.make(linearLayout,putMessage,Snackbar.LENGTH_LONG).show();
    }

    //show alert dialog
    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Support.this);
        alertDialog.setTitle("select category");
        alertDialog.setIcon(R.drawable.ic_baseline_list_24);
        String[] items = {"Extend loan duration","Incorrect installments","Postpone loan duration","Transfer the loan","other"};
        int checkedItem = 1;
        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        heading.setText("Extend loan duration");
                        break;
                    case 1:
                        heading.setText("Incorrect installments");
                        break;
                    case 2:
                        heading.setText("Postpone loan duration");
                        break;
                    case 3:
                        heading.setText("Transfer the loan");
                        break;
                    case 4:
                        heading.setText("other");
                        break;
                }
            }

        });

        // Setting Positive "Yes" Btn
        alertDialog.setPositiveButton("select",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        Toast.makeText(getApplicationContext(),
                                        "selected", Toast.LENGTH_SHORT)
                                .show();
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    //menu items  selected
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
            case R.id.Logout:
                SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREF_XML,MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.clear();
                editor.apply();
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

    //send message method
    public void sendMessage()
    {
        //instantiate instance variables
        String heading_=heading.getText().toString();
        String message_=message.getEditText().getText().toString();
        String opinion_=opinion.getEditText().getText().toString();

        //get current login customer
        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREF_XML,MODE_PRIVATE);
        String username=sharedPreferences.getString("control_number",null);

        Call<MessageResponse>
                call=ServerConnection.getServerConnection().create(useroperations.class)
                .messages_method(heading_,message_,opinion_,username);

        //listen response from API  serverResponse

        call.enqueue(new Callback<MessageResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response)
            {
                //check server state
                if(response.code()==200)
                {
                    if(response.body().getStatus().equals("ok"))
                    {
                        if(response.body().getResultCode()==1)
                        {
                            warning.setVisibility(View.VISIBLE);
                            if(response.body().getMessage_status().equals("disapproved"))
                            {
                                warning.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.danger));
                            }
                            else
                            {
                                warning.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.sucess));
                            }
                            heading.setError("select another heading");
                            message.setError("This incident has already been recorded:  | status: "+response.body().getMessage_status());
                            send.setClickable(true);
                            send.setText("send");
                        }
                        else if(response.body().getResultCode()==0)
                        {
                            displayMessage("Message has been sent");
                            heading.setText("");
                            send.setClickable(true);
                            send.setText("send");
                            warning.setVisibility(View.GONE);
                        }
                    }
                }
                else
                {
                    displayMessage("SERVER RESPONSE FAILURE");
                    Intent intent=new Intent(getApplicationContext(),ActivityNotFound.class);
                    startActivity(intent);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    finish();
                }
            }
            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {

            }
        });
    }
    public void onBackPressed() {
        // super.onBackPressed();
        Toast.makeText(getApplicationContext(),"There is no back action",Toast.LENGTH_LONG).show();
        return;
    }
}