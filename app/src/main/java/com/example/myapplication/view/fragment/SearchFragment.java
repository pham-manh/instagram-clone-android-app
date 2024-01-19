package com.example.myapplication.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.UserAdapter;
import com.example.myapplication.databinding.FragmentSearchBinding;
import com.example.myapplication.viewmodel.SearchViewModel;
import com.hendraanggrian.appcompat.socialview.widget.SocialAutoCompleteTextView;

import java.util.ArrayList;


public class SearchFragment extends Fragment {
    private UserAdapter mAdapter;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentSearchBinding fragmentSearchBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        fragmentSearchBinding.setSearchFragmentViewModel(new SearchViewModel());

        View view = fragmentSearchBinding.getRoot();
        SocialAutoCompleteTextView completeTextView = view.findViewById(R.id.search_bar);
        RecyclerView recyclerView = fragmentSearchBinding.recyclerViewUsers;
        mAdapter = new UserAdapter(new ArrayList<>());
        recyclerView.setAdapter(mAdapter);

        completeTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                fragmentSearchBinding.getSearchFragmentViewModel().searchUser(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        fragmentSearchBinding.getSearchFragmentViewModel().getUsers().observe(getViewLifecycleOwner(),
                users -> {
                    mAdapter.setUsers(users);
                    mAdapter.notifyDataSetChanged();
                });

        return view;
    }

//    private void readUser() {
//        DatabaseReference mRef = FirebaseDatabase
//                .getInstance("https://instagram-clone-784ff-default-rtdb.asia-southeast1.firebasedatabase.app/")
//                .getReference()
//                .child("Users");
//
//        mRef.addValueEventListener(new ValueEventListener() {
//            @SuppressLint("NotifyDataSetChanged")
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (TextUtils.isEmpty(socialAutoCompleteTextView.getText().toString())) {
//                    mUser.clear();
//                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                        User user = dataSnapshot.getValue(User.class);
//                        mUser.add(user);
//                    }
//                    userAdapter.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
//    }
//
//    private void searchUser(String s) {
//        Query query = FirebaseDatabase.getInstance("https://instagram-clone-784ff-default-rtdb.asia-southeast1.firebasedatabase.app/")
//                .getReference()
//                .child("Users")
//                .orderByChild("username")
//                .startAt(s)
//                .endAt(s + "\uf8ff");
//
//        query.addValueEventListener(new ValueEventListener() {
//            @SuppressLint("NotifyDataSetChanged")
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                mUser.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    User user = dataSnapshot.getValue(User.class);
//                    mUser.add(user);
//                }
//                userAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
}