package com.ivan.popollo_adventures;


import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import servicios.ServicioNotificacion;


/**
 * A simple {@link Fragment} subclass.
 */
public class JuegoFragment extends Fragment {
    public MainActivity activity;
    Button btnJugar;
    View vista;

    public JuegoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        vista = inflater.inflate(R.layout.fragment_juego, container, false);
        btnJugar = (Button) vista.findViewById(R.id.botonJugar);
        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.musica.stop();
                Intent intent = new Intent(getContext(), AndroidLauncher.class);
                startActivity(intent);

            }
        });
        return vista;
    }

}
