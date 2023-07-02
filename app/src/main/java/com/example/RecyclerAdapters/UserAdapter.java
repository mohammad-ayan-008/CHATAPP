package com.example.RecyclerAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.Holders.User;
import com.example.RecyclerAdapters.UserAdapter;
import com.example.mim.Chatlayout;
import com.example.mim.databinding.UserBinding;
import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.Holder> {

    private ArrayList<User> list;
    private Context context;

    public UserAdapter(ArrayList<User> list, Context context) {
        this.list = list;
        this.context = context;
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        UserBinding binding;

        Holder(UserBinding bind) {
            super(bind.getRoot());
            this.binding = bind;
            bind.coordinator.setOnClickListener(this);
        }

        @Override
        public void onClick(View arg0) {
            Intent i= new Intent(context,Chatlayout.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("UID",list.get(getAdapterPosition()).getUID());
            i.putExtra("IMG",list.get(getAdapterPosition()).getProfileURL());
            i.putExtra("NAME",list.get(getAdapterPosition()).getName());
            i.putExtra("BIO",list.get(getAdapterPosition()).getBio());
            context.startActivity(i);
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup arg0, int arg1) {
        UserBinding binding = UserBinding.inflate(LayoutInflater.from(context), arg0, false);
        return new UserAdapter.Holder(binding);
    }

    @Override
    public void onBindViewHolder(Holder arg0, int arg1) {
        User user = list.get(arg1);
        arg0.binding.nameText.setText(user.getName());
        arg0.binding.statusText.setText(user.getStatus());
        Glide.with(context).load(user.getProfileURL()).into(arg0.binding.profileImage);
        arg0.binding.BIO.setText(user.getBio());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
