package com.example.fangdou2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.fangdou2.R;
import com.example.fangdou2.fragment.MapFragment;


public class NavigationFragment extends Fragment
{

    private View view;
    private RadioGroup mTabRadioGroup;
    private SparseArray<Fragment> mFragmentSparseArray;
    private FragmentTransaction fragmentTransaction;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        //又因为onCreateView会多次调用，所以view可以重复利用。
        if (view != null)
        {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
            {
                parent.removeView(view);
            }

        } else
        {
            view = inflater.inflate(R.layout.activity_navigation, null);

            initView();
        }
        return view;

    }

    private void initView()
    {
        final FragmentManager fragmentManager = getFragmentManager();
        ListViewFragment listViewFragment = new ListViewFragment();
        final NavigationFragment navigationFragment = new NavigationFragment();
        if (fragmentTransaction == null)
        {
            fragmentTransaction = fragmentManager.beginTransaction();
        }
        mTabRadioGroup = view.findViewById(R.id.tabs_rg);
        mFragmentSparseArray = new SparseArray<>();
        mFragmentSparseArray.append(R.id.square, listViewFragment);
        mFragmentSparseArray.append(R.id.knowledge, navigationFragment);
        mTabRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                fragmentTransaction = fragmentManager.beginTransaction();
                //因为一个实例化的FragmentTransaction只能被commit一次，所以
                //我们每次点击就重新实例化一次FragmentTransaction。
                fragmentTransaction.replace(R.id.fragment_container,
                        mFragmentSparseArray.get(checkedId));
//                for(int i=0;i<mFragmentSparseArray.size();i++)
//                {
//                    int key = mFragmentSparseArray.keyAt(i);
//                    if (key != checkedId)
//                    {
//                        fragmentTransaction.hide(mFragmentSparseArray.get(key));
//                    }
//                    else
//                        fragmentTransaction.show(mFragmentSparseArray.get(key));
//                }
                fragmentTransaction.hide(navigationFragment).commit();
            }
        });
        // 默认显示第一个
       /* fragmentTransaction.add(R.id.fragment_container,
                mFragmentSparseArray.get(R.id.square)).commit();*/
        RadioButton defaultButton = (RadioButton) mTabRadioGroup.findViewById(R.id.square);
        defaultButton.setChecked(true);



       /* ImageView imageView = (ImageView) view.findViewById(R.id.start_record);
        imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(view.getContext(), RecordActivity.class);
                startActivity(intent);
            }
        });*/

    }
}
