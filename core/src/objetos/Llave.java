package objetos;

import com.badlogic.gdx.Gdx;

import java.util.Random;

public class Llave extends Objeto {
    public Llave(float x, float y) {
        super("objetos/llave.png", x, y, Gdx.graphics.getWidth()/15, Gdx.graphics.getHeight()/15);
    }
}
