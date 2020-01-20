package com.andyadr.apps.hidroponikstore.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andyadr.apps.hidroponikstore.API.ApiClient;
import com.andyadr.apps.hidroponikstore.API.ApiEndpoints;
import com.andyadr.apps.hidroponikstore.API.ResponsePostPutDelKeranjang;
import com.andyadr.apps.hidroponikstore.BuildConfig;
import com.andyadr.apps.hidroponikstore.CheckoutFragment;
import com.andyadr.apps.hidroponikstore.DetailActivity;
import com.andyadr.apps.hidroponikstore.KeranjangActivity;
import com.andyadr.apps.hidroponikstore.R;
import com.andyadr.apps.hidroponikstore.model.Keranjang;
import com.andyadr.apps.hidroponikstore.model.Produk;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PembelianAdapter extends RecyclerView.Adapter<PembelianAdapter.PembelianViewHolder> {
    private Context context;
    private ArrayList<Produk> Product = new ArrayList<>();
    public PembelianAdapter(Context context) {
        this.context = context;
    }
    public void setPembelian(ArrayList<Produk> Produks) {
        Product.clear();
        Product.addAll(Produks);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PembelianAdapter.PembelianViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_pembelian, viewGroup, false);
        PembelianAdapter.PembelianViewHolder PembelianViewHolder = new PembelianAdapter.PembelianViewHolder(mView);
        return PembelianViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PembelianAdapter.PembelianViewHolder holder, final int i) {
        holder.bind(Product.get(i));
    }

    @Override
    public int getItemCount() {
        return Product.size();
    }


    public class PembelianViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.kodeprodukbeli)
        TextView tv_kode_produk;
        @BindView(R.id.kodememberbeli)
        TextView tv_kode_member;
        @BindView(R.id.nama_produk_beli)
        TextView tv_nama_produk;
        @BindView(R.id.totalbayar)
        TextView tv_harga;
        @BindView(R.id.tgl_belanja)
        TextView tv_tgl_belanja;
        @BindView(R.id.fotobeli)
        ImageView iv_foto;
        PembelianViewHolder(@NonNull final View itemView) {
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
            Integer harga= pecahproduk.getTotal_bayar();

            DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
            DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

            formatRp.setCurrencySymbol("Rp. ");
            formatRp.setMonetaryDecimalSeparator('.');
            formatRp.setGroupingSeparator(',');

            kursIndonesia.setDecimalFormatSymbols(formatRp);
            String x = kursIndonesia.format(harga);

            tv_harga.setText(x);

            String dateString = pecahproduk.getTgl_belanja();
            try {

                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
                String dates = new SimpleDateFormat("dd/MMM/yyyy").format(date);
                tv_tgl_belanja.setText(dates);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
