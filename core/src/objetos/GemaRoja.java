package objetos;

import com.badlogic.gdx.Gdx;

import java.util.Random;

public class GemaRoja extends Objeto{
    private int puntuacion;

    public GemaRoja(float x, float y) {
        super("objetos/gemaroja.png", x, y, Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/10);
        puntuacion = 500;
    }

    public int getPuntuacion() {
        return puntuacion;
    }
}
