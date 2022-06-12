package com.example.android.api;

import com.example.android.MyApp;
import com.example.android.R;
import com.example.android.entities.User;
import com.example.android.entities.UserCred;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserApi {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    Gson gson;

    public UserApi() {
        gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApp.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void create(UserCred userCred){
        Call<String> call = webServiceAPI.registerUser(userCred);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String token = response.body();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
    public void checkIfUsernameExists(String username, UserCred newUser, UserApi obj){
        Call<Void> call = webServiceAPI.checkIfUsernameExists(username);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                // if there is a username in db already we return true
                if (response.isSuccessful()){
                    System.out.println("Existsssss!");
                    // else there is no username with this name so we create one new
                } else {
                    obj.create(newUser);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
