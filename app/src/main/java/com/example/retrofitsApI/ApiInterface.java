package com.example.retrofitsApI;
import com.example.Holders.RootModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {
  
    @Headers({"Authorization:key="+Constants.Server_key,Constants.Content})
    @POST("/fcm/send")
    Call<ResponseBody> sendNotification(@Body RootModel model);
    
}
