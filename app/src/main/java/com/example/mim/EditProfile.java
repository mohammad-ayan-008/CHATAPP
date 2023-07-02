package com.example.mim;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.mim.databinding.LayoutEditProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class EditProfile extends AppCompatActivity{
       LayoutEditProfileBinding binding;
       private DatabaseReference reff;
       private Uri uri;
       private StorageReference storage;
       @Override
        protected void onCreate(Bundle arg0) {
            super.onCreate(arg0);
            // TODO: Implement this method
            binding=LayoutEditProfileBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            Intent rec=  getIntent();
            storage = FirebaseStorage.getInstance().getReference("Profiles");
            reff = FirebaseDatabase.getInstance().getReference("Users");
            binding.uname.setText(rec.getExtras().getString("NAME"));
            binding.Bio.setText(rec.getExtras().getString("BIO"));
            Glide.with(this).load(rec.getExtras().getString("URL")).into(binding.editprofileimage);
            binding.editprofileimage.setOnClickListener(V->{
                 Intent pic= new Intent(Intent.ACTION_GET_CONTENT);
                 pic.setType("image/*");
                 startActivityForResult(pic,100);
            });
           binding.save.setOnClickListener((View v)->{
              if(!TextUtils.isEmpty(binding.uname.getText())||!TextUtils.isEmpty(binding.Bio.getText())){
                saveChanges(uri);
                
               }     
           });
        
        }
    
    void saveChanges(Uri image){
       if(uri!=null){
           Toast.makeText(getApplicationContext(),"Hold A sec ",0).show();
           storage.child(FirebaseAuth.getInstance().getCurrentUser().getUid()+"."+MimeTypeMap.getSingleton().getExtensionFromMimeType(getContentResolver().getType(image))).putFile(image).addOnSuccessListener((UploadTask.TaskSnapshot s)->{
             s.getMetadata().getReference().getDownloadUrl().addOnSuccessListener((Uri U)->{
                  reff.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("name").setValue(binding.uname.getText().toString());
                  reff.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("profileURL").setValue(U.toString());     
                  reff.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("bio").setValue(binding.Bio.getText().toString()).addOnSuccessListener(L->{
                     finish();
                 });      
                            
              });
           });
       }else{
          reff.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("name").setValue(binding.uname.getText().toString());
          reff.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("bio").setValue(binding.Bio.getText().toString()).addOnSuccessListener(L->{
             finish();       
          });          
       }
    }
    
    
        @Override
        protected void onActivityResult(int arg0, int arg1, Intent arg2) {
            super.onActivityResult(arg0, arg1, arg2);
            // TODO: Implement this method
            if(arg0==100&& arg1==RESULT_OK){
                uri=arg2.getData();
                binding.editprofileimage.setImageURI(uri);
            }
        }
        
        
}
