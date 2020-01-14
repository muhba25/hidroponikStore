package com.andyadr.apps.hidroponikstore;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.andyadr.apps.hidroponikstore.adapter.ProdukAdapter;
import com.andyadr.apps.hidroponikstore.model.MainViewModel;
import com.andyadr.apps.hidroponikstore.model.Produk;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProdukFragment extends Fragment {


    static final String TAG = ProdukFragment.class.getSimpleName();

    public static final String EXTRAS = "extras";


    private ProdukAdapter pr_adapter;
    private ProgressBar progressBar;

    public ProdukFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_produk, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pr_adapter = new ProdukAdapter(getActivity());
        RecyclerView recyclerView = view.findViewById(R.id.rvListproduk);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(pr_adapter);

//        progressBar = view.findViewById(R.id.progressbar);

        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.setProduk();
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

//    private void showLoading(Boolean state) {
//        if (state) {
//            progressBar.setVisibility(View.VISIBLE);
//        } else {
//            progressBar.setVisibility(View.GONE);
//        }
//    }

}
