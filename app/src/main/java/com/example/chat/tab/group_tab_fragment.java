package com.example.chat.tab;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.chat.R;
import com.example.chat.insidechat;

public class group_tab_fragment extends Fragment {
    View view;
    public group_tab_fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.group_tab_fragment,container,false);
        view.findViewById(R.id.c1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(group_tab_fragment.this.getActivity(), insidechat.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
