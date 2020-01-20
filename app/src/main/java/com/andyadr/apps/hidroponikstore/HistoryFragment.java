package com.andyadr.apps.hidroponikstore;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andyadr.apps.hidroponikstore.Auth.SharePrefManagerLogin;
import com.andyadr.apps.hidroponikstore.adapter.ProdukAdapter;
import com.andyadr.apps.hidroponikstore.model.MainViewModel;
import com.andyadr.apps.hidroponikstore.model.Produk;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

    static final String TAG = HistoryFragment.class.getSimpleName();

    public static final String EXTRAS = "extras";


    private ProdukAdapter pr_adapter;
    SharePrefManagerLogin sesslogin;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pr_adapter = new ProdukAdapter(getActivity());
        RecyclerView recyclerView = view.findViewById(R.id.rvListhistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(pr_adapter);
        sesslogin = new SharePrefManagerLogin(getActivity());
//        progressBar = view.findViewById(R.id.progressbar);

        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.setHistory(sesslogin.getSPKodeuser());
        mainViewModel.getProduk().observe(this, getProduct);

//        showLoading(true);
    }

    private Observer<ArrayList<Produk>> getProduct = new Observer<ArrayList<Produk>>() {
        @Override
        public void onChanged(@Nullable ArrayList<Produk> produks) {
            if (produks != null) {
                pr_adapter.setProduks(produks);
            }
        }
    };
}
