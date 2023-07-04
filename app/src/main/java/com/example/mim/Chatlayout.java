package com.example.mim;

import android.app.Notification;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.MimeTypeMap;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.Holders.Messages;
import com.example.Holders.NotificationModel;
import com.example.Holders.RootModel;
import com.example.RecyclerAdapters.chatAdapter;
import com.example.mim.databinding.LayoutChatlayoutBinding;
import com.example.retrofitsApI.ApiClient;
import com.example.retrofitsApI.ApiInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.itsaky.androidide.logsender.LogSender;
import java.util.ArrayList;

public class Chatlayout extends AppCompatActivity {
    private LayoutChatlayoutBinding binding;
    private String UID, MyUID = "";
    private FirebaseAuth auth;
    private String senderRoom, recieveRroom;
    private DatabaseReference reff, usr;
    private RecyclerView recycler;
    private ArrayList<Messages> list = new ArrayList<>();
    private Uri image;
    private MaterialToolbar tb;
    private String Profile,Name,BIO,FMC;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        // TODO: Implement this method
        LogSender.startLogging(this);
        auth = FirebaseAuth.getInstance();
        MyUID = auth.getCurrentUser().getUid();
        reff = FirebaseDatabase.getInstance().getReference("Chats");
        usr = FirebaseDatabase.getInstance().getReference("Users");
        binding = LayoutChatlayoutBinding.inflate(getLayoutInflater());
        recycler = binding.Recycleraview;
        tb = binding.MToolbar;
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setStackFromEnd(true);
        recycler.setLayoutManager(manager);
        setContentView(binding.getRoot());
        try {
            UID = getIntent().getExtras().getString("UID");
            Profile = getIntent().getExtras().getString("IMG");
            Name = getIntent().getExtras().getString("NAME");
            BIO = getIntent().getExtras().getString("BIO");
            FMC = getIntent().getExtras().getString("FMC");
            
        } catch (Exception e) {

        }
        senderRoom = UID + MyUID;
        recieveRroom = MyUID + UID;

        binding.Send.setOnClickListener(
                V -> {
                    if (!binding.Message.getText().toString().isEmpty()) {
                        sendMessage(binding.Message.getText().toString(), MyUID);
                    }
                });
        binding.Img.setOnClickListener(
                L -> {
                    Intent iny = new Intent(Intent.ACTION_GET_CONTENT);
                    iny.setType("image/*");
                    startActivityForResult(iny, 100);
                });

        reff.child(senderRoom)
                .addValueEventListener(
                        new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot arg0) {
                                if (list.size() > 0) {
                                    list.clear();
                                }
                                for (DataSnapshot snap : arg0.getChildren()) {
                                    Messages m = snap.getValue(Messages.class);
                                    list.add(m);
                                }
                                chatAdapter adapter = new chatAdapter(list, Chatlayout.this);
                                recycler.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(DatabaseError arg0) {}
                        });

        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.Text.setText(Name+"\n"+BIO);
        
        Glide.with(this)
                .asDrawable()
                .load(Profile)
                .circleCrop()
                .into(binding.IMG);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem arg0) {
        // TODO: Implement this method
        switch (arg0.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(arg0);
    }

    void sendMessage(String Text, String UId) {
        Messages m = new Messages(Text, UId);
        reff.child(senderRoom).child(reff.push().getKey()).setValue(m);
        reff.child(recieveRroom)
                .child(reff.push().getKey())
                .setValue(m)
                .addOnSuccessListener(
                        (Void v) -> {
                            binding.Message.setText("");
                            ApiClient.getClient().create(ApiInterface.class).sendNotification(new RootModel(FMC,new NotificationModel("New Message from"+FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),Text+"....")));
                        });
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
        // TODO: Implement this method
        if (arg0 == 100 && arg1 == RESULT_OK) {
            image = arg2.getData();
            uploadandSend(image);
        }
    }

    void uploadandSend(Uri img) {
        FirebaseStorage.getInstance()
                .getReference("Uploads")
                .child(
                        System.currentTimeMillis()
                                + "."
                                + MimeTypeMap.getSingleton()
                                        .getExtensionFromMimeType(
                                                getContentResolver().getType(img)))
                .putFile(img)
                .addOnSuccessListener(
                        new OnSuccessListener<UploadTask.TaskSnapshot>() {

                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot arg0) {
                                if (arg0 != null) {
                                    arg0.getMetadata()
                                            .getReference()
                                            .getDownloadUrl()
                                            .addOnSuccessListener(
                                                    (Uri u) -> {
                                                        Messages m =
                                                                new Messages(u.toString(), MyUID);
                                                        reff.child(senderRoom)
                                                                .child(reff.push().getKey())
                                                                .setValue(m);
                                                        reff.child(recieveRroom)
                                                                .child(reff.push().getKey())
                                                                .setValue(m);
                                                    });
                                }
                            }
                        });
    }

    Task<Void> pushOffline() {
        return usr.child(MyUID).child("status").setValue("offline");
    }

    Task<Void> pushOnline() {
        return usr.child(MyUID).child("status").setValue("Online");
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
