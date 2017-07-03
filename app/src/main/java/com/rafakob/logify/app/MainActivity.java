package com.rafakob.logify.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.rafakob.logify.Logify;
import com.rafakob.logify.okhttp3.LogifyInterceptor;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Logify.i("Test info message");
        Logify.d("Test debug message");


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LogifyInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://jsonplaceholder.typicode.com")
                .build();

        Api api = retrofit.create(Api.class);

        api.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                Log.d("Logify", response.toString());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public interface Api {
        @GET("/users")
        Call<List<User>> getUsers();

    }

    public static class User {
        int id;
        String name;
        String email;
        Address address;

        public static class Address {
            String stree;
            String city;
        }
    }
}
