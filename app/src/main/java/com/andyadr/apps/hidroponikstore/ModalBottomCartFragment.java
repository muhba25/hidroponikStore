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
import com.andyadr.apps.hidroponikstore.API.ResponseFotoProfil;
import com.andyadr.apps.hidroponikstore.API.ResponsePostPutDelKeranjang;
import com.andyadr.apps.hidroponikstore.API.ResponsePostPutDelUser;
import com.andyadr.apps.hidroponikstore.Auth.DaftarActivity;
import com.andyadr.apps.hidroponikstore.Auth.SharePrefManagerLogin;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ModalBottomCartFragment extends BottomSheetDialogFragment {

    public static final String EXTRA_TITLE = "extrask";
    public static final String EXTRA_HARGA = "extrams";
    public static final String EXTRA_KODE = "exltras";
    public static final String EXTRA_FOTO = "extrlas";
    public static final String EXTRA_MEMBER = "extnras";
    static final String TAG = ProdukFragment.class.getSimpleName();
    private ApiEndpoints apiService = ApiClient.getClient().create(ApiEndpoints.class);
    private Button btntambah,btnkurang,btnkeranjang;
    private String kodeproduk,member;
    private EditText etjumlah;

    public ModalBottomCartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_modal_bottom_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvnama = view.findViewById(R.id.produknama2);
        TextView tvharga = view.findViewById(R.id.produkharga);
        EditText etkode = view.findViewById(R.id.produkode);
        ImageView ivfoto = view.findViewById(R.id.foto);
        EditText etmember = view.findViewById(R.id.memberkode);
        etjumlah = view.findViewById(R.id.integer_number);
        btntambah = view.findViewById(R.id.buttonincrease);
        btnkurang = view.findViewById(R.id.buttondecrease);
        btnkeranjang = view.findViewById(R.id.btninsertcart);
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
                int jp = Integer.parseInt(jumlah);
                if (jp==0){etjumlah.setText("0");}else {
                    int jumpro = jp - 1;
                    String jmp = Integer.toString(jumpro);
                    etjumlah.setText(jmp);
                }
            }
        });
        if (getArguments() != null) {
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

            tvnama.setText(namaproduk);
            tvharga.setText(x);
            etkode.setText(kodeproduk);
            etmember.setText(member);

            Glide.with(this)
                    .load(fotoproduk)
                    .into(ivfoto);
        }
        btnkeranjang.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String jumlahpr = etjumlah.getText().toString().trim();
                int jum = Integer.parseInt(jumlahpr);
                if (jum==0){
                    Toast.makeText(getActivity().getBaseContext(), "Tidak ada jumlah pembelian", Toast.LENGTH_SHORT).show();
                } else {
                    Call<ResponsePostPutDelKeranjang> postKontakCall = apiService.postKeranjang(member, kodeproduk, jumlahpr);
                    postKontakCall.enqueue(new Callback<ResponsePostPutDelKeranjang>() {
                        @Override
                        public void onResponse(Call<ResponsePostPutDelKeranjang> call, Response<ResponsePostPutDelKeranjang> response) {
                            ResponsePostPutDelKeranjang serverResponse = response.body();
                            if (serverResponse != null) {
                                if (serverResponse.getSuccess()) {
                                    Toast.makeText(getActivity().getBaseContext(), serverResponse.getMessage(), Toast.LENGTH_LONG).show();
//                                    Intent intent = new Intent(getActivity(), MainActivity.class);
//                                    startActivity(intent);
                                    dismiss();
                                } else {
                                    Toast.makeText(getActivity().getBaseContext(), serverResponse.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            } else {
                                assert serverResponse != null;
                                Log.v("Response", serverResponse.toString());
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponsePostPutDelKeranjang> call, Throwable t) {
                            Toast.makeText(getActivity().getBaseContext(), "Error", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

}
