package objetos;

import com.badlogic.gdx.Gdx;

public class GemaAzul extends Objeto {
    private int puntuacion;

    public GemaAzul(float x, float y) {
        super("objetos/gemaazul.png", x, y, Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/10);
        puntuacion = 1000;
    }

    public int getPuntuacion() {
        return puntuacion;
    }
}
