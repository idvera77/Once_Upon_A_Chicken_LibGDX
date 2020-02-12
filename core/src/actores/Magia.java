package actores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Magia extends Personaje{
    private Sound sound;

    public Magia() {
        super("personajes/magia.png");
        sound = Gdx.audio.newSound(Gdx.files.internal("sonidos/daño.mp3"));
    }

    public Magia(float x, float y) {
        super("personajes/magia.png", x, y);
    }

    public Sound getSound() {
        return sound;
    }
}
