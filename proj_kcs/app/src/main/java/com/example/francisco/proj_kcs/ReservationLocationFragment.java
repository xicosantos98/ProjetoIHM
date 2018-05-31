package com.example.francisco.proj_kcs;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationLocationFragment extends Fragment {

    int id_compra;
    Button location1, location2;
    public static final String LOCAL = "LOCAL";
    public static final String COMPRA = "COMPRA";

    public ReservationLocationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.confirmReservation2);
        RelativeLayout view =  (RelativeLayout) inflater.inflate(R.layout.fragment_reservation_location, container, false);

        id_compra = getArguments().getInt("ID");

        location1 = view.findViewById(R.id.buttonLocation1);
        location2 = view.findViewById(R.id.buttonLocation2);

        location1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(getActivity(), RotaFragment.class);
                String id = "1";
                intent.putExtra(LOCAL, id);
                intent.putExtra(COMPRA, String.valueOf(id_compra));
                startActivity(intent);
            }
        });

        location2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(getActivity(), RotaFragment.class);
                String id = "2";
                intent.putExtra(LOCAL, id);
                intent.putExtra(COMPRA, String.valueOf(id_compra));
                startActivity(intent);
            }
        });



        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
    }
}
