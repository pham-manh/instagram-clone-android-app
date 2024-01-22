package com.example.myapplication.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.myapplication.BR;
import com.example.myapplication.R;
import com.example.myapplication.adapter.UserAdapter.UserViewHolder;
import com.example.myapplication.data.model.User;
import com.example.myapplication.databinding.UserItemBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {
    private List<User> users = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private FirebaseUser fireBaseUser;

    public UserAdapter() {
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UserItemBinding itemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.user_item,
                parent,
                false
        );
        return new UserViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, @SuppressLint("RecyclerView") int position) {
        fireBaseUser = FirebaseAuth.getInstance().getCurrentUser();
        User userModel = users.get(position);
        holder.bind(userModel);
        holder.binding.btnFollow.setVisibility(View.VISIBLE);
        isFollowed(userModel.getId(), holder.binding.btnFollow);
        if (userModel.getId().equals(fireBaseUser.getUid())) {
            holder.binding.btnFollow.setEnabled(false);
        }

        holder.binding.btnFollow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("SearchViewModel - Adapter", "Click Item =" + position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public class UserViewHolder extends ViewHolder {
        private final UserItemBinding binding;

        public UserViewHolder(UserItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }

        public void bind(Object obj) {
            binding.setVariable(BR.user, obj);
            binding.executePendingBindings();
        }
    }

    private void isFollowed(String id, Button followBtn) {
        DatabaseReference mref = FirebaseDatabase
                .getInstance("https://instagram-clone-784ff-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference()
                .child("Follow")
                .child(fireBaseUser.getUid());

        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(id).exists()) {
                    followBtn.setText("Following");
                } else {
                    followBtn.setText("Follow");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
