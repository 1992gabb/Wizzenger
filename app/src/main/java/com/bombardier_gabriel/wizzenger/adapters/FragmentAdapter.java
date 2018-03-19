package com.bombardier_gabriel.wizzenger.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bombardier_gabriel.wizzenger.R;
import com.bombardier_gabriel.wizzenger.fragments.mainFragments.ContactsFragment;
import com.bombardier_gabriel.wizzenger.fragments.mainFragments.ConversationsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabb_ on 2018-01-08.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private List<Fragment> fragmentList;

    public FragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        fragmentList = new ArrayList<>();
        fragmentList.add(new ConversationsFragment());
        fragmentList.add(new ContactsFragment());
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return fragmentList.size();
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return mContext.getString(R.string.conversations_title);
            case 1:
                return mContext.getString(R.string.contacts_title);
            default:
                return null;
        }
    }

}
