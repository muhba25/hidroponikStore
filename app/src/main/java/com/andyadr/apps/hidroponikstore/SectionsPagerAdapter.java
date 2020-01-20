package com.andyadr.apps.hidroponikstore;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.andyadr.apps.hidroponikstore.FragmentPembelian.BBFragment;
import com.andyadr.apps.hidroponikstore.FragmentPembelian.BatalFragment;
import com.andyadr.apps.hidroponikstore.FragmentPembelian.DikemasFragment;
import com.andyadr.apps.hidroponikstore.FragmentPembelian.DikirimFragment;
import com.andyadr.apps.hidroponikstore.FragmentPembelian.SelesaiFragment;


public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.tab_Belum_Bayar,
            R.string.tab_dikemas,
            R.string.tab_dikirim,
            R.string.tab_selesai,
            R.string.tab_dibatalkan
    };
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new BBFragment();
                break;

            case 1:
                fragment = new DikemasFragment();
                break;
            case 2:
                fragment = new DikirimFragment();
                break;
            case 3:
                fragment = new SelesaiFragment();
                break;
            case 4:
                fragment = new BatalFragment();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 5;
    }
}

