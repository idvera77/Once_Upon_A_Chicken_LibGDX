package actores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

public class Cuervo extends Personaje {
    private Sound sound;

    /**
     * Contructor sin parametros, el cual incluye su sprite y su sonido
     */
    public Cuervo() {
        super("personajes/cuervo.png");
        sound = Gdx.audio.newSound(Gdx.files.internal("sonidos/dañoenemigo.mp3"));
    }

    /**
     * Contructor del personaje con posiciones, el cual incluye su sprite y su sonido
     *
     * @param x indica la posicion x del mapa
     * @param y indica la posicion y del mapa
     */
    public Cuervo(float x, float y) {
        super("personajes/cuervo.png", x, y);
        sound = Gdx.audio.newSound(Gdx.files.internal("sonidos/dañoenemigo.mp3"));
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
     * Funcion de movimiento, el cuervo siempre anda en linea recta hacia la izquierda
     */
    public void movimientoEnemigo() {
        MoveByAction moveLeftAction = new MoveByAction();
        moveLeftAction.setAmount(-10, 0);
        moveLeftAction.setDuration(10);
        addAction(moveLeftAction);
    }
}
