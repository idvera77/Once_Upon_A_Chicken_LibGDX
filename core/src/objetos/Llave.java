package objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Llave extends Objeto {
    private Sound sound;

    /**
     * Contructor del objeto, el cual incluye su sprite y su sonido
     *
     * @param x indica la posicion x del mapa
     * @param y indica la posicion y del mapa
     */
    public Llave(float x, float y) {
        super("objetos/llave.png", x, y, Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 15);
        sound = Gdx.audio.newSound(Gdx.files.internal("sonidos/llave.mp3"));
    }

    /**
     * Getter de Sound
     *
     * @return Devuelve el sonido
     */
    public Sound getSound() {
        return sound;
    }
}
