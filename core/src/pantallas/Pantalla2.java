package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ivan.popollo_adventures.Juego;

import java.util.Random;

import actores.Cuervo;
import actores.Sierra;
import objetos.GemaAzul;
import objetos.GemaRoja;
import objetos.Llave;
import objetos.Pincho;
import objetos.Puerta;


public class Pantalla2 extends BaseScreen {
    private Texture texturaObjeto;
    private TextureRegion texturaRegion;
    private TextureRegion[] framesTextura;
    private Animation animacionObjeto;
    private float duracion = 0f;
    private static final int ANCHO = Gdx.graphics.getWidth() / 5;
    private static final int ALTO = Gdx.graphics.getHeight() / 9;

    public Pantalla2(Juego game) {
        super(game);
        this.fondo = new Texture("fondospantalla/montaña.png");
        this.sound = Gdx.audio.newSound(Gdx.files.internal("sonidos/nieve.mp3"));
        sound.play(0.2f);

        //Añadimos los enemigos.
        enemigoTerrestre = new Cuervo(Gdx.graphics.getWidth() + popollo.getX() * 3, Gdx.graphics.getHeight() / 23 * 4);
        pantalla.addActor(enemigoTerrestre);
        sierra = new Sierra(Gdx.graphics.getWidth() / 31 * 7, Gdx.graphics.getWidth() / 31 * 1 - popollo.getY() * 5);
        pantalla.addActor(sierra);
        pincho = new Pincho(Gdx.graphics.getWidth() / 31 * 20, Gdx.graphics.getWidth() / 31 * 3);
        pantalla.addActor(pincho);

        //Añadimos la llave
        llave = new Llave(Gdx.graphics.getWidth() / 31 * 28, Gdx.graphics.getHeight() / 23 * 6);
        pantalla.addActor(llave);

        //Añadimos las gemas
        gemaAzul = new GemaAzul(Gdx.graphics.getWidth() / 31 * 15, Gdx.graphics.getHeight() / 23 * 6);
        pantalla.addActor(gemaAzul);

        gemaRoja = new GemaRoja(Gdx.graphics.getWidth() / 31 * 27, Gdx.graphics.getHeight() / 23 * 12);
        pantalla.addActor(gemaRoja);

        //Añadimos la salida del nivel y la enviamos atras para que los personajes sean visibles al pasar por ella.
        puerta = new Puerta(0, Gdx.graphics.getHeight() / 23 * 11);
        pantalla.addActor(puerta);
    }

    @Override
    public void show() {
        texturaObjeto = new Texture("objetos/animacionpuerta.png");
        texturaRegion = new TextureRegion(texturaObjeto, ANCHO, ALTO);
        TextureRegion[][] temp = texturaRegion.split(ANCHO / 3, ALTO);
        framesTextura = new TextureRegion[temp.length * temp[0].length];

        int indice = 0;
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length; j++) {
                framesTextura[indice++] = temp[i][j];
            }
        }
        animacionObjeto = new Animation(0.2f, framesTextura);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (popollo.checkCollision(puerta)) {
            if (popollo.getObjetos().size() >= 1) {
                puerta.getSound().play(1f);
                sound.stop();
                game.setPantallaActual(new Tienda(this.game, 2));
            }
        }
        duracion += delta;
        TextureRegion frame = (TextureRegion) animacionObjeto.getKeyFrame(duracion, true);
        pantalla.getBatch().begin();
        pantalla.getBatch().draw(frame, 0, Gdx.graphics.getHeight() / 23 * 14);//posicion de la animación
        pantalla.getBatch().end();
        //Moviendo enemigos
        movimientoEnemigos();
    }

    public Sound getSound() {
        return sound;
    }

    @Override
    public void dispose() {
        sound.dispose();
    }
}
