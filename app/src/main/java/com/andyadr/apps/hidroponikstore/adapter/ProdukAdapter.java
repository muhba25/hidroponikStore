package com.andyadr.apps.hidroponikstore.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import com.andyadr.apps.hidroponikstore.BuildConfig;
import com.andyadr.apps.hidroponikstore.DetailActivity;
import com.andyadr.apps.hidroponikstore.R;
import com.andyadr.apps.hidroponikstore.model.Produk;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.ProdukViewHolder> {
    private Context context;
    private ArrayList<Produk> Product = new ArrayList<>();

    public ProdukAdapter(Context context) {
        this.context = context;
    }

    public void setProduks(ArrayList<Produk> Produks) {
        Product.clear();
        Product.addAll(Produks);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProdukViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_produk, viewGroup, false);
        ProdukViewHolder produkViewHolder = new ProdukViewHolder(mView);
        return produkViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProdukViewHolder holder,final int i) {
        holder.bind(Product.get(i));
    }

    @Override
    public int getItemCount() {
        return Product.size();
    }


    public class ProdukViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.kode_produk)
        TextView tv_kode_produk;
        @BindView(R.id.nama_produk)
        TextView tv_nama_produk;
        @BindView(R.id.deskripsi)
        TextView tv_deskripsi;
        @BindView(R.id.harga)
        TextView tv_harga;
        @BindView(R.id.jenis)
        TextView tv_jenis;
        @BindView(R.id.sold)
        TextView tv_sold;
        @BindView(R.id.tgl_upload)
        TextView tv_tgl_upload;
        @BindView(R.id.rating)
        TextView tv_rating;
        @BindView(R.id.foto)
        ImageView iv_foto;
        ProdukViewHolder(@NonNull final View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent details = new Intent(context, DetailActivity.class);
                    details.putExtra("produk", Product.get(getAdapterPosition()));
                    context.startActivity(details);
                }
            });
        }

        public void bind(Produk pecahproduk) {
            Glide.with(itemView.getContext())
                    .load( BuildConfig.URL_IMG + pecahproduk.getFoto())
                    .apply(new RequestOptions())
                    .into(iv_foto);

            tv_nama_produk.setText(pecahproduk.getNama_produk());
            tv_kode_produk.setText(pecahproduk.getKode_produk());
            tv_deskripsi.setText(pecahproduk.getDeskripsi());
            Integer harga= pecahproduk.getHarga();

            DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
            DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

            formatRp.setCurrencySymbol("Rp. ");
            formatRp.setMonetaryDecimalSeparator('.');
            formatRp.setGroupingSeparator(',');

            kursIndonesia.setDecimalFormatSymbols(formatRp);
            String x = kursIndonesia.format(harga);

            tv_harga.setText(x);

            String sold= Integer.toString(pecahproduk.getSold());
            tv_sold.setText(sold);

            String rating= Double.toString(pecahproduk.getRating());
            tv_rating.setText(rating);

            tv_jenis.setText(pecahproduk.getJenis());
            tv_tgl_upload.setText(pecahproduk.getTgl_upload());
        }
    }
}
