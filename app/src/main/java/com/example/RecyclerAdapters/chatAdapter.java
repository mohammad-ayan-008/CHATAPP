package com.example.RecyclerAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.Holders.Messages;
import com.example.RecyclerAdapters.chatAdapter;
import com.example.mim.databinding.ChatBinding;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;
import kotlinx.coroutines.channels.Receive;

public class chatAdapter extends RecyclerView.Adapter<chatAdapter.Holder> {
   private ArrayList<Messages> list;
   private Context context;

   public chatAdapter(ArrayList<Messages> list, Context context) {
       this.list = list;
       this.context = context;
   }
    
    
    class Holder extends RecyclerView.ViewHolder {
        ChatBinding binding;

        public Holder(ChatBinding bind) {
            super(bind.getRoot());
            this.binding = bind;
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup arg0, int arg1) 
    {
        return new chatAdapter.Holder(ChatBinding.inflate(LayoutInflater.from(context),arg0,false));
    }

    @Override
    public void onBindViewHolder(Holder arg0, int arg1) {
        Messages m= list.get(arg1);
        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(m.getUID())){
           if(!m.getMessage().startsWith("https://")){
            arg0.binding.Recieved.setText(m.getMessage());
            arg0.binding.Sender.setVisibility(View.GONE);
            arg0.binding.SenderImage.setVisibility(View.GONE);
            arg0.binding.RecievedImage.setVisibility(View.GONE);
            }else{
               Glide.with(context).load(m.getMessage()).into(arg0.binding.RecievedImage);
               arg0.binding.Recieved.setVisibility(View.GONE);
               arg0.binding.Sender.setVisibility(View.GONE);
               arg0.binding.SenderImage.setVisibility(View.GONE);
            }
        }else{
          if(!m.getMessage().startsWith("https://")){
              arg0.binding.Sender.setText(m.getMessage());
              arg0.binding.Recieved.setVisibility(View.GONE);
              arg0.binding.SenderImage.setVisibility(View.GONE);
              arg0.binding.RecievedImage.setVisibility(View.GONE);
          }else{
               Glide.with(context).load(m.getMessage()).into(arg0.binding.SenderImage);
               arg0.binding.Recieved.setVisibility(View.GONE);
               arg0.binding.Sender.setVisibility(View.GONE);
               arg0.binding.RecievedImage.setVisibility(View.GONE);
          }      
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
