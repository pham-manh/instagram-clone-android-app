package com.example.myapplication.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.myapplication.R;
import com.example.myapplication.adapter.UserAdapter.UserViewHolder;
import com.example.myapplication.data.model.User;
import com.example.myapplication.databinding.UserItemBinding;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {
    private List<User> users;
    private LayoutInflater layoutInflater;
    private UsersAdapterListener listener;
    private FirebaseUser fireBaseUser;

    public UserAdapter(List<User> users, UsersAdapterListener listener) {
        this.users = users;
        this.listener = listener;
    }

    public UserAdapter(List<User> users) {
        this.users = users;

    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        UserItemBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.user_item, parent, false);

        return new UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, @SuppressLint("RecyclerView") int position) {
        User user = users.get(position);

        holder.binding.setUser(user);
        holder.binding.btnFollow.setVisibility(View.VISIBLE);
        isFollowed(user.getId(), holder.binding.btnFollow);
        if (user.getId().equals(fireBaseUser.getUid())) {
            holder.binding.btnFollow.setVisibility(View.GONE);
        }
        holder.binding.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onUserClicked(users.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserViewHolder extends ViewHolder {
        private final UserItemBinding binding;

        public UserViewHolder(final UserItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
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

    public interface UsersAdapterListener {
        void onUserClicked(User user);
    }
}
