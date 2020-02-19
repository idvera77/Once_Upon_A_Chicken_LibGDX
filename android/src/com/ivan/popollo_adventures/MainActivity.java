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
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {
    public MediaPlayer musica;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        musica = MediaPlayer.create(this, R.raw.portada);
        musica.setVolume(0.4f, 0.4f);
        musica.isLooping();
        musica.start();

        notificacion();

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
        super.onDestroy();
        if(musica!=null){
            musica.stop();
            musica.release();
            musica = null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void notificacion(){
        AudioAttributes audioAttr = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build();
        
        NotificationManager nmanager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel("principal","principal",NotificationManager.IMPORTANCE_DEFAULT);
        nmanager.createNotificationChannel(channel);
        channel.setLightColor(Color.WHITE);
        channel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM), audioAttr);

        Notification.Builder builder= new Notification.Builder(this,"principal");

        builder.setSmallIcon(R.drawable.ic_videogame_asset_black_24dp);
        builder.setContentTitle("¿Quieres jugar de una vez?");
        builder.setContentText("Gracias ^_^");
        builder.setLights(Color.BLUE,1,0);
        builder.setColor(R.drawable.ic_videogame_asset_black_24dp);

        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_videogame_asset_black_24dp));
        builder.setOngoing(false);

        builder.setColorized(true);


        nmanager.notify(1,builder.build());
    }
}
