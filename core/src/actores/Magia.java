package actores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

public class Magia extends Personaje {
    private Sound sound;

    /**
     * Contructor sin parametros, el cual incluye su sprite y su sonido
     */
    public Magia() {
        super("personajes/magia.png");
        sound = Gdx.audio.newSound(Gdx.files.internal("sonidos/disparo.mp3"));
    }

    /**
     * Contructor del personaje con posiciones, el cual incluye su sprite y su sonido
     *
     * @param x indica la posicion x del mapa
     * @param y indica la posicion y del mapa
     */
    public Magia(float x, float y) {
        super("personajes/magia.png", x, y);
        sound = Gdx.audio.newSound(Gdx.files.internal("sonidos/disparo.mp3"));
    }

    /**
     * Getter de Sound
     *
     * @return Devuelve el sonido
     */
    public Sound getSound() {
        return sound;
    }

    /**
     * Funcion de movimiento, el personaje recorre la pantalla en linea recta hacia la izquierda o la derecha
     *
     * @param direccion esta variable de entero nos ayuda a realizar la accion hacia delante o hacia atras
     */
    public void ataqueDisparo(int direccion) {
        if (direccion == 0) {
            MoveByAction moveLeftAction = new MoveByAction();
            moveLeftAction.setAmountX(-Gdx.graphics.getWidth());
            moveLeftAction.setDuration(1);
            addAction(moveLeftAction);
        } else {
            MoveByAction moveRightAction = new MoveByAction();
            moveRightAction.setAmountX(Gdx.graphics.getWidth());
            moveRightAction.setDuration(1);
            addAction(moveRightAction);
        }
    }
}
