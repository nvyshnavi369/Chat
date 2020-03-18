package com.example.chat.ui.send.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.chat.R;
import com.example.chat.tab.ViewPagerAdapter;
import com.example.chat.tab.chat_tab_fragment;
import com.example.chat.tab.group_tab_fragment;
import com.google.android.material.tabs.TabLayout;

public class HomeFragment extends Fragment {
private TabLayout tabLayout;

    private HomeViewModel homeViewModel;
    private ViewPager viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        tabLayout=root.findViewById(R.id.tablayout);
        viewPager=root.findViewById(R.id.view_id);
        ViewPagerAdapter adapter=new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.AddFragment(new chat_tab_fragment(),"Chat");
        adapter.AddFragment(new group_tab_fragment(),"Groups");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        /*homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }

}