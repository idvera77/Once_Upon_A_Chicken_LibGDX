package actores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Cuervo extends Personaje {
    private Sound sound;

    public Cuervo() {
        super("personajes/cuervo.png");
        sound = Gdx.audio.newSound(Gdx.files.internal("sonidos/dañoenemigo.mp3"));
    }

    public Cuervo(float x, float y) {
        super("personajes/cuervo.png", x, y);
        sound = Gdx.audio.newSound(Gdx.files.internal("sonidos/dañoenemigo.mp3"));
    }

    public Sound getSound() {
        return sound;
    }
}
