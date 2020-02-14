package objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Pocion extends Objeto {
    private Sound sound;

    public Pocion(float x, float y) {
        super("objetos/pocion.png", x, y, Gdx.graphics.getWidth() / 9, Gdx.graphics.getHeight() / 7);
        sound = Gdx.audio.newSound(Gdx.files.internal("sonidos/pocion.mp3"));
    }

    public Sound getSound() {
        return sound;
    }
}
