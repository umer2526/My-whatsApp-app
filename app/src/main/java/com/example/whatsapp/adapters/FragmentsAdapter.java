package com.example.whatsapp.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.whatsapp.fragments.CallsFrag;
import com.example.whatsapp.fragments.ChatsFrag;
import com.example.whatsapp.fragments.StatusFrag;

public class FragmentsAdapter extends FragmentPagerAdapter {
    public FragmentsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0: return new ChatsFrag();
            case 1: return new StatusFrag();
            case 2: return new CallsFrag();
            default: return new ChatsFrag();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        String title = null;
        if (position==0){
            title = "Chats";
        }
        if (position==1){
            title = "Status";
        }
        if (position==2){
            title = "Calls";
        }
        return title;
    }
}
