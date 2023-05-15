package com.clinton.loanapp;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public abstract class useroperations
{
    //login method
    @FormUrlEncoded
    @POST("login.php")
    public abstract Call<ServerResponse>login_method(@Field("username") String username, @Field("password") String password);

    //customer change password method
    @FormUrlEncoded
    @POST("changepassword.php")
    public abstract Call<ServerResponse>changepassword_method(@Field("newpassword") String newpassword, @Field("username") String username);

     //send message or support method
    @FormUrlEncoded
    @POST("messages.php")
    public abstract Call<MessageResponse>messages_method(@Field("heading") String heading, @Field("message") String message, @Field("opinion") String opinion, @Field("username") String username);

}
