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
    protected int vida, vidaMaxima, ataque, defensa, mana, velocidad, puntuacion;
    protected boolean colliding; //Nos detecta si está colisionando o no
    private HashSet<Integer> moving;

    public Personaje(String rutaTextura) {
        this.moving = new HashSet<Integer>();
        //Atributos
        velocidad = 10;
        vida = 100;
        vidaMaxima = 100;
        mana = 100;
        ataque = 100;
        defensa = 100;
        puntuacion = 1000;
        colliding = false;
        objetos = new ArrayList<Objeto>();

        sprite = new Sprite(new Texture(rutaTextura));
        sprite.setBounds(0, 0, Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 7);
        this.setSize(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 7);
        this.setOrigin(this.sprite.getWidth() / 2, this.sprite.getHeight() / 2);
        sprite.setOrigin(this.getOriginX(), this.getOriginY());
        addListener(new EscuchadorJugador(this));
    }

    public Personaje(String rutaTextura, float x, float y) {
        this.moving = new HashSet<Integer>();
        velocidad = 10;
        vida = 100;
        vidaMaxima = 100;
        mana = 100;
        ataque = 100;
        defensa = 100;
        objetos = new ArrayList<Objeto>();

        sprite = new Sprite(new Texture(rutaTextura));
        sprite.setBounds(x, y, Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 7);
        this.setSize(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 7);
        this.setPosition(x, y); //Cambio posición del actor
        this.setOrigin(this.sprite.getWidth() / 2, this.sprite.getHeight() / 2);
        sprite.setOrigin(this.getOriginX(), this.getOriginY());
        addListener(new EscuchadorJugador(this));
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

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
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

    public boolean checkCollision(Objeto c) {
        boolean overlaps = getHitBox().overlaps(c.getHitBox());
        if (overlaps && colliding == false) {
            colliding = true;
            Gdx.app.log("Colisionando", "con " + c.getClass().getName());
        } else if (!overlaps) {
            colliding = false;
        }
        return colliding;
    }

    public boolean checkCollision(PolloMalvado enemigo) {
        boolean overlaps = getHitBox().overlaps(enemigo.getHitBox());
        if (overlaps && colliding == false) {
            colliding = true;
            Gdx.app.log("Colisionando", "con " + enemigo.getClass().getName());
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
        if (getX() <= -getWidth()) {
            setX(Gdx.graphics.getWidth());
        } else {
            MoveByAction moveLeftAction = new MoveByAction();
            moveLeftAction.setAmount(-this.velocidad, 0);
            moveLeftAction.setDuration(delta);
            addAction(moveLeftAction);
        }
    }

    public void moveUp(float delta) {
        if (getY() >= Gdx.graphics.getHeight()) {
            setY(-getHeight());
        } else {
            MoveByAction moveUpAction = new MoveByAction();
            moveUpAction.setAmount(0, this.velocidad);
            moveUpAction.setDuration(delta);
            addAction(moveUpAction);
        }
    }

    public void moveDown(float delta) {
        if (getY() <= -getHeight()) {
            setY(Gdx.graphics.getHeight());
        } else {
            MoveByAction moveDownAction = new MoveByAction();
            moveDownAction.setAmount(0, -this.velocidad);
            moveDownAction.setDuration(delta);
            addAction(moveDownAction);
        }
    }

    public void moveRight(float delta) {
        if (getX() >= Gdx.graphics.getWidth()) {
            setX(-getWidth());
        } else {
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
        ParallelAction pa = new ParallelAction(mta);
        addAction(pa);
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
        SequenceAction escalarseDanio = new SequenceAction(danioDisminuir,
                danioDisminuir2, danioAumentar, danioAumentar2);
        addAction(new ParallelAction(parpadear, escalarseDanio));
    }

    public void movimientoFuego() {
        if (getY() <= -getHeight()) {
            MoveByAction moveUpAction = new MoveByAction();
            moveUpAction.setAmount(0, this.velocidad);
            moveUpAction.setDuration(1);
            addAction(moveUpAction);
        } else {
            MoveByAction moveDownAction = new MoveByAction();
            moveDownAction.setAmount(0, -this.velocidad);
            moveDownAction.setDuration(1);
            addAction(moveDownAction);
        }
    }
}

