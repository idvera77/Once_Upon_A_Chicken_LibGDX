package objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Puerta extends Objeto {
    private Sound sound;

    public Puerta(float x, float y) {
        super("objetos/puerta.png", x, y, Gdx.graphics.getWidth()/15, Gdx.graphics.getHeight()/15);
        sound = Gdx.audio.newSound(Gdx.files.internal("sonidos/puerta.mp3"));
    }

    public Sound getSound() {
        return sound;
    }
}
