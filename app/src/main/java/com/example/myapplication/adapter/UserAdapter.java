package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context mContext;
    private List<User> mUsers;
    private boolean isFragment;
    private FirebaseUser fireBaseUser;

    public UserAdapter(Context mContext, List<User> mUsers, FirebaseUser fireBaseUser) {
        this.mContext = mContext;
        this.mUsers = mUsers;
        this.fireBaseUser = fireBaseUser;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        fireBaseUser = FirebaseAuth.getInstance().getCurrentUser();
        User user = mUsers.get(position);
        holder.followBtn.setVisibility(View.VISIBLE);
        holder.username.setText(user.getUsername());
        holder.fullName.setText(user.getName());

        Picasso.get().load(user.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imageProfile);
        isFollowed(user.getId(), holder.followBtn);
        if (user.getId().equals(fireBaseUser.getUid())) {
            holder.followBtn.setVisibility(View.GONE);
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

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView imageProfile;
        public TextView username;
        public TextView fullName;
        public Button followBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageProfile = itemView.findViewById(R.id.image_profile);
            username = itemView.findViewById(R.id.username);
            fullName = itemView.findViewById(R.id.fullname);
            followBtn = itemView.findViewById(R.id.btn_follow);
        }
    }

}
