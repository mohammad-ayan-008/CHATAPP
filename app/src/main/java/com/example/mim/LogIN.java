package com.example.mim;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.Holders.User;
import com.example.mim.MainActivity;
import com.example.mim.databinding.LayoutLoginBinding;
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
import com.itsaky.androidide.logsender.LogSender;

public class LogIN extends AppCompatActivity {
    
    LayoutLoginBinding binding;
    TextInputEditText Name, Email, pass;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reff;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        // TODO: Implement this method
        LogSender.startLogging(this);
        binding = LayoutLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        pass = binding.LPass;
        Email = binding.LEmail;
        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null){
           Intent move = new Intent(LogIN.this,MainActivity.class);
                  move.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  startActivity(move);
                  finish();
        }
        reff=  FirebaseDatabase.getInstance().getReference("Users");
        binding.LLogin.setOnClickListener(
                (View v) -> {
                    String p = pass.getText().toString();
                    String e = Email.getText().toString();
                    if ( !TextUtils.isEmpty(p) || !TextUtils.isEmpty(e)) {
                        signUp(e,p);
                    }
                });
        binding.movesignups.setOnClickListener((View v)->{
           Intent move = new Intent(LogIN.this,SIGNUP.class);
                  move.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  startActivity(move);
                  finish();
        });
    }

    void signUp(final String email, final String pass) {
        auth.signInWithEmailAndPassword(email, pass)
            .addOnFailureListener(new OnFailureListener(){
          @Override
          public void onFailure(Exception arg0) {
            Toast.makeText(getApplicationContext(),arg0.getMessage().toString(),0).show();
          }
          })     
             .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                           @Override
                            public void onComplete(Task<AuthResult> arg0) {
                                 if(arg0.isSuccessful()){
                                         Intent move = new Intent(LogIN.this,MainActivity.class);
                                         move.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                         startActivity(move);
                                         finish();
                                 }
                            }
                        });
    }
}
