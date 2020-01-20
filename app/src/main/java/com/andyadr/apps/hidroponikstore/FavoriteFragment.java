package com.andyadr.apps.hidroponikstore;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andyadr.apps.hidroponikstore.adapter.ProdukAdapter;
import com.andyadr.apps.hidroponikstore.db.DatabaseProduk;
import com.andyadr.apps.hidroponikstore.db.ProdukDao;
import com.andyadr.apps.hidroponikstore.model.MainViewModel;
import com.andyadr.apps.hidroponikstore.model.Produk;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    public static final String EXTRAS = "extras";


    private ProdukAdapter pr_adapter;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pr_adapter = new ProdukAdapter(getActivity());
        RecyclerView recyclerView = view.findViewById(R.id.rvListfavproduk);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(pr_adapter);

        ArrayList<Produk> data = (ArrayList<Produk>) loadFavProduks();
//        progressBar = view.findViewById(R.id.progressbar);

        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.setFavProduk(data);
        mainViewModel.getProduk().observe(this, getProduct);

//        showLoading(true);
    }

    private List<Produk> loadFavProduks() {
        DatabaseProduk database = Room.databaseBuilder(getActivity(), DatabaseProduk.class, "db_produk")
                .allowMainThreadQueries()
                .build();
        ProdukDao produkDao = database.getProdukDAO();
        return produkDao.getAllFavProduks();
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
