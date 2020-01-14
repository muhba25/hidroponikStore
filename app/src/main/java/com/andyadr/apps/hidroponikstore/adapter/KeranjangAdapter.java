package com.andyadr.apps.hidroponikstore.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.andyadr.apps.hidroponikstore.Auth.LoginActivity;
import com.andyadr.apps.hidroponikstore.BuildConfig;
import com.andyadr.apps.hidroponikstore.DetailActivity;
import com.andyadr.apps.hidroponikstore.KeranjangActivity;
import com.andyadr.apps.hidroponikstore.MainActivity;
import com.andyadr.apps.hidroponikstore.PembelianActivity;
import com.andyadr.apps.hidroponikstore.R;
import com.andyadr.apps.hidroponikstore.model.Keranjang;
import com.andyadr.apps.hidroponikstore.model.Produk;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KeranjangAdapter extends RecyclerView.Adapter<KeranjangAdapter.KeranjangViewHolder> {
    private Context context;
    private ArrayList<Keranjang> Keranjang = new ArrayList<>();
    public KeranjangAdapter(Context context) {
        this.context = context;
    }
    public void setKeranjang(ArrayList<Keranjang> Keranjangs) {
        this.Keranjang = Keranjangs;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public KeranjangViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_keranjang, viewGroup, false);
        return new KeranjangViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull KeranjangAdapter.KeranjangViewHolder holder, int i) {
        holder.bind(Keranjang.get(i));


    }

    @Override
    public int getItemCount() {
        return Keranjang.size();
    }

    public class KeranjangViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.kodeproduk_keranjang)
        TextView tv_kode_produk;
        @BindView(R.id.nama_produk_keranjang)
        TextView tv_nama_produk;
        @BindView(R.id.harga_keranjang)
        TextView tv_harga;
        @BindView(R.id.kodemember_keranjang)
        TextView tv_member;
        @BindView(R.id.jumlah_keranjang)
        EditText et_jumlah;
        @BindView(R.id.foto_keranjang)
        ImageView iv_foto;

        private Button btntambah,btnkurang,btncheckout;
        private EditText etjumlah,etkodeproduk,etkodemember;
        private String ke,kp;
        private ApiEndpoints apiService = ApiClient.getClient().create(ApiEndpoints.class);
        ProgressDialog progressDialog;

        KeranjangViewHolder(@NonNull final View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
            btntambah = itemView.findViewById(R.id.buttonincrease_keranjang);
            btnkurang = itemView.findViewById(R.id.buttondecrease_keranjang);
            btncheckout = itemView.findViewById(R.id.btncheckout);
            etjumlah = itemView.findViewById(R.id.jumlah_keranjang);
            etkodemember = itemView.findViewById(R.id.kodemember_keranjang);
            etkodeproduk = itemView.findViewById(R.id.kodeproduk_keranjang);

            //button

            btntambah.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    final String jumlah = etjumlah.getText().toString().trim();
                    int jp = Integer.parseInt(jumlah);
                    int jumpro = jp + 1;
                    String jmp = Integer.toString(jumpro);
                    etjumlah.setText(jmp);
                }
            });

            btnkurang.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    final String jumlah = etjumlah.getText().toString().trim();
                    ke =  etkodemember.getText().toString().trim();
                    kp = etkodeproduk.getText().toString().trim();
                    int jp = Integer.parseInt(jumlah);
                    if (jp==0){
                        new AlertDialog.Builder(itemView.getContext())
                                .setTitle("Hapus Keranjang")
                                .setMessage("Ingin Menghapus Produk ini Dari Keranjang ?")
                                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(final DialogInterface dialog, int which) {
                                        //Toast.makeText(getActivity(), "Kamu Memilih YES", Toast.LENGTH_LONG).show();\
                                        Call<ResponsePostPutDelKeranjang> call = apiService.deleteKeranjang(ke,kp);
                                        call.enqueue(new Callback<ResponsePostPutDelKeranjang>() {
                                            @Override
                                            public void onResponse(Call<ResponsePostPutDelKeranjang> call, Response<ResponsePostPutDelKeranjang> response) {
                                                ResponsePostPutDelKeranjang serverResponse = response.body();
                                                if (serverResponse != null) {
                                                    if (serverResponse.getSuccess()) {
                                                        Toast.makeText(context, serverResponse.getStatus(), Toast.LENGTH_LONG).show();
                                                        dialog.cancel();
                                                    } else {
                                                        Toast.makeText(itemView.getContext(), serverResponse.getStatus(), Toast.LENGTH_SHORT).show();
                                                        dialog.cancel();
                                                    }
                                                    Intent details = new Intent(context, KeranjangActivity.class);
                                                    details.putExtra("keranjang", Keranjang.get(getAdapterPosition()));
                                                    context.startActivity(details);
                                                } else {
                                                    assert serverResponse != null;
                                                    Log.v("Response", serverResponse.toString());
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ResponsePostPutDelKeranjang> call, Throwable t) {
                                                Toast.makeText(itemView.getContext(), "error", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }

                                })
                                .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                }).show();

                    }else {
                        int jumpro = jp - 1;
                        String jmp = Integer.toString(jumpro);
                        etjumlah.setText(jmp);
                    }
                }
            });

        }

        public void bind(Keranjang pecahkeranjang) {
            Glide.with(itemView.getContext())
                    .load( BuildConfig.URL_IMG + pecahkeranjang.getFoto_keranjang())
                    .apply(new RequestOptions())
                    .into(iv_foto);

            tv_nama_produk.setText(pecahkeranjang.getNama_produk_keranjang());
            tv_kode_produk.setText(pecahkeranjang.getKode_produk_keranjang());
            Integer harga= pecahkeranjang.getHarga_keranjang();

            DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
            DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

            formatRp.setCurrencySymbol("Rp. ");
            formatRp.setMonetaryDecimalSeparator('.');
            formatRp.setGroupingSeparator(',');

            kursIndonesia.setDecimalFormatSymbols(formatRp);
            String x = kursIndonesia.format(harga);

            tv_harga.setText(x);
            et_jumlah.setText(pecahkeranjang.getJumlah_keranjang());
            tv_member.setText(pecahkeranjang.getMemberid_keranjang());


        }

        @Override
        public void onClick(View v) {

        }
    }
}
