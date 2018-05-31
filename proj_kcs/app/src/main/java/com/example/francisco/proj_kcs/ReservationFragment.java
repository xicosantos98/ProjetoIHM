package com.example.francisco.proj_kcs;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationFragment extends Fragment {

    ImageButton chooseDate, chooseLocation;
    String dia, mes, ano;
    TextView data;
    Button confirm,cancel;
    int id_compra;
    private static String dataReserva;


    public ReservationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.confirmReservation1);
        RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.fragment_reservation, container, false);
        data = view.findViewById(R.id.valorData);
        String dataAtual = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        dataReserva = dataAtual;
        data.setText(dataAtual);
        id_compra = getArguments().getInt("ID");

        chooseDate = view.findViewById(R.id.editDate);
        confirm = view.findViewById(R.id.confirmDate);
        cancel = view.findViewById(R.id.cancelDate);

        chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog d = new Dialog(getActivity(), R.style.MyDatePickerDialogTheme);
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.dialog_calendar);
                d.show();
                final DatePicker date = d.findViewById(R.id.datePickerReserva);
                Calendar today = Calendar.getInstance();



                date.init(today.get(Calendar.YEAR),
                        today.get(Calendar.MONTH),
                        today.get(Calendar.DAY_OF_MONTH),
                        new DatePicker.OnDateChangedListener() {


                            @Override
                            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                                dia = String.valueOf(i2);
                                mes = String.valueOf(i1);
                                ano = String.valueOf(i);
                                data.setText(dia + "/" + mes + "/" + ano);
                                dataReserva = dia + "/" + mes + "/" + ano;
                                d.dismiss();
                            }
                        });

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReservationLocationFragment reservationLocationFragment = new ReservationLocationFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                Bundle args = new Bundle();
                args.putInt("ID", id_compra);
                reservationLocationFragment.setArguments(args);
                fragmentTransaction.replace(R.id.principal, reservationLocationFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartFragment cartFragment = new CartFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.principal, cartFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        return view;
    }

    public static String getDataReserva(){return dataReserva;}


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
    }
}
