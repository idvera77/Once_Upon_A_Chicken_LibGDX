package objetos;

import com.badlogic.gdx.Gdx;

public class Salir extends Objeto {

    /**
     * Contructor del objeto, el cual incluye su sprite y su sonido
     *
     * @param x indica la posicion x del mapa
     * @param y indica la posicion y del mapa
     */
    public Salir(float x, float y) {
        super("objetos/puerta.png", x, y, Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 15);
    }
}
