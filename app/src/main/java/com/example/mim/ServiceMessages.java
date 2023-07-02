package com.example.mim;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.os.IBinder;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import com.example.Holders.Messages;
import com.example.mim.notificationchannels;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Context;

public class ServiceMessages extends Service {
    private Notification notify;
    private NotificationManager manager;
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
    @Override
        public void onCreate() {
            super.onCreate();
            // TODO: Implement this method
        manager = getSystemService(NotificationManager.class);
        notify = new NotificationCompat.Builder(getApplicationContext(),notificationchannels.CHANNEL)
                .setSmallIcon(R.drawable.image)
                .setContentIntent(PendingIntent.getActivity(getApplicationContext(),0,new Intent(getApplicationContext(),MainActivity.class),PendingIntent.FLAG_UPDATE_CURRENT))
                .setContentTitle("New Message ")
                .build();
                
        }
        
    
    @Override
        public int onStartCommand(Intent arg0, int arg1, int arg2) {
            // TODO: Implement this metho
            FirebaseDatabase.getInstance().getReference("Chats").addValueEventListener(new ValueEventListener(){
                 @Override
                 public void onDataChange(DataSnapshot arg0) {
                    
                    Messages m= arg0.getValue(Messages.class);
                    
                 }
                 @Override
                 public void onCancelled(DatabaseError arg0) {
                 }
                                                                                                                   
        });
            return Service.START_NOT_STICKY;
        }
        
}
