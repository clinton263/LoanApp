package com.clinton.loanapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//THIS CLASS ESTABLISHES CONNECTION

public class ServerConnection
{
    public static  final String SERVER_URL="https://192.168.88.241/debt/";
    private static Retrofit retrofit=null;
    // connection
    public static Retrofit getServerConnection()
    {
        if(retrofit==null)
        {
            retrofit=new Retrofit.Builder().baseUrl(SERVER_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return  retrofit;
    }
}
//Place root path followed by '/',