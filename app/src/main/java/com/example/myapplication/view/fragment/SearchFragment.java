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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.UserAdapter;
import com.example.myapplication.databinding.FragmentSearchBinding;
import com.example.myapplication.viewmodel.SearchViewModel;
import com.hendraanggrian.appcompat.socialview.widget.SocialAutoCompleteTextView;


public class SearchFragment extends Fragment {
    private UserAdapter mAdapter;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentSearchBinding fragmentSearchBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_search,
                container,
                false);
        fragmentSearchBinding.setSearchFragmentViewModel(new SearchViewModel());

        View view = fragmentSearchBinding.getRoot();

        SocialAutoCompleteTextView completeTextView = view.findViewById(R.id.search_bar);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_users);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(view.getContext(),
                        LinearLayoutManager.VERTICAL,
                        false));

        mAdapter = new UserAdapter();
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
}