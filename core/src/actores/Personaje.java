package actores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import java.util.ArrayList;
import java.util.HashSet;

import escuchadores.EscuchadorJugador;
import objetos.Objeto;

public class Personaje extends Actor {
    protected Sprite sprite;
    protected ArrayList<Objeto> objetos;
    protected int vida, vidaMaxima, puntuacion;
    protected boolean colliding; //Nos detecta si está colisionando o no

    private HashSet<Integer> moving;

    //Constructor de Heroe
    public Personaje(String rutaTextura) {
        this.moving = new HashSet<Integer>();
        vida = 100;
        vidaMaxima = 100;
        puntuacion = 0;
        colliding = false;
        objetos = new ArrayList<>();
        sprite = new Sprite(new Texture(rutaTextura));
        sprite.setBounds(sprite.getX(), sprite.getY(), Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 7);
        this.setSize(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 7);
        this.setPosition(0, Gdx.graphics.getHeight() / 23 * 4);
        this.setOrigin(this.sprite.getWidth() / 2, this.sprite.getHeight() / 2);
        sprite.setOrigin(this.getOriginX(), this.getOriginY());
        addListener(new EscuchadorJugador(this));
    }

    public Personaje(String rutaTextura, float x, float y) {
        this.moving = new HashSet<Integer>();
        objetos = new ArrayList<Objeto>();
        sprite = new Sprite(new Texture(rutaTextura));
        sprite.setBounds(x, y, Gdx.graphics.getWidth() / 31 * 2, Gdx.graphics.getHeight() / 23 * 2);
        this.setSize(Gdx.graphics.getWidth() / 31 * 2, Gdx.graphics.getHeight() / 23 * 2);
        this.setPosition(x, y); //Cambio posición del actor
        this.setOrigin(this.sprite.getWidth() / 2, this.sprite.getHeight() / 2);
        sprite.setOrigin(this.getOriginX(), this.getOriginY());
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public void setVidaMaxima(int vidaMaxima) {
        this.vidaMaxima = vidaMaxima;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public void addObjeto(Objeto o) {
        this.objetos.add(o);
    }

    public ArrayList<Objeto> getObjetos() {
        return objetos;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        sprite.setPosition(getX(), getY());
        sprite.setScale(this.getScaleX(), getScaleY());
        sprite.setRotation(this.getRotation());
        sprite.setColor(getColor().r, getColor().g, getColor().b, getColor().a);
        sprite.draw(batch);
        //para cada objeto
        for (Objeto o : this.objetos) {
            //lo muevo con el sprite
            o.moverA(sprite.getX(), sprite.getY());
            //lo dibujo
            o.draw(batch, parentAlpha);
        }
    }

    //COLISIONES
    public Rectangle getHitBox() {
        return sprite.getBoundingRectangle();
    }

    /**
     * @param c
     * @return
     */
    public boolean checkCollision(Objeto c) {
        boolean overlaps = getHitBox().overlaps(c.getHitBox());
        if (overlaps && colliding == false) {
            colliding = true;
        } else if (!overlaps) {
            colliding = false;
        }
        return colliding;
    }

    /**
     * @param enemigo
     * @return
     */
    public boolean checkCollision(Personaje enemigo) {
        boolean overlaps = getHitBox().overlaps(enemigo.getHitBox());
        if (overlaps && colliding == false) {
            colliding = true;
        } else if (!overlaps) {
            colliding = false;
        }
        return colliding;
    }

    public HashSet<Integer> getMoving() {
        return moving;
    }

    public void startMoving(Integer direccion) {
        this.moving.add(direccion);
    }

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

    public void moveLeft(float delta) {
        if (getX() <= 0) {
            setX(0);
        } else {
            this.sprite = new Sprite(new Texture("personajes/polloizquierda.png"));
            sprite.setBounds(sprite.getX(), sprite.getY(), Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 7);
            MoveByAction moveLeftAction = new MoveByAction();
            moveLeftAction.setAmount(-5, 0);
            moveLeftAction.setDuration(delta);
            addAction(moveLeftAction);
        }
    }

    public void moveRight(float delta) {
        if (getX() >= Gdx.graphics.getWidth() - this.sprite.getWidth()) {
            setX(Gdx.graphics.getWidth() - this.sprite.getWidth());
        } else {
            this.sprite = new Sprite(new Texture("personajes/polloderecha.png"));
            sprite.setBounds(sprite.getX(), sprite.getY(), Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 7);
            MoveByAction moveRightAction = new MoveByAction();
            moveRightAction.setAmount(5, 0);
            moveRightAction.setDuration(delta);
            addAction(moveRightAction);
        }
    }

    public void salto() {
        SequenceAction salto = new SequenceAction(
                Actions.moveBy(0, Gdx.graphics.getHeight() / 33 * 11, 0.6f),
                Actions.moveBy(0, -Gdx.graphics.getHeight() / 33 * 11, 0.6f)
        );
        addAction(salto);
    }

    public int recibirDaño() {
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
        return vida;
    }

    public void movimientoEnemigo() {
        MoveByAction moveLeftAction = new MoveByAction();
        moveLeftAction.setAmount(-10, 0);
        moveLeftAction.setDuration(10);
        addAction(moveLeftAction);
    }

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


