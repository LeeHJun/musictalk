package com.example.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.ChatActivity;
import com.example.myapplication.R;

public class Frag2 extends Fragment {

    private View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag2, container, false);

        Button btn1 = (Button) view.findViewById(R.id.btnn2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a TranslateAnimation object to move the button
                TranslateAnimation animation = new TranslateAnimation(0, 100, 0, 100);
                animation.setDuration(1000);
                btn1.startAnimation(animation);

                // Start the SettingFragment activity
                Intent intent = new Intent(getContext(), ChatActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }


}
