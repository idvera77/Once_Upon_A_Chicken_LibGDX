package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ivan.popollo_adventures.Juego;

import actores.*;
import basededatos.JuegoDataBase;
import objetos.*;


public class Pantalla3 extends BaseScreen {
    private Texture texturaObjeto;
    private TextureRegion texturaRegion;
    private TextureRegion[] framesTextura;
    private Animation animacionObjeto;
    private float duracion = 0f;
    private static final int ANCHO = Gdx.graphics.getWidth() / 5;
    private static final int ALTO = Gdx.graphics.getHeight() / 9;


    public Pantalla3(Juego game, Popollo popollo, JuegoDataBase db) {
        super(game, popollo, db);
        this.fondo = new Texture("fondospantalla/cementerio.png");
        this.musica = Gdx.audio.newMusic(Gdx.files.internal("sonidos/cementerio.mp3")); //Musica de la pantalla
        musica.setVolume(0.2f); //Volumen de la musica
        musica.play(); //Iniciamos la musica

        //Añadimos los enemigos.
        cuervo = new Cuervo(Gdx.graphics.getWidth() + popollo.getX() * 3, Gdx.graphics.getHeight() / 23 * 4);
        pantalla.addActor(cuervo);
        sierra = new Sierra(Gdx.graphics.getWidth() / 31 * 7, Gdx.graphics.getWidth() / 31 * 1 - popollo.getY() * 5);
        pantalla.addActor(sierra);
        pincho = new Pincho(Gdx.graphics.getWidth() / 31 * 15, Gdx.graphics.getWidth() / 31 * 3);
        pantalla.addActor(pincho);

        //Añadimos la llave
        llave = new Llave(Gdx.graphics.getWidth() / 31 * 10, Gdx.graphics.getHeight() / 23 * 6);
        pantalla.addActor(llave);

        //Añadimos las gemas
        gemaAzul = new GemaAzul(Gdx.graphics.getWidth() / 31 * 29, Gdx.graphics.getHeight() / 23 * 6);
        pantalla.addActor(gemaAzul);
        gemaRoja = new GemaRoja(Gdx.graphics.getWidth() / 17 * 13, Gdx.graphics.getHeight() / 23 * 12);
        pantalla.addActor(gemaRoja);

        //Añadimos la salida del nivel y la enviamos atras para que los personajes sean visibles al pasar por ella.
        puerta = new Puerta(Gdx.graphics.getWidth() / 31 * 30, Gdx.graphics.getHeight() / 23 * 10);
        pantalla.addActor(puerta);
    }

    @Override
    public void show() {
        //Variables y bucle necesario para realizar la animacion de la exclamacion que indica la
        //salida del nivel.
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
        //Si el popollo tiene en su inventario un objeto (solo podemos guardar la llave) se completa
        // y nos movemos a la siguiente pantalla parando ademas la musica
        //Aprovechamos para guardar la puntuacion en la base de datos
        if (popollo.checkCollision(puerta)) {
            if (popollo.getObjetos().size() == 1) {
                juegoDataBase.terminarPartida(popollo.getPuntuacion());
                popollo.getObjetos().remove(0);
                puerta.getSound().play(0.7f);
                musica.stop();
                musica.dispose();
                pantalla.dispose();
                game.setPantallaActual(new Tienda(this.game, popollo, juegoDataBase, 3));
            }
        }

        //Posicion y duracion de la animacion
        duracion += delta;
        TextureRegion frame = (TextureRegion) animacionObjeto.getKeyFrame(duracion, true);
        pantalla.getBatch().begin();
        pantalla.getBatch().draw(frame, Gdx.graphics.getWidth() / 50 * 48, Gdx.graphics.getHeight() / 23 * 13);//posicion de la animación
        pantalla.getBatch().end();

        //Moviendo enemigos
        movimientoEnemigos();

        //Colision con la sierra que aparece en el suelo, vuelve a reaparecer al cabo de un tiempo
        if (popollo.checkCollision(sierra)) {
            recibirGolpe();
            sierra.remove();
            sierra = new Sierra(Gdx.graphics.getWidth() / 31 * 7, Gdx.graphics.getWidth() / 31 * 1 - popollo.getY() * 5);
            pantalla.addActor(sierra);
        }
    }
}
