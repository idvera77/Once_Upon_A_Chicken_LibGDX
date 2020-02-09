package objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class GemaAzul extends Objeto {
    private int puntuacion;
    private Sound sound;

    public GemaAzul(float x, float y) {
        super("objetos/gemaazul.png", x, y, Gdx.graphics.getWidth()/20, Gdx.graphics.getHeight()/20);
        sound = Gdx.audio.newSound(Gdx.files.internal("sonidos/gema.mp3"));
        puntuacion = 1000;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public Sound getSound() {
        return sound;
    }
}
