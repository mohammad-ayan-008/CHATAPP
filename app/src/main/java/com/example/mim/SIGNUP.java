package com.example.mim;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import com.example.Holders.User;
import com.example.mim.databinding.LayoutSignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;



public class SIGNUP extends AppCompatActivity {
   LayoutSignUpBinding binding;
    TextInputEditText Name, Email, pass;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reff;
    private String Profile_Image="default";
    private StorageReference storage;
    private Uri image;
    private NotificationManager nmanager;
    private NotificationCompat.Builder compact;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        // TODO: Implement this method
        binding = LayoutSignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        storage = FirebaseStorage.getInstance().getReference("Profiles");
        Name = binding.SName;
        pass = binding.SPass;
        Email = binding.SEmail;
        nmanager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        auth = FirebaseAuth.getInstance();
        reff=  FirebaseDatabase.getInstance().getReference("Users");
        binding.SignUP.setOnClickListener(
                (View v) -> {
                    String n = Name.getText().toString();
                    String p = pass.getText().toString();
                    String e = Email.getText().toString();
                    if (!TextUtils.isEmpty(n) || !TextUtils.isEmpty(p) || !TextUtils.isEmpty(e)) {
                        signUp(n, p, e, "online");
                    }
                });
        
        binding.profileimage.setOnClickListener((View v)->{
             Intent fetch= new Intent(Intent.ACTION_GET_CONTENT);
             fetch.setType("image/*");
             startActivityForResult(fetch,100); 
        });
    }
    

    void signUp(final String name, final String pass, final String email, final String status) {
     
       if(image==null){
          Toast.makeText(this,"Image Required",0).show();
          return; 
       }else{
          Toast.makeText(getApplicationContext(),"It may take Few Seconds ",0).show();
       }
       
        auth.createUserWithEmailAndPassword(email, pass)
         
        .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(Task<AuthResult> arg0) {
                                 if(arg0.isSuccessful()){
                                         storage.child(FirebaseAuth.getInstance().getCurrentUser().getUid()+"."+MimeTypeMap.getSingleton().getExtensionFromMimeType(getContentResolver().getType(image))).putFile(image)
                                            .addOnProgressListener((UploadTask.TaskSnapshot arg)->{
                                              
                                              double progress = 100* (arg.getBytesTransferred()/arg.getTotalByteCount());
                                              if(arg.getBytesTransferred()==arg.getTotalByteCount()){
                                                compact.setProgress(100,(int)progress, false);
                                                nmanager.notify(100,compact.build()); 
                                              }else{
                                                compact.setProgress(100,(int)progress, false);
                                                compact.setOngoing(false);
                                                nmanager.notify(100,compact.build()); 
                                              } 
                                            })
                                             .addOnSuccessListener((UploadTask.TaskSnapshot s)->{
                                             s.getMetadata().getReference().getDownloadUrl().addOnSuccessListener((Uri v)->{
                                              Profile_Image = v.toString(); 
                                              FirebaseMessaging.getInstance().getToken().addOnSuccessListener(token->{
                                              User user = new User(name,pass,email,auth.getCurrentUser().getUid(),status,Profile_Image,"Heyy i am using Mim",token);
                                              reff.child(auth.getCurrentUser().getUid()).setValue(user)
                                              .addOnSuccessListener((Void m)->{
                                              Intent move = new Intent(SIGNUP.this,LogIN.class);
                                              move.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                              startActivity(move);
                                              finish();
                                              });
                                            });
                                        });
                                     });
                                 }
                            }
                        });
     }
    
    
       @Override
        protected void onActivityResult(int arg0, int arg1, Intent arg2) {
            super.onActivityResult(arg0, arg1, arg2);
            // TODO: Implement this method
            if(arg0==100 && arg1==RESULT_OK){
                image = arg2.getData();
                binding.profileimage.setImageURI(image);
            }
        }
        
    void setup_Notification() {
             compact =  new NotificationCompat.Builder(this, notificationchannels.CHANNEL)
                        .setSmallIcon(R.drawable.image)
                        .setOngoing(true)
                        .setOnlyAlertOnce(true)
                        .setContentTitle("Uploading Image")
                        .setContentText("Wait for it to finsih")
                        .setProgress(100, 0, false);
    }
    
}


