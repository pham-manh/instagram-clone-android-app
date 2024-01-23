package com.example.myapplication.viewmodel;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.data.model.entity.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchViewModel extends BaseObservable {
    public MutableLiveData<List<User>> users= new MutableLiveData<>();
    public MutableLiveData<String> searchChar;


    public MutableLiveData<List<User>> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users.setValue(users);
    }

    public MutableLiveData<String> getSearchChar() {
        return searchChar;
    }

    public void searchUser(String s) {
        Query query = FirebaseDatabase
                .getInstance("https://instagram-clone-784ff-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference()
                .child("Users")
                .orderByChild("username")
                .startAt(s)
                .endAt(s + "\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<User> userSearchList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    userSearchList.add(user);
                }
                setUsers(userSearchList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
