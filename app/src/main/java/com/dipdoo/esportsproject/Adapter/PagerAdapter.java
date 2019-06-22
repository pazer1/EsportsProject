package com.dipdoo.esportsproject.Adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();

    FragmentManager fm;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
    }

    public List getFragmentList(){
        return fragmentList;
    }

    public void addFragement(Fragment fragment, String title){
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
    }

//    public void refresh(){
//        for(int i = 0; i<fragmentList.size(); i++){
//            MainFragment fragment = (MainFragment) fragmentList.get(i);
//            fragment.refreshRecyclerAdapter();
//            FragmentTransaction ft = fm.beginTransaction();
//            ft.detach(fragment).attach(fragment).commit();
//        }
//    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }
}
