package com.example.francisco.proj_kcs;


import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.irozon.sneaker.Sneaker;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PagamentoFragment extends Fragment {

    int id_compra;
    TextView valor, userNome, rua, cp, pais;
    float valor_compra;
    Button confirm;
    DatabaseHelper db;
    Cursor cursor;
    ArrayList<String> user;

    public PagamentoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.confirm);
        // Inflate the layout for this fragment
        RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.fragment_pagamento, container, false);

        db = new DatabaseHelper(getActivity());
        id_compra = getArguments().getInt("ID");

        userNome = view.findViewById(R.id.user);
        rua = view.findViewById(R.id.rua);
        cp = view.findViewById(R.id.localidade);
        pais = view.findViewById(R.id.pais);
        confirm = view.findViewById(R.id.buttonConfirmar);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage(getResources().getString(R.string.wait));
                progressDialog.show();

                Runnable progressRunnable = new Runnable() {

                    @Override
                    public void run() {
                        progressDialog.cancel();


                        Sneaker.with(getActivity())
                                .setTitle(getResources().getString(R.string.productBuy))
                                .sneakSuccess();


                    }
                };

                Handler pdCanceller = new Handler();
                pdCanceller.postDelayed(progressRunnable, 2000);
                db.updateEstadoCompra(id_compra);
                HomeFragment homeFragment = new HomeFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_esquerda, R.anim.slide_esquerdaout);
                fragmentTransaction.replace(R.id.principal, homeFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }
        });


        user = new ArrayList<>();


        valor = view.findViewById(R.id.valor_preco);
        valor_compra = db.getValueCompra(id_compra);
        valor.setText(String.valueOf(valor_compra) + "€");


        cursor = db.getUserByNome(MainActivity.getUser());

        cursor.moveToFirst();
        do {

            user.add(cursor.getString(cursor.getColumnIndex(db.NOME_UTILIZADOR)));
            user.add(cursor.getString(cursor.getColumnIndex(db.RUA)));
            user.add(cursor.getString(cursor.getColumnIndex(db.CP)));
            user.add(cursor.getString(cursor.getColumnIndex(db.PAIS)));

        } while (cursor.moveToNext());


        userNome.setText(user.get(0));
        rua.setText(user.get(1));
        cp.setText(user.get(2));
        pais.setText(user.get(3));


        return view;
    }


}
