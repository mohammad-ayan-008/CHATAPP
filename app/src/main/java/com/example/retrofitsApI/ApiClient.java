package com.example.retrofitsApI;
import com.google.android.datatransport.runtime.retries.Retries;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static  Retrofit client;
    private static  String  url ="https://fcm.googleapis.com/";
    
    public static Retrofit getClient(){
      if(client == null){
          client = new Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
       }
       return client;
    }
}
