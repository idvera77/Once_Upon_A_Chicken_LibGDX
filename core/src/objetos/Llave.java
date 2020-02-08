package objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.Random;

public class Llave extends Objeto {
    private Sound sound;

    public Llave(float x, float y) {
        super("objetos/llave.png", x, y, Gdx.graphics.getWidth()/15, Gdx.graphics.getHeight()/15);
        sound = Gdx.audio.newSound(Gdx.files.internal("sonidos/llave.mp3"));
    }

    public Sound getSound() {
        return sound;
    }

}
