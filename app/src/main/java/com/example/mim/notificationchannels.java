package com.example.mim;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class notificationchannels extends Application {
   private NotificationChannel channel;
   private NotificationManager manager;
   public static final String CHANNEL="MyChannel";
    
    @Override
        public void onCreate() {
            super.onCreate();
            // TODO: Implement this method
           if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
               channel = new NotificationChannel(CHANNEL,"Messages Alert",NotificationManager.IMPORTANCE_DEFAULT);
           }
            manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        
}
