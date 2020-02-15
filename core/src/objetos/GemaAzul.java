package objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class GemaAzul extends Objeto {
    private int puntuacion;
    private Sound sound;

    /**
     * Contructor del objeto, el cual incluye su sprite y su sonido
     *
     * @param x indica la posicion x del mapa
     * @param y indica la posicion y del mapa
     */
    public GemaAzul(float x, float y) {
        super("objetos/gemaazul.png", x, y, Gdx.graphics.getWidth() / 20, Gdx.graphics.getHeight() / 20);
        sound = Gdx.audio.newSound(Gdx.files.internal("sonidos/gema.mp3"));
        puntuacion = 1000;
    }

    /**
     * Getter de puntuacion
     *
     * @return Devuelve un entero
     */
    public int getPuntuacion() {
        return puntuacion;
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
