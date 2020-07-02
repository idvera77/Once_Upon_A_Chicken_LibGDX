package com.ivan.popollo_adventures;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import servicios.ServicioNotificacion;


public class MainActivity extends AppCompatActivity {
    public MediaPlayer musica;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Iniciamos la musica nada mas abrir el main activity, con sonido bajo y en bucle por si queremos estar
        //dos horas mirando el tutorial
        musica = MediaPlayer.create(this, R.raw.portada);
        musica.setVolume(0.4f, 0.4f);
        musica.setLooping(true);
        musica.start();

        //Iniciamos el servicio
        startService(new Intent(this, ServicioNotificacion.class));

        //Menu de navegacion con fragments
        //Depende del id carga muestra uno u otro, predeterminado esta el fragment Juego
        BottomNavigationView navigationView = findViewById(R.id.btm_nav);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.juego) {
                    JuegoFragment fragment = new JuegoFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout, fragment);
                    fragmentTransaction.commit();
                }

                if (id == R.id.ranking) {
                    RankingFragment fragment = new RankingFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout, fragment);
                    fragmentTransaction.commit();
                }

                if (id == R.id.tutorial) {
                    TutorialFragment fragment = new TutorialFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout, fragment);
                    fragmentTransaction.commit();
                }
                return true;
            }
        });
        navigationView.setSelectedItemId(R.id.juego);
    }

    /**
     * Paramos la musica cuando dejamos la aplicacion en segundo plano
     */
    @Override
    protected void onPause() {
        musica.stop();
        super.onPause();
    }

    /**
     * Cuando volvemos del juego a la actividad principal de Android vuelve a sonar la musica
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onRestart() {
        musica = MediaPlayer.create(this, R.raw.portada);
        musica.setVolume(0.4f, 0.4f);
        musica.isLooping();
        musica.start();
        super.onRestart();
    }

    /**
     * Funcion que muestra un alert cuando pulsamos dos veces atras, el alert nos permite salir del programa o seguir en este.
     */
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_videogame_asset_black_24dp)
                .setTitle("Adiós Popollo ^_^")
                .setMessage("¿Estas seguro que quieres cerrar el juego?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    protected void onDestroy() {
        if (musica != null) {
            musica.stop();
            musica.release();
            musica = null;
        }
        super.onDestroy();
    }
}
