package com.rafakob.logify.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.rafakob.logify.Logify;
import com.rafakob.logify.okhttp3.LogifyInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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

//        Logify.i("Test", "Test info message");
//        Logify.d("MainActivity", "Test debug message");
        findViewById(R.id.logify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logify.startActivity(MainActivity.this);
            }
        });

//        testApiCall();
    }

    private void testApiCall() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new LogifyInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://swapi.co")
                .build();

        Api api = retrofit.create(Api.class);

        api.getUsers().enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public interface Api {
        @GET("/api/people")
        Call<Void> getUsers();
    }


}
