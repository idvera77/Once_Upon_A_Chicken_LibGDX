package pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.ivan.popollo_adventures.Juego;

import actores.*;
import basededatos.JuegoDataBase;
import objetos.*;

public abstract class BaseScreen implements Screen {
    protected Juego game;
    protected Stage pantalla;
    protected Popollo popollo;
    protected Pocion pocion;
    protected Magia magia;
    protected Cuervo cuervo;
    protected Sierra sierra;
    protected Pincho pincho;
    protected Texture fondo;
    protected Llave llave;
    protected GemaAzul gemaAzul;
    protected GemaRoja gemaRoja;
    protected Puerta puerta;
    protected ProgressBar barraVida;
    protected FreeTypeFontGenerator fontGenerator;
    protected FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    protected BitmapFont font, fuente, fuente2, fuente3, fuente4, fuente5, fuente6;
    protected Label textoPuntuacion, textoGameOver, textoLlave;
    protected Label.LabelStyle textStyle;
    protected TextButton.TextButtonStyle estiloBoton, estiloBoton2, estiloBoton3, estiloBoton4, estiloBoton5, estiloBoton6;
    protected TextButton boton1, boton2, boton3, boton4, boton5, boton6;
    protected Pixmap pixmap, pixmap2;
    protected Music musica;
    protected int direccion;
    protected JuegoDataBase juegoDataBase;

    public BaseScreen(Juego juego, Popollo heroe, JuegoDataBase db) {
        //Creando la base del juego
        game = juego;
        pantalla = new Stage(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(pantalla);
        juegoDataBase = db;
        //Como el heroe se usara en todas las pantalla, lo creamos de inicio en esta BaseScreen.
        //Lo mismo con su ataque magico y una variable entero que usaremos para el ataque magico.
        popollo = heroe;
        popollo.setX(0);
        magia = new Magia(0, 0);
        direccion = 1;
        pantalla.addActor(popollo);

        //Añadimos el tipo de letra, estilo y tamaño de texto para los botones que lo requieren
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("04B_20__.TTF"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.borderWidth = 2;
        fontParameter.borderColor = Color.BLACK;
        fontParameter.color = Color.WHITE;
        fontParameter.size = Gdx.graphics.getWidth() / 30;
        font = fontGenerator.generateFont(fontParameter);
        textStyle = new Label.LabelStyle();
        textStyle.font = font;

        //Se añade la imagen a los botones, tamaño y posicion
        //Boton1
        fuente = new BitmapFont();
        estiloBoton = new TextButton.TextButtonStyle();
        estiloBoton.font = fuente;
        estiloBoton.over = new TextureRegionDrawable(new Texture("controles/izquierda.png"));
        estiloBoton.up = new TextureRegionDrawable(new Texture("controles/izquierda.png"));
        boton1 = new TextButton("", estiloBoton);
        boton1.setSize(Gdx.graphics.getWidth() / 31 * 4, Gdx.graphics.getHeight() / 23 * 4);
        boton1.setPosition(31 * 1, 0);
        pantalla.addActor(boton1);
        //Boton2
        fuente2 = new BitmapFont();
        estiloBoton2 = new TextButton.TextButtonStyle();
        estiloBoton2.font = fuente2;
        estiloBoton2.over = new TextureRegionDrawable(new Texture("controles/derecha.png"));
        estiloBoton2.up = new TextureRegionDrawable(new Texture("controles/derecha.png"));
        boton2 = new TextButton("", estiloBoton2);
        boton2.setSize(Gdx.graphics.getWidth() / 31 * 4, Gdx.graphics.getHeight() / 23 * 4);
        boton2.setPosition(Gdx.graphics.getWidth() / 31 * 27, 0);
        pantalla.addActor(boton2);
        //Boton3
        fuente3 = new BitmapFont();
        estiloBoton3 = new TextButton.TextButtonStyle();
        estiloBoton3.font = fuente3;
        estiloBoton3.over = new TextureRegionDrawable(new Texture("controles/arriba.png"));
        estiloBoton3.up = new TextureRegionDrawable(new Texture("controles/arriba.png"));
        boton3 = new TextButton("", estiloBoton3);
        boton3.setSize(Gdx.graphics.getWidth() / 31 * 4, Gdx.graphics.getHeight() / 23 * 4);
        boton3.setPosition(Gdx.graphics.getWidth() / 31 * 22, 0);
        pantalla.addActor(boton3);
        //Boton4
        fuente4 = new BitmapFont();
        estiloBoton4 = new TextButton.TextButtonStyle();
        estiloBoton4.font = fuente4;
        estiloBoton4.over = new TextureRegionDrawable(new Texture("personajes/magia.png"));
        estiloBoton4.up = new TextureRegionDrawable(new Texture("personajes/magia.png"));
        boton4 = new TextButton("", estiloBoton4);
        boton4.setSize(Gdx.graphics.getWidth() / 31 * 3, Gdx.graphics.getHeight() / 23 * 4);
        boton4.setPosition(Gdx.graphics.getWidth() / 31 * 5, 0);
        pantalla.addActor(boton4);
        //Boton5
        fuente5 = new BitmapFont();
        estiloBoton5 = new TextButton.TextButtonStyle();
        estiloBoton5.font = font;
        boton5 = new TextButton("EXIT", estiloBoton5);
        boton5.setPosition(Gdx.graphics.getWidth() / 31 * 7, Gdx.graphics.getHeight() / 23 * 10);
        //Boton6
        fuente6 = new BitmapFont();
        estiloBoton6 = new TextButton.TextButtonStyle();
        estiloBoton6.font = font;
        boton6 = new TextButton("RETRY", estiloBoton6);
        boton6.setPosition(Gdx.graphics.getWidth() / 31 * 16, Gdx.graphics.getHeight() / 23 * 10);

        //Evento de botones
        //Boton1: Inicia el movimiento hacia la izquierda
        boton1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                popollo.stopMoving(1);
                popollo.startMoving(0);
                direccion = 0;
            }
        });
        //Boton2: Inicia el movimiento hacia la derecha
        boton2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                popollo.stopMoving(0);
                popollo.startMoving(1);
                direccion = 1;
            }
        });
        //Boton3: Inicia el salto
        //Añadido un Timer para que no puedas saltar sin parar gracias a setTouchable.disabled
        boton3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                boton3.setTouchable(Touchable.disabled);
                float delay = 1.2f; // seconds
                popollo.salto();
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        boton3.setTouchable(Touchable.enabled);
                    }
                }, delay);
            }
        });
        //Boton4: Inicia el disparo
        boton4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                disparo(direccion);
            }
        });
        //Boton5: Salir del juego
        boton5.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        //Boton6: Reinicia la partida
        boton6.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juegoDataBase.empezarPartida();
                reiniciar();
            }
        });

        //Añadimos los tres textos que van a mostrarse en pantalla, el marcador de puntos y llaves
        //Tambien se modifica su tamaño
        fontParameter.size = Gdx.graphics.getWidth() / 40;
        font = fontGenerator.generateFont(fontParameter);
        textStyle = new Label.LabelStyle();
        textStyle.font = font;

        textoPuntuacion = new Label("SCORE: " + popollo.getPuntuacion(), textStyle);
        textoPuntuacion.setPosition(Gdx.graphics.getWidth() / 31 * 18, Gdx.graphics.getHeight() / 23 * 22);
        textoLlave = new Label("LLAVE: 0", textStyle);
        textoLlave.setPosition(Gdx.graphics.getWidth() / 31 * 18, Gdx.graphics.getHeight() / 23 * 20);
        pantalla.addActor(textoPuntuacion);
        pantalla.addActor(textoLlave);

        //El texto de GameOver se deja invisible y solo aparece al quedarnos a 0 de puntos de vida
        //Ademas de utilizar un estilo y tamaño diferente
        fontParameter.color = Color.RED;
        fontParameter.size = Gdx.graphics.getWidth() / 20;
        font = fontGenerator.generateFont(fontParameter);
        textStyle = new Label.LabelStyle();
        textStyle.font = font;
        textoGameOver = new Label("GAME OVER", textStyle);
        textoGameOver.setPosition(Gdx.graphics.getWidth() / 31 * 6, Gdx.graphics.getHeight() / 2);
        fuente.dispose();
        fuente2.dispose();
        fuente3.dispose();
        fuente4.dispose();
        fuente5.dispose();
        fuente6.dispose();

        //Añadimos la barra de vida
        /**
         * Se crean tres pixmap uno de fondo con color rojo, otro que actua de caja y otro verde.
         * Estos se incluyen en un estilo de barra de progreso en el cual indicaremos como valor
         * maximo la salud de popollo y cuando reciba daño el color verde dejara paso al rojo del fondo
         * mediante una animacion resultona.
         */
        pixmap = new Pixmap(Gdx.graphics.getWidth() / 31 * 7, Gdx.graphics.getHeight() / 23 * 3, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fill();
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        ProgressBar.ProgressBarStyle progressBarStyle = new ProgressBar.ProgressBarStyle();
        progressBarStyle.background = drawable;

        pixmap = new Pixmap(0, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        progressBarStyle.knob = drawable;

        pixmap2 = new Pixmap(Gdx.graphics.getWidth() / 31 * 7, Gdx.graphics.getHeight() / 23 * 3, Pixmap.Format.RGBA8888);
        pixmap2.setColor(Color.GREEN);
        pixmap2.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap2)));
        pixmap2.dispose();
        progressBarStyle.knobBefore = drawable;

        barraVida = new ProgressBar(0, popollo.getVidaMaxima(), 1, false, progressBarStyle);
        barraVida.setValue(popollo.getVida());
        barraVida.getVisualPercent();
        barraVida.setSize(Gdx.graphics.getWidth() / 31 * 10, Gdx.graphics.getHeight() / 23 * 3);
        barraVida.setAnimateDuration(0.25f);
        barraVida.setPosition(Gdx.graphics.getWidth() / 31 * 2, Gdx.graphics.getHeight() / 23 * 20);
        pantalla.addActor(barraVida);

        //Indicamos quien es el actor a mover, en este caso solo a nuestro heroe
        pantalla.setKeyboardFocus(pantalla.getActors().get(0));
        //pantalla.setDebugAll(true);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //Color de limpieza blanco por las transparencias
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Dibujo el fondo de pantalla
        pantalla.getBatch().begin();
        pantalla.getBatch().draw(fondo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        pantalla.getBatch().end();
        pantalla.getBatch().setColor(pantalla.getBatch().getColor().r, pantalla.getBatch().getColor().g, pantalla.getBatch().getColor().b, 0.5f);
        pantalla.act(delta); //Realizamos las acciones dibujando el tiempo transcurrido entre frame y frame
        pantalla.draw();

        //Al ser un juego simple podemos dejar el apartado de colisiones de muchos enemigos y objetos en este render global.
        //Colision con llave, la agregamos a objetos del heroe y asi avanzaremos de nivel, aumenta el contador.
        if (popollo.checkCollision(llave)) {
            colisionLlave();
        }
        //Colision con gemas, aumenta nuestra puntuacion
        if (popollo.checkCollision(gemaRoja)) {
            colisionGemaRoja();
        }
        if (popollo.checkCollision(gemaAzul)) {
            colisionGemaAzul();
        }
        //Colision con enemigo basico, recibimos daño y generamos otro enemigo para que venga en oleadas
        if (popollo.checkCollision(cuervo)) {
            colisionCuervo();
        }
        //Colision el pincho, al chocar desaparece
        if (popollo.checkCollision(pincho)) {
            colisionPincho();
        }
        //Colision de nuestro disparo con el enemigo
        if (magia.checkCollision(cuervo)) {
            impactoMagico();
        }

        //Borra el disparo al salir de la pantalla su sprite
        limiteMagia();

        //Game Over cuando nuestro heroe tiene 0 de vida. Se remueven todos los actores y se activan los botones de salir o reintentar.
        if (popollo.getVida() <= 0) {
            gameOver();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    /**
     * Funcion que se activa al recibir daño, realiza un sonido, una animacion y baja la barra de vida
     */
    public void recibirGolpe() {
        popollo.getSound().play(0.5f);
        popollo.recibirDaño();
        barraVida.setValue(popollo.getVida());
    }

    /**
     * Funcion que nos permite disparar una magia hacia delante, el disparo solo puede ejecutarse si no existe otra magia disparada
     *
     * @param direccion Este valor entero nos permite indicar la posicion del disparo 0 - Izquierda, 1 Derecha
     */
    public void disparo(int direccion) {
        if (magia.getX() == 0) {
            magia.getSound().play(1f);
            magia = new Magia(popollo.getX(), popollo.getY());
            pantalla.addActor(magia);
            magia.ataqueDisparo(direccion);
        }
    }

    /**
     * Funcion que llama a otras para realizar el movimiento de enemigos diferentes, en el caso del cuervo
     * una vez que este ha salido de la pantalla se genera otro para simular oleadas de enemigos
     */
    public void movimientoEnemigos() {
        //Movimiento enemigo
        cuervo.movimientoEnemigo();
        sierra.movimientoSierra();
        //Si este desaparece de la pantalla vuelve a generarse otro.
        if (cuervo.getX() + cuervo.getWidth() <= 0) {
            cuervo.setPosition(Gdx.graphics.getWidth() + popollo.getX() * 1, Gdx.graphics.getHeight() / 23 * 4);
            pantalla.addActor(cuervo);
        }
    }

    /**
     * Funcion para la colision con el objeto Llave, lo reduce, recoge y realiza un sonido
     */
    public void colisionLlave() {
        llave.reduce();
        popollo.addObjeto(llave);
        llave.getSound().play(0.8f);
        llave.remove();
        llave = new Llave(0, 0);
        textoLlave.setText("LLAVE: 1");
    }

    /**
     * Funcion para la colision con el objeto gemaRoja, lo hace desaparecer de la pantalla y aumenta la puntuacion
     */
    public void colisionGemaRoja() {
        gemaRoja.getSound().play(1f);
        gemaRoja.remove();
        gemaRoja = new GemaRoja(0, 0);
        popollo.setPuntuacion(popollo.getPuntuacion() + gemaRoja.getPuntuacion());
        textoPuntuacion.setText("SCORE: " + popollo.getPuntuacion());
    }

    /**
     * Funcion para la colision con el objeto gemaAzul, lo hace desaparecer de la pantalla y aumenta la puntuacion
     */
    public void colisionGemaAzul() {
        gemaAzul.getSound().play(1f);
        gemaAzul.remove();
        gemaAzul = new GemaAzul(0, 0);
        popollo.setPuntuacion(popollo.getPuntuacion() + gemaAzul.getPuntuacion());
        textoPuntuacion.setText("SCORE: " + popollo.getPuntuacion());
    }

    /**
     * Funcion para la colision con el Personaje Cuervo, lo hace desaparecer y disminuye la barra de salud del personaje
     */
    public void colisionCuervo() {
        recibirGolpe();
        cuervo.remove();
        cuervo = new Cuervo(Gdx.graphics.getWidth() + popollo.getX() * 1, Gdx.graphics.getHeight() / 23 * 4);
        pantalla.addActor(cuervo);
    }

    /**
     * Funcion para la colision con el Objeto pincho, lo hace desaparecer y disminuye la barra de salud del personaje
     */
    public void colisionPincho() {
        recibirGolpe();
        pincho.remove();
        pincho = new Pincho(0, 0);
    }

    /**
     * Funcion para la colision de la magia con el personaje Cuervo, lo hace desaparecer ademas de realizar un sonido
     */
    public void impactoMagico() {
        cuervo.getSound().play(1f);
        cuervo.remove();
        cuervo = new Cuervo(Gdx.graphics.getWidth() + popollo.getX() * 1, Gdx.graphics.getHeight() / 23 * 4);
        pantalla.addActor(cuervo);
        magia.remove();
        magia = new Magia(0, 0);
    }

    /**
     * Funcion para el Personaje magia, cuando desaparece de la pantalla se resetea
     */
    public void limiteMagia() {
        if (magia.getX() + magia.getWidth() >= Gdx.graphics.getWidth()) {
            magia = new Magia(0, 0);
        } else if (magia.getX() + magia.getWidth() <= 0) {
            magia = new Magia(0, 0);
        }
    }

    /**
     * Funcion que mos permite empezar una nueva partida
     */
    public void reiniciar() {
        musica.stop();
        dispose();
        game.setPantallaActual(new Pantalla1(game, new Popollo(), juegoDataBase));
    }

    /**
     * Funcion que remueve algunos actors, bloquea los botones y muestra la pantalla de gameOver
     */
    public void gameOver() {
        juegoDataBase.terminarPartida(popollo.getPuntuacion());
        popollo.remove();
        cuervo.remove();
        sierra.remove();
        pincho.remove();
        llave.remove();
        puerta.remove();
        gemaAzul.remove();
        gemaRoja.remove();
        boton1.setTouchable(Touchable.disabled);
        boton2.setTouchable(Touchable.disabled);
        boton3.setTouchable(Touchable.disabled);
        boton4.setTouchable(Touchable.disabled);
        pantalla.addActor(textoGameOver);
        pantalla.addActor(boton5);
        pantalla.addActor(boton6);
    }
}
