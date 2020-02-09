package objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.Random;

public class GemaRoja extends Objeto{
    private int puntuacion;
    private Sound sound;

    public GemaRoja(float x, float y) {
        super("objetos/gemaroja.png", x, y, Gdx.graphics.getWidth()/20, Gdx.graphics.getHeight()/20);
        sound = Gdx.audio.newSound(Gdx.files.internal("sonidos/gema.mp3"));
        puntuacion = 500;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public Sound getSound() {
        return sound;
    }
}
