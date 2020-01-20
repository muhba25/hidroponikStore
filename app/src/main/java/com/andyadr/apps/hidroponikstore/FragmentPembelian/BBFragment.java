package com.andyadr.apps.hidroponikstore.FragmentPembelian;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andyadr.apps.hidroponikstore.Auth.SharePrefManagerLogin;
import com.andyadr.apps.hidroponikstore.KeranjangActivity;
import com.andyadr.apps.hidroponikstore.PembelianActivity;
import com.andyadr.apps.hidroponikstore.ProdukFragment;
import com.andyadr.apps.hidroponikstore.R;
import com.andyadr.apps.hidroponikstore.adapter.KeranjangAdapter;
import com.andyadr.apps.hidroponikstore.adapter.PembelianAdapter;
import com.andyadr.apps.hidroponikstore.adapter.ProdukAdapter;
import com.andyadr.apps.hidroponikstore.model.MainViewModel;
import com.andyadr.apps.hidroponikstore.model.Produk;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BBFragment extends Fragment {

    static final String TAG = BBFragment.class.getSimpleName();

    public static final String EXTRAS = "extras";


    private PembelianAdapter pr_adapter;
    SharePrefManagerLogin sesslogin;

    public BBFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bb, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        pr_adapter = new PembelianAdapter(getActivity());
        RecyclerView recyclerView = view.findViewById(R.id.rvListpembelian);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(pr_adapter);
        sesslogin = new SharePrefManagerLogin(getActivity());

//        progressBar = view.findViewById(R.id.progressbar);

        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.setPembelian(sesslogin.getSPKodeuser());
        mainViewModel.getProduk().observe(this, getProduct);

        RekeningFragment fragment = new RekeningFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }

    private Observer<ArrayList<Produk>> getProduct = new Observer<ArrayList<Produk>>() {
        @Override
        public void onChanged(@Nullable ArrayList<Produk> produks) {
            if (produks != null) {
                pr_adapter.setPembelian(produks);
            }
        }
    };
}
