package actores;

import com.badlogic.gdx.audio.Sound;

public class PolloMalvado extends Personaje {
    private Sound sound;

    public PolloMalvado() {
        super("personajes/pollo.png");

    }

    public PolloMalvado(float x, float y) {
        super("personajes/pollo.png", x, y);
    }
}
