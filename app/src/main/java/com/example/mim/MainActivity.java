package com.example.mim;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import android.os.*;
import android.util.Log;
import android.content.Context;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.Holders.User;
import com.example.RecyclerAdapters.UserAdapter;
import com.example.mim.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itsaky.androidide.logsender.LogSender;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseAuth user;
    private RecyclerView revycler;
    private DatabaseReference reff;
    private ArrayList<User> list = new ArrayList<>();
    private String Userid;
    private User Current_user= new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Remove this line if you don't want AndroidIDE to show this app's logs
        LogSender.startLogging(this);
        super.onCreate(savedInstanceState);
        // Inflate and get instance of binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        revycler =binding.Recycler;
        revycler.setLayoutManager(new LinearLayoutManager(this));
        // set content view to binding's root
        setContentView(binding.getRoot());
        user = FirebaseAuth.getInstance();
        Userid = user.getCurrentUser().getUid();
        setSupportActionBar(binding.Toolbar);
        reff = FirebaseDatabase.getInstance().getReference("Users");
        pushOnline();
        reff.addValueEventListener(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot arg0) {
                       if(list.size()>0){
                           list.clear();
                       }
                        for(DataSnapshot snap: arg0.getChildren()){
                            User duser = snap.getValue(User.class);
                            if(!user.getCurrentUser().getUid().equals(duser.getUID())){
                               list.add(duser);
                            }else{
                                Current_user=duser;
                            }
                        }
                        UserAdapter adapter = new UserAdapter(list,MainActivity.this);
                        revycler.setAdapter(adapter);
                        adapter.notifyDataSetChanged();   
                        
                    }

                    @Override
                    public void onCancelled(DatabaseError arg0) {
                        
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu arg0) {
        // TODO: Implement this method
        new MenuInflater(this).inflate(R.menu.goole, arg0);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem arg0) {

        // TODO: Implement this metho
        switch (arg0.getItemId()) {
            case R.id.SignOUT:
                signOut();
                break;
            case R.id.Profile:
            Intent i=new Intent(MainActivity.this,EditProfile.class);
            i.putExtra("NAME",Current_user.getName());
            i.putExtra("BIO",Current_user.getBio());
            i.putExtra("URL",Current_user.getProfileURL());
            startActivity(i);
            break;
        }

        return super.onOptionsItemSelected(arg0);
    }

    void signOut() {
        pushOffline().addOnSuccessListener((Void v)->{
        Intent move = new Intent(MainActivity.this, LogIN.class);
        move.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        user.signOut();       
        startActivity(move);
        finish();
        });        
    }
    Task<Void> pushOffline(){
      return  reff.child(Userid).child("status").setValue("offline");
    }
    Task<Void> pushOnline(){
       return  reff.child(Userid).child("status").setValue("Online");
    }
    @Override
        protected void onPause() {
             super.onPause();
             pushOffline();
            // TODO: Implement this method
        }
        
    @Override
        protected void onResume() {
              super.onResume();
            // TODO: Implement this method
              pushOnline();
              
        }
        
}
