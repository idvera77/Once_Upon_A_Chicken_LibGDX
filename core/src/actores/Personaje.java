package actores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import java.util.ArrayList;
import java.util.HashSet;

import escuchadores.EscuchadorJugador;
import objetos.Objeto;

public class Personaje extends Actor {
    protected Sprite sprite;
    protected ArrayList<Objeto> objetos;
    protected int vida, vidaMaxima, velocidad, puntuacion;
    protected boolean colliding; //Nos detecta si está colisionando o no

    private HashSet<Integer> moving;

    //Constructor de Heroe
    public Personaje(String rutaTextura) {
        this.moving = new HashSet<Integer>();
        velocidad = 5;
        vida = 100;
        vidaMaxima = 100;
        puntuacion = 1000;
        colliding = false;
        objetos = new ArrayList<>();
        sprite = new Sprite(new Texture(rutaTextura));
        sprite.setBounds(50, Gdx.graphics.getHeight()/5, Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 7);
        this.setSize(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 7);
        this.setPosition(50, Gdx.graphics.getHeight()/5);
        this.setOrigin(this.sprite.getWidth() / 2, this.sprite.getHeight() / 2);
        sprite.setOrigin(this.getOriginX(), this.getOriginY());

        addListener(new EscuchadorJugador(this));
    }


    public Personaje(String rutaTextura, float x, float y) {
        this.moving = new HashSet<Integer>();
        velocidad = 10;
        objetos = new ArrayList<Objeto>();
        sprite = new Sprite(new Texture(rutaTextura));
        sprite.setBounds(x, y, Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 7);
        this.setSize(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 7);
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

    //MOVIMIENTO
    public void moverAPixel(float x, float y) {
        this.setPosition(x, y);
        sprite.setPosition(x, y);
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
            moveUp(delta);
        }
        if (moving.contains(1)) {
            moveRight(delta);
        }
        if (moving.contains(2)) {
            moveDown(delta);
        }
        if (moving.contains(3)) {
            moveLeft(delta);
        }
    }

    public void moveLeft(float delta) {
        if (getX() <= 0) {
            setX(0);
        } else {
            this.sprite = new Sprite(new Texture("personajes/polloizquierda.png"));
            sprite.setBounds(sprite.getX(), sprite.getY(), Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 7);
            MoveByAction moveLeftAction = new MoveByAction();
            moveLeftAction.setAmount(-this.velocidad, 0);
            moveLeftAction.setDuration(delta);
            addAction(moveLeftAction);
        }
    }

    public void moveUp(float delta) {
        if (getY() >= Gdx.graphics.getHeight()-this.sprite.getHeight()) {
            setY(Gdx.graphics.getHeight()-this.sprite.getHeight());
        } else {
            MoveByAction moveUpAction = new MoveByAction();
            moveUpAction.setAmount(0, this.velocidad);
            moveUpAction.setDuration(delta);
            addAction(moveUpAction);
        }
    }

    public void moveDown(float delta) {
        if (getY() <= Gdx.graphics.getHeight()/5) {
            setY(Gdx.graphics.getHeight()/5);
        } else {
            MoveByAction moveDownAction = new MoveByAction();
            moveDownAction.setAmount(0, -this.velocidad);
            moveDownAction.setDuration(delta);
            addAction(moveDownAction);
        }
    }

    public void moveRight(float delta) {
        if (getX() >= Gdx.graphics.getWidth()-this.sprite.getWidth()) {
            setX(Gdx.graphics.getWidth()-this.sprite.getWidth());
        }  else {
            this.sprite = new Sprite(new Texture("personajes/polloderecha.png"));
            sprite.setBounds(sprite.getX(), sprite.getY(), Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 7);
            MoveByAction moveRightAction = new MoveByAction();
            moveRightAction.setAmount(this.velocidad, 0);
            moveRightAction.setDuration(delta);
            addAction(moveRightAction);
        }
    }

    public void recibirDaño() {
        MoveToAction mta = new MoveToAction();
        mta.setPosition(getX() - 40, getY());
        mta.setDuration(0);
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
        ScaleByAction danioDisminuir = new ScaleByAction();
        danioDisminuir.setAmount(-1f, -0.5f);
        danioDisminuir.setDuration(0.4f);
        ScaleByAction danioDisminuir2 = new ScaleByAction();
        danioDisminuir2.setAmount(+0.5f, -0.5f);
        danioDisminuir2.setDuration(0.4f);
        ScaleByAction danioAumentar = new ScaleByAction();
        danioAumentar.setAmount(+0.5f, +0.5f);
        danioAumentar.setDuration(0.4f);
        ScaleByAction danioAumentar2 = new ScaleByAction();
        danioAumentar2.setAmount(+0, +0.5f);
        danioAumentar2.setDuration(0.4f);
        SequenceAction escalarseDanio = new SequenceAction(mta, danioDisminuir, danioDisminuir2, danioAumentar, danioAumentar2);
        addAction(new ParallelAction(parpadear, escalarseDanio));
    }

    public void movimientoEnemigo() {
        if (getX() <= -getWidth()) {
            setX(Gdx.graphics.getWidth());
        } else {
            MoveByAction moveLeftAction = new MoveByAction();
            moveLeftAction.setAmount(-this.velocidad, 0);
            moveLeftAction.setDuration(this.velocidad);
            addAction(moveLeftAction);
        }
    }

    public void movimientoSierra() {
        if (getY() >= Gdx.graphics.getHeight() - this.sprite.getHeight()) {
            setY(Gdx.graphics.getHeight() - this.sprite.getHeight());
        } else if (getY() >= Gdx.graphics.getHeight() / 5 - this.sprite.getHeight()) {
            MoveByAction moveDownAction = new MoveByAction();
            moveDownAction.setAmount(0, -this.velocidad);
            moveDownAction.setDuration(1);
            addAction(moveDownAction);
        } else {
            MoveByAction moveUpAction = new MoveByAction();
            moveUpAction.setAmount(0, this.velocidad);
            moveUpAction.setDuration(2);
            addAction(moveUpAction);
        }
    }

    public void ataqueDisparo() {
        MoveByAction moveRightAction = new MoveByAction();
        moveRightAction.setAmount(1000, 0);
        moveRightAction.setDuration(1);
        addAction(moveRightAction);
    }

    public int disminuirVida(){
        this.vida -= 20;
        return vida;
    }


}


