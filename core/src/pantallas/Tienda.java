package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ivan.popollo_adventures.Juego;

import actores.*;
import basededatos.JuegoDataBase;
import objetos.*;


public class Tienda extends BaseScreen {
    private int seleccion;
    private Texture texturaObjeto;
    private TextureRegion texturaRegion;
    private TextureRegion[] framesTextura;
    private Animation animacionObjeto;
    private float duracion = 0f;
    private static final int ANCHO = Gdx.graphics.getWidth() / 5;
    private static final int ALTO = Gdx.graphics.getHeight() / 9;

    public Tienda(Juego game, Popollo popollo, JuegoDataBase db, int seleccion) {
        super(game, popollo, db);
        this.seleccion = seleccion;
        this.fondo = new Texture("fondospantalla/tienda.png");
        this.musica = Gdx.audio.newMusic(Gdx.files.internal("sonidos/tienda.mp3"));
        musica.setVolume(0.4f);
        musica.play();

        //Añadimos la pocion
        pocion = new Pocion(Gdx.graphics.getWidth() / 31 * 14, Gdx.graphics.getHeight() / 23 * 11);
        pantalla.addActor(pocion);

        //Aunque no es necesario dejo la opcion de añadir los enemigos por si cambio de idea
        //Añadimos los enemigos.
        cuervo = new Cuervo(0, 0);
        sierra = new Sierra(0, 0);
        pincho = new Pincho(0, 0);

        //Añadimos la llave
        llave = new Llave(Gdx.graphics.getWidth() / 31 * 7, Gdx.graphics.getHeight() / 23 * 5);
        pantalla.addActor(llave);

        //Añadimos las gemas
        gemaAzul = new GemaAzul(0, 0);
        gemaRoja = new GemaRoja(0, 0);

        //Añadimos la salida del nivel y la enviamos atras para que los personajes sean visibles al pasar por ella.
        puerta = new Puerta(Gdx.graphics.getWidth() / 31 * 27, Gdx.graphics.getHeight() / 23 * 5);
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
        if (popollo.checkCollision(puerta)) {
            if (popollo.checkCollision(puerta)) {
                if (popollo.getObjetos().size() == 1) {
                    popollo.getObjetos().remove(0);
                    puerta.getSound().play(0.7f);
                    musica.stop();
                    musica.dispose();
                    pantalla.dispose();
                    if (seleccion == 3) {
                        game.setPantallaActual(new Pantalla1(this.game, popollo, juegoDataBase));
                    } else if (seleccion == 1) {
                        game.setPantallaActual(new Pantalla2(this.game, popollo, juegoDataBase));
                    } else if (seleccion == 2) {
                        game.setPantallaActual(new Pantalla3(this.game, popollo, juegoDataBase));
                    }
                }
            }
        }
        // //Colision con pocion, aumenta nuestra vida
        if (popollo.checkCollision(pocion)) {
            recogerPocion();
        }

        //Posicion y duracion de la animacion
        duracion += delta;
        TextureRegion frame = (TextureRegion) animacionObjeto.getKeyFrame(duracion, true);
        pantalla.getBatch().begin();
        pantalla.getBatch().draw(frame, Gdx.graphics.getWidth() / 50 * 43, Gdx.graphics.getHeight() / 23 * 7);//posicion de la animación
        pantalla.getBatch().end();
    }

    /**
     * Funcion para la colision con el objeto Pocion, lo hace desaparecer de la pantalla y
     * aumenta la vida, ademas de realizar el sonido
     * La salud no puede superar la vida maxima
     */
    public void recogerPocion() {
        pocion.getSound().play(0.6f);
        pocion.remove();
        pocion = new Pocion(0, 0);
        if (popollo.getVida() + 30 > popollo.getVidaMaxima()) {
            popollo.setVida(popollo.getVidaMaxima());
        } else {
            popollo.setVida(popollo.getVida() + 30);
        }
        barraVida.setValue(popollo.getVida());
    }
}
