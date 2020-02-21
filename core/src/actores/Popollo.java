package actores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import java.util.HashSet;

import escuchadores.EscuchadorJugador;

public class Popollo extends Personaje {
    private Sound sound;
    private HashSet<Integer> moving;
    private Texture derecha, izquierda;

    /**
     * Contructor sin parametros, el cual incluye su sprite y su sonido
     */
    public Popollo() {
        super("personajes/polloderecha.png");
        derecha = new Texture("personajes/polloderecha.png");
        izquierda = new Texture("personajes/polloizquierda.png");
        sound = Gdx.audio.newSound(Gdx.files.internal("sonidos/daño.mp3"));
        moving = new HashSet<Integer>();
        addListener(new EscuchadorJugador(this));
    }

    /**
     * Contructor del personaje con posiciones, el cual incluye su sprite y su sonido
     *
     * @param x indica la posicion x del mapa
     * @param y indica la posicion y del mapa
     */
    public Popollo(float x, float y) {
        super("personajes/polloderecha.png", x, y);
        sound = Gdx.audio.newSound(Gdx.files.internal("sonidos/daño.mp3"));
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
     * Nos ayuda a realizar el movimiento con el escuchador
     *
     * @return devuelve un HashSet de entero
     */
    public HashSet<Integer> getMoving() {
        return moving;
    }

    /**
     * Inicia el movimiento del personaje
     */
    public void startMoving(Integer direccion) {
        this.moving.add(direccion);
    }

    /**
     * Para el movimiento del personaje
     */
    public void stopMoving(int direccion) {
        this.moving.remove(direccion);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (moving.contains(0)) {
            moveLeft(delta);
        }
        if (moving.contains(1)) {
            moveRight(delta);
        }
    }

    /**
     * Funcion que nos permite mover hacia la izquierda al personaje, asegurandonos que no puede salirse
     * por los bordes del mapa
     *
     * @param delta tiempo en ejecutarse la accion
     */
    public void moveLeft(float delta) {
        if (getX() <= 0) {
            setX(0);
        } else {
            this.sprite.setTexture(izquierda);
            sprite.setBounds(sprite.getX(), sprite.getY(), Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 7);
            MoveByAction moveLeftAction = new MoveByAction();
            moveLeftAction.setAmount(-5, 0);
            moveLeftAction.setDuration(delta);
            addAction(moveLeftAction);
        }
    }

    /**
     * Funcion que nos permite mover hacia la izquierda al personaje, asegurandonos que no puede salirse
     * por los bordes del mapa
     *
     * @param delta tiempo en ejecutarse la accion
     */
    public void moveRight(float delta) {
        if (getX() >= Gdx.graphics.getWidth() - this.sprite.getWidth()) {
            setX(Gdx.graphics.getWidth() - this.sprite.getWidth());
        } else {
            this.sprite.setTexture(derecha);
            sprite.setBounds(sprite.getX(), sprite.getY(), Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 7);
            MoveByAction moveRightAction = new MoveByAction();
            moveRightAction.setAmount(5, 0);
            moveRightAction.setDuration(delta);
            addAction(moveRightAction);
        }
    }

    /**
     * Funcion para realizar los saltos, activa una secuencia de movimiento hacia arriba y luego abajo
     */
    public void salto() {
        SequenceAction salto = new SequenceAction(
                Actions.moveBy(0, Gdx.graphics.getHeight() / 33 * 11, 0.6f),
                Actions.moveBy(0, -Gdx.graphics.getHeight() / 33 * 11, 0.6f)
        );
        addAction(salto);
    }

    /**
     * Funcion que modifica al actor al recibir daño y luego reduce su vida en 20 puntos
     */
    public void recibirDaño() {
        SequenceAction parpadear = new SequenceAction(new SequenceAction(
                Actions.alpha(0.1f, 0.2f),
                Actions.alpha(1f, 0.2f),
                Actions.alpha(0.1f, 0.2f),
                Actions.alpha(1f, 0.2f)
        ), new SequenceAction(
                Actions.alpha(0.1f, 0.2f),
                Actions.alpha(1f, 0.2f),
                Actions.alpha(0.1f, 0.2f),
                Actions.alpha(1f, 0.2f)));
        addAction(new ParallelAction(parpadear));
        this.vida -= 20;
    }
}
