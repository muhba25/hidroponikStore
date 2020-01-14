package com.andyadr.apps.hidroponikstore;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class KeranjangFragment extends Fragment {
    public static final String EXTRAS = "extras";
    static final String TAG = ProdukFragment.class.getSimpleName();

    public KeranjangFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_keranjang, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = view.findViewById(R.id.tv_fragment);
        if (getArguments() != null) {
            String page = getArguments().getString(EXTRAS);
            textView.setText(page);
            Log.e(TAG, "onCreateView: halaman fragment " + page);
        }
    }

}
