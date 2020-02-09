package actores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Popollo extends Personaje {
    private Sound sound;

    public Popollo() {
        super("personajes/pollo.png");
        sound = Gdx.audio.newSound(Gdx.files.internal("sonidos/daño.mp3"));
    }

    public Popollo(float x, float y) {
        super("personajes/pollo.png", x, y);
    }

    public Sound getSound() {
        return sound;
    }
}
