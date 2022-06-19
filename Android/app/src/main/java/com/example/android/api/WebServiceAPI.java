package com.example.android.api;

import com.example.android.Data.Contact;
import com.example.android.Data.ContactPost;
import com.example.android.Data.Content;
import com.example.android.Data.Message;
import com.example.android.Data.UserCred;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface WebServiceAPI {
    @GET("Contacts")
    Call<List<Contact>> getContacts(@Header("Authorization") String token);

    @POST("Contacts")
    Call<Void> createContact(@Header("Authorization") String token, @Body Contact contact);

    @POST("Users/Register")
    Call<String> registerUser(@Body UserCred user);

    @POST("Users/Exists")
    Call<Void> checkIfUsernameExists(@Body String username);

    @POST("Users/Login")
    Call<String> logIn(@Body UserCred loginCred);

    @POST("Contacts/{id}/messages")
    Call<Void> postMessage(@Header("Authorization") String token, @Path("id") String id, @Body Content message);

    @POST("Invitations")
    Call<Void> inviteContact(@Header("Authorization") String token, @Body ContactPost contact);

    @POST("Transfer")
    Call<Void> transferMessage(@Header("Authorization") String token, @Body ContactPost contactPost);

    @GET("Users/Logout")
    Call<Void> logOut(@Header("Authorization") String token);

    @GET("Contacts/{id}/messages")
    Call<List<Message>> getMessages(@Header("Authorization") String token, @Path("id") String id);

}
