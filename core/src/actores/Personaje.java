package actores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;

import objetos.Objeto;

public class Personaje extends Actor {
    protected Sprite sprite;
    protected ArrayList<Objeto> objetos;
    protected int vida, vidaMaxima, puntuacion;
    protected boolean colliding; //Nos detecta si está colisionando o no

    /**
     * Constructor utilizado principalmente para crear al heroe ya que dispone de parametros que no
     * utilizan el resto de personajes o enemigos.
     *
     * @param rutaTextura Añadimos la textura que tendra el sprite
     */
    public Personaje(String rutaTextura) {
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
    }

    /**
     * Constructor utilizado principalmente para enemigos
     *
     * @param rutaTextura Añadimos la textura que tendra el sprite
     * @param x           Nos ayuda a indicar la posicion x donde debe aparecer el enemigo
     * @param y           Nos ayuda a indicar la posicion y donde debe aparecer el enemigo
     */
    public Personaje(String rutaTextura, float x, float y) {
        objetos = new ArrayList<Objeto>();
        sprite = new Sprite(new Texture(rutaTextura));
        sprite.setBounds(x, y, Gdx.graphics.getWidth() / 31 * 2, Gdx.graphics.getHeight() / 23 * 2);
        this.setSize(Gdx.graphics.getWidth() / 31 * 2, Gdx.graphics.getHeight() / 23 * 2);
        this.setPosition(x, y); //Cambio posición del actor
        this.setOrigin(this.sprite.getWidth() / 2, this.sprite.getHeight() / 2);
        sprite.setOrigin(this.getOriginX(), this.getOriginY());
    }

    /**
     * Getter de Vida
     *
     * @return devuelve un entero con la vida del personaje
     */
    public int getVida() {
        return vida;
    }

    /**
     * Setter de vida
     *
     * @param vida variable de tipo entero que modifica el valor de la vida del personaje
     */
    public void setVida(int vida) {
        this.vida = vida;
    }

    /**
     * Getter de Vida Maxima
     *
     * @return devuelve un entero con la vida maxima del personaje
     */
    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    /**
     * Getter de ArrayList<Objeto>
     *
     * @return devuelve un ArrayList de objetos
     */
    public ArrayList<Objeto> getObjetos() {
        return objetos;
    }

    /**
     * Funcion para añadir objetos a la lista
     *
     * @param objeto El objeto a añadir
     */
    public void addObjeto(Objeto objeto) {
        this.objetos.add(objeto);
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
        for (Objeto objeto : this.objetos) {
            //lo muevo con el sprite
            objeto.moverA(sprite.getX(), sprite.getY());
            //lo dibujo
            objeto.draw(batch, parentAlpha);
        }
    }

    //COLISIONES
    public Rectangle getHitBox() {
        return sprite.getBoundingRectangle();
    }

    /**
     * Funcion que nos permite realizar las colisiones entre un personaje y un objeto
     *
     * @param objeto Objeto con el cual colisionamos
     * @return Devuelve True si colisiona o false si no
     */
    public boolean checkCollision(Objeto objeto) {
        boolean overlaps = getHitBox().overlaps(objeto.getHitBox());
        if (overlaps && colliding == false) {
            colliding = true;
        } else if (!overlaps) {
            colliding = false;
        }
        return colliding;
    }

    /**
     * Funcion que nos permite realizar las colisiones entre un personaje y un objeto
     *
     * @param enemigo Enemigo con el cual colisionamos
     * @return Devuelve True si colisiona o false si no
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
}


