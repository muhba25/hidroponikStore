package com.andyadr.apps.hidroponikstore.FragmentPembelian;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andyadr.apps.hidroponikstore.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RekeningFragment extends Fragment {


    public RekeningFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rekening, container, false);
    }

}
