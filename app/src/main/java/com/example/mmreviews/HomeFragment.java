package com.example.mmreviews;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        view.findViewById(R.id.btn_edit).setOnClickListener(v -> getFragmentManager().beginTransaction().replace(R.id.fragment_container, new EditFragment()).commit());

        view.findViewById(R.id.btn_logout).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LogIn.class);
            startActivity(intent);

        });



        return view;
    }
}
