package com.category.base.adapter;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import com.category.base.fragment.BaseFragment;

import java.util.List;

/**
 * Created by fengyin on 16-4-11.
 */
public class BaseFragmentAdapter extends FragmentPagerAdapter {
    private FragmentManager mFragmentManager;
    private List<BaseFragment> mFragments;
    private List<String> mTitles;

    public BaseFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void updateFragments(List<BaseFragment> fragments, List<String> title){
        for(int i = 0; i < fragments.size(); i++){
            final BaseFragment fragment = mFragments.get(i);
            final FragmentTransaction transaction = mFragmentManager.beginTransaction();
            if(i > 2){
                transaction.remove(fragment);
                mFragments.remove(fragment);
                i--;
            }

            transaction.commit();
        }

        for(int i = 0; i < fragments.size(); i++){
            if(i > 2){
                mFragments.add(fragments.get(i));
            }
        }

        this.mTitles = title;
        notifyDataSetChanged();
    }

    public BaseFragmentAdapter(FragmentManager fm, List<BaseFragment> fragments, List<String> titles) {
        super(fm);
        mFragmentManager = fm;
        mFragments = fragments;
        mTitles = titles;
    }


    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        super.restoreState(state, loader);
    }
}
