package com.example.android.api;

import com.example.android.entities.Contact;
import com.example.android.entities.User;
import com.example.android.entities.UserCred;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface WebServiceAPI {
    @GET("Contacts")
    Call<List<Contact>> getContacts();

    @POST("Contacts")
    Call<Contact> createContact();

    @POST("Users/Register")
    Call<String> registerUser(@Body UserCred user);

    @POST("Users/Exists")
    Call<Void> checkIfUsernameExists(@Body String username);
}
