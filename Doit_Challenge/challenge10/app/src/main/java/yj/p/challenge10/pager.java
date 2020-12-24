package yj.p.challenge10;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class pager extends Fragment {

    ViewPager pager;

    MyPagerAdapter myPagerAdapter;

    fragment_cat1 fragment_cat1;
    fragment_cat2 fragment_cat2;
    fragment_cat3 fragment_cat3;
    fragment_cat4 fragment_cat4;
    fragment_cat5 fragment_cat5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_pager, container, false);


        pager = rootView.findViewById(R.id.pager);
        pager.setOffscreenPageLimit(5);

        myPagerAdapter = new MyPagerAdapter(getParentFragmentManager());
        fragment_cat1 = new fragment_cat1();
        myPagerAdapter.addItem(fragment_cat1);
        fragment_cat2 = new fragment_cat2();
        myPagerAdapter.addItem(fragment_cat2);
        fragment_cat3 = new fragment_cat3();
        myPagerAdapter.addItem(fragment_cat3);
        fragment_cat4 = new fragment_cat4();
        myPagerAdapter.addItem(fragment_cat4);
        fragment_cat5 = new fragment_cat5();
        myPagerAdapter.addItem(fragment_cat5);

        pager.setAdapter(myPagerAdapter);

        return rootView;
    }


}


class MyPagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<Fragment> items = new ArrayList<>();
    public MyPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    public void addItem(Fragment item) {
        items.add(item);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "페이지" + position;
    }
}