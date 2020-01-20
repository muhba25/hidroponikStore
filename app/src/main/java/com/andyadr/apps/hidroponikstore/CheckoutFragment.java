package com.andyadr.apps.hidroponikstore;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
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
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class CheckoutFragment extends BottomSheetDialogFragment {

    public static final String EXTRA_TITLE = "extrask";
    public static final String EXTRA_HARGA = "extrams";
    public static final String EXTRA_KODE = "exltras";
    public static final String EXTRA_FOTO = "extrlasas";
    public static final String EXTRA_JUMLAH = "extrlas";
    public static final String EXTRA_MEMBER = "extnras";
    static final String TAG = CheckoutFragment.class.getSimpleName();
    private ApiEndpoints apiService = ApiClient.getClient().create(ApiEndpoints.class);
    private EditText etTotalbayar,etRealpay;
    private String kodeproduk,member;
    private Button btnBayar;
    private Integer totalbayar;

    public CheckoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_checkout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvnama = view.findViewById(R.id.produknama2);
        TextView tvharga = view.findViewById(R.id.hargacheck);
        TextView tvjumlah = view.findViewById(R.id.jumlahcheck);
        TextView tvdet = view.findViewById(R.id.textView);
        TextView tvtot = view.findViewById(R.id.textView2);
        EditText etkode = view.findViewById(R.id.produkodecheck);
        ImageView ivfoto = view.findViewById(R.id.fotocheck);
        EditText etmember = view.findViewById(R.id.memberkodecheck);
        etTotalbayar = view.findViewById(R.id.totalbayarcheck);
        etRealpay = view.findViewById(R.id.realhargacheck);
        btnBayar = view.findViewById(R.id.btnpaynow);

        if (getArguments() != null) {
            String jumlahproduk = getArguments().getString(EXTRA_JUMLAH);
            int jp = Integer.parseInt(jumlahproduk);
            if (jp==0){
                tvnama.setText("Jumlah Produk tidak ada");
                btnBayar.setVisibility(view.GONE);
            }else {
                String namaproduk = getArguments().getString(EXTRA_TITLE);
                kodeproduk = getArguments().getString(EXTRA_KODE);
                String fotoproduk = BuildConfig.URL_IMG + getArguments().getString(EXTRA_FOTO);
                member = getArguments().getString(EXTRA_MEMBER);
                Integer harga = getArguments().getInt(EXTRA_HARGA);
                DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
                DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

                formatRp.setCurrencySymbol("Rp. ");
                formatRp.setMonetaryDecimalSeparator('.');
                formatRp.setGroupingSeparator(',');

                kursIndonesia.setDecimalFormatSymbols(formatRp);
                String x = kursIndonesia.format(harga);

                int jumlah = Integer.parseInt(jumlahproduk);
                Integer totalpay = harga * jumlah;
                String realpaynow = Integer.toString(totalpay);
                String paynow = kursIndonesia.format(totalpay);

                tvnama.setText(namaproduk);
                tvharga.setText(x + "   X");
                tvjumlah.setText(jumlahproduk);
                tvdet.setText("Detail");
                tvtot.setText("Total Bayar");
                etTotalbayar.setText(paynow);
                etRealpay.setText(realpaynow);
                etkode.setText(kodeproduk);
                etmember.setText(member);

                Glide.with(this)
                        .load(fotoproduk)
                        .into(ivfoto);
            }
        }

        btnBayar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String jumlahpr = etRealpay.getText().toString().trim();
                    Call<ResponsePostPutDelKeranjang> postKontakCall = apiService.postPay(member, kodeproduk, jumlahpr);
                    postKontakCall.enqueue(new Callback<ResponsePostPutDelKeranjang>() {
                        @Override
                        public void onResponse(Call<ResponsePostPutDelKeranjang> call, Response<ResponsePostPutDelKeranjang> response) {
                            ResponsePostPutDelKeranjang serverResponse = response.body();
                            if (serverResponse != null) {
                                if (serverResponse.getSuccess()) {
                                    Toast.makeText(getActivity().getBaseContext(), serverResponse.getMessage(), Toast.LENGTH_LONG).show();

                                } else {
                                    Toast.makeText(getActivity().getBaseContext(), serverResponse.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            } else {
                                assert serverResponse != null;
                                Log.v("Response", serverResponse.toString());
                            }
                            Intent intent = new Intent(getActivity().getBaseContext(), PembelianActivity.class);
                            startActivity(intent);

                        }

                        @Override
                        public void onFailure(Call<ResponsePostPutDelKeranjang> call, Throwable t) {
                            Toast.makeText(getActivity().getBaseContext(), "Error", Toast.LENGTH_LONG).show();
                        }
                    });
                }
        });

    }

}
