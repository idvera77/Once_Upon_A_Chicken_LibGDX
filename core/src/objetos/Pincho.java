package objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Pincho extends Objeto{
    private Sound sound;

    public Pincho(float x, float y) {
        super("objetos/pincho.png", x, y, Gdx.graphics.getWidth()/15, Gdx.graphics.getHeight()/11);
        sound = Gdx.audio.newSound(Gdx.files.internal("sonidos/puerta.mp3"));
    }

    public Sound getSound() {
        return sound;
    }
}


