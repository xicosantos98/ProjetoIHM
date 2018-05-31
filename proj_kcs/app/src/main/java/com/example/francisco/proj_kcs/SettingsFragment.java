package com.example.francisco.proj_kcs;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {


    SwitchCompat aSwitch;
    Configuration config;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.settings);
        RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.fragment_settings, container, false);
        config = new Configuration(getResources().getConfiguration());
        setHasOptionsMenu(true);

        aSwitch = (SwitchCompat) view.findViewById(R.id.switchLanguage);

        if(config.locale.getDisplayLanguage().equals("inglês")){
            aSwitch.setChecked(true);
        }else{
            aSwitch.setChecked(false);
        }

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    //Toast.makeText(getContext(), "Ingles", Toast.LENGTH_SHORT).show();
                    config.locale = Locale.ENGLISH;
                    getResources().updateConfiguration(config, getResources().getDisplayMetrics());
                }else{
                    //Toast.makeText(getContext(), "Portugues", Toast.LENGTH_SHORT).show();
                    config.locale = new Locale("pt");
                    getResources().updateConfiguration(config, getResources().getDisplayMetrics());
                }
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().finish();
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
