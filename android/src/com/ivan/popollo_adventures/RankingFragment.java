package com.ivan.popollo_adventures;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import basededatos.JuegoDataBaseAndroid;
import basededatos.MiOpenHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class RankingFragment extends Fragment {
    private TextView puntuacion1, puntuacion2, puntuacion3;

    public RankingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranking, container, false);
        JuegoDataBaseAndroid db = new JuegoDataBaseAndroid(getContext());
        puntuacion1 = view.findViewById(R.id.cajaPuntuacion);
        puntuacion2 = view.findViewById(R.id.cajaPuntuacion2);
        puntuacion3 = view.findViewById(R.id.cajaPuntuacion3);
        //Miramos el array recibido gracias la consulta de la base de datos para incluir un raking con puntuaciones
        if(db.mejorPartida().size() >= 1) {
            puntuacion1.setText(this.getString(R.string.primerPuesto) + " " + db.mejorPartida().get(0));
        }
        if(db.mejorPartida().size() >= 2) {
            puntuacion2.setText(this.getString(R.string.segundoPuesto) + " " + db.mejorPartida().get(1));
        }
        if(db.mejorPartida().size() >= 3){
            puntuacion3.setText(this.getString(R.string.tercerPuesto) + " " + db.mejorPartida().get(2));
        }
        return view;
    }

}
