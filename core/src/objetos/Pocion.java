package objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Pocion extends Objeto {
    private Sound sound;

    /**
     * Contructor del objeto, el cual incluye su sprite y su sonido
     *
     * @param x indica la posicion x del mapa
     * @param y indica la posicion y del mapa
     */
    public Pocion(float x, float y) {
        super("objetos/pocion.png", x, y, Gdx.graphics.getWidth() / 9, Gdx.graphics.getHeight() / 7);
        sound = Gdx.audio.newSound(Gdx.files.internal("sonidos/pocion.mp3"));
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
