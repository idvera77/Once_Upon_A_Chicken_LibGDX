package actores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

public class Sierra extends Personaje {

    /**
     * Contructor sin parametros, el cual incluye su sprite
     */
    public Sierra() {
        super("objetos/sierra.png");
    }

    /**
     * Contructor del personaje con posiciones, el cual incluye su sprite
     *
     * @param x indica la posicion x del mapa
     * @param y indica la posicion y del mapa
     */
    public Sierra(float x, float y) {
        super("objetos/sierra.png", x, y);
    }

    /**
     * Funcion de movimiento, la sierra siempre anda en linea recta hacia arriba y hacia abajo
     * dependiendo de la altura en la que se encuentre
     */
    public void movimientoSierra() {
        if (getY() >= Gdx.graphics.getHeight() / 33 * 6 - this.sprite.getHeight()) {
            MoveByAction moveDownAction = new MoveByAction();
            moveDownAction.setAmountY(-10);
            moveDownAction.setDuration(1);
            addAction(moveDownAction);
        } else {
            MoveByAction moveUpAction = new MoveByAction();
            moveUpAction.setAmountY(10);
            moveUpAction.setDuration(2);
            addAction(moveUpAction);
        }
    }
}


