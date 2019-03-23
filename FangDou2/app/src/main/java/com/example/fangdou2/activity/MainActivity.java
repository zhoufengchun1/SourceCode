package com.example.fangdou2.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.fangdou2.R;
import com.example.fangdou2.fragment.ListViewFragment;
import com.example.fangdou2.fragment.NavigationFragment;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addFragment(new NavigationFragment(), R.id.navigation_fragment);
        addFragment(new ListViewFragment(), R.id.listView_Fragment);
    }

    private void addFragment(Fragment fragment, int id)
    {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(id, fragment);

        fragmentTransaction.commit();

    }
}
