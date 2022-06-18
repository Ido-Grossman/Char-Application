package com.example.android.api;

import com.example.android.Data.Contact;
import com.example.android.Data.User;
import com.example.android.entities.Message;
import com.example.android.entities.UserCred;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface WebServiceAPI {
    @GET("Contacts")
    Call<List<Contact>> getContacts(@Header("Authorization") String token);

    @POST("Contacts")
    Call<String> createContact(@Header("Authorization") String token, @Body Contact contact);

    @POST("Users/Register")
    Call<String> registerUser(@Body UserCred user);

    @POST("Users/Exists")
    Call<Void> checkIfUsernameExists(@Body String username);

    @POST("Users/Login")
    Call<String> logIn(@Body UserCred loginCred);

    @GET
    Call<List<Message>> getMessages(@Url String url);

    @POST
    Call<Void> postMessage(@Url String url);


}
