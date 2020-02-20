package servicios;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import com.ivan.popollo_adventures.R;

public class ServicioNotificacion extends Service {
    private Handler handler;

    public void startNotificationListener() {
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                ShowNotification();
            }
        }).start();
    }

    //Aqui esta la clave de la notificacion de este juego, cuando pasa 20 segundos en la pantalla inicial se lanza
    //Para no ser pesados luego cancelamos la notificacion
    @Override
    public void onCreate() {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                startNotificationListener();
            }
        }, 60000);
        super.onCreate();
    }

    //Funcion que crea nuestra notificacion con mensaje e icono personalizado
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void ShowNotification() {
        NotificationManager nmanager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel("principal", "principal", NotificationManager.IMPORTANCE_DEFAULT);
        nmanager.createNotificationChannel(channel);
        channel.setLightColor(Color.BLUE);
        AudioAttributes audioAttr = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build();
        channel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM), audioAttr);
        Notification.Builder builder = new Notification.Builder(this, "principal");
        builder.setSmallIcon(R.drawable.ic_videogame_asset_black_24dp);
        builder.setContentTitle(getString(R.string.JuegaYA));
        builder.setContentText(getString(R.string.gracias));
        builder.setLights(Color.BLUE, 1, 0);
        builder.setColor(R.drawable.ic_videogame_asset_black_24dp);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_videogame_asset_black_24dp));
        builder.setOngoing(false);
        builder.setColorized(true);
        nmanager.notify(1, builder.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        this.stopSelf();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return onBind(intent);
    }

}















