package objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Pincho extends Objeto {

    /**
     * Contructor del objeto, el cual incluye su sprite y su sonido
     *
     * @param x indica la posicion x del mapa
     * @param y indica la posicion y del mapa
     */
    public Pincho(float x, float y) {
        super("objetos/pincho.png", x, y, Gdx.graphics.getWidth() / 22, Gdx.graphics.getHeight() / 14);
    }
}


