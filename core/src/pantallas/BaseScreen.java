package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.ivan.popollo_adventures.Juego;

import actores.Cuervo;
import actores.Magia;
import actores.Personaje;
import actores.Popollo;
import actores.Sierra;
import objetos.GemaAzul;
import objetos.GemaRoja;
import objetos.Llave;
import objetos.Objeto;
import objetos.Puerta;


public abstract class BaseScreen implements Screen {
    protected Juego game;
    protected Stage pantalla;
    protected Popollo popollo;
    protected Magia magia;
    protected Cuervo enemigoTerrestre;
    protected Sierra sierra;
    protected Texture fondo;
    protected Llave llave;
    protected GemaAzul gemaAzul;
    protected GemaRoja gemaRoja;
    protected Puerta puerta;
    protected ProgressBar barraVida;
    protected FreeTypeFontGenerator fontGenerator;
    protected FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    protected BitmapFont font, fuente, fuente2, fuente3, fuente4;
    protected Label textoPuntuacion, textoGameOver, textoLlave, textoSalir, textoReintentar;
    protected Label.LabelStyle textStyle;
    protected TextButton.TextButtonStyle estiloBoton, estiloBoton2, estiloBoton3, estiloBoton4;
    protected TextButton boton1, boton2, boton3, boton4;
    protected Pixmap pixmap, pixmap2;
    protected Sound sound;
    protected int puntuacion, direccion;

    public BaseScreen(Juego juego) {
        //Creando la base del juego
        game = juego;
        pantalla = new Stage(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(pantalla);

        //Como el heroe se usara en todas las pantalla, lo creamos de inicio en esta BaseScreen.
        //Lo mismo con su ataque magico y una variable entero que usaremos para el ataque magico.
        popollo = new Popollo();
        direccion = 1;
        magia = new Magia();
        pantalla.addActor(popollo);

        //Añadimos el estilo, tamaño e imagen que tendran los botones que aparecen en pantalla.
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
        estiloBoton3.font = fuente2;
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
        boton4.setSize(Gdx.graphics.getWidth() / 31 * 3, Gdx.graphics.getHeight() / 23 * 3);
        boton4.setPosition(Gdx.graphics.getWidth() / 31 * 5, 0);
        pantalla.addActor(boton4);

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
        boton3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                popollo.salto();
            }
        });
        //Boton4: Inicia el disparo
        boton4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                disparo(direccion);
            }
        });

        //Añadimos el texto a mostrar, en este juego la puntuacion obtenida.
        //Los parametros utilizados son el tipo de letra (ttf), ancho del borde, color y tamaño de letra
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("04B_20__.TTF"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.borderWidth = 2;
        fontParameter.borderColor = Color.BLACK;
        fontParameter.color = Color.WHITE;
        fontParameter.size = Gdx.graphics.getWidth() / 40;
        font = fontGenerator.generateFont(fontParameter);
        textStyle = new Label.LabelStyle();
        textStyle.font = font;

        //Añadimos los tres textos que van a mostrarse en pantalla, el marcador de puntos y llaves
        textoPuntuacion = new Label("SCORE: " + popollo.getPuntuacion(), textStyle);
        textoPuntuacion.setPosition(Gdx.graphics.getWidth() / 31 * 18, Gdx.graphics.getHeight() / 23 * 22);
        textoLlave = new Label("LLAVE: 0", textStyle);
        textoLlave.setPosition(Gdx.graphics.getWidth() / 31 * 18, Gdx.graphics.getHeight() / 23 * 20);
        pantalla.addActor(textoPuntuacion);
        pantalla.addActor(textoLlave);

        //Estos dos textos auxiliares nos serviran de boton para decidir si abandonar el juego o continuar desde 0.
        textoSalir = new Label("Salir", textStyle);
        textoSalir.setPosition(Gdx.graphics.getWidth() / 31 * 6, Gdx.graphics.getHeight() / 23 * 10);
        textoReintentar = new Label("Reintentar", textStyle);
        textoReintentar.setPosition(Gdx.graphics.getWidth() / 31 * 18, Gdx.graphics.getHeight() / 23 * 10);

        //El texto de GameOver se deja invisible y solo aparece al quedarnos a 0 de puntos de salud
        //Ademas de utilizar un estilo y tamaño diferente.
        fontParameter.color = Color.RED;
        fontParameter.size = Gdx.graphics.getWidth() / 20;
        font = fontGenerator.generateFont(fontParameter);
        textStyle = new Label.LabelStyle();
        textStyle.font = font;
        textoGameOver = new Label("GAME OVER", textStyle);
        textoGameOver.setVisible(false);
        textoGameOver.setPosition(Gdx.graphics.getWidth() / 31 * 6, Gdx.graphics.getHeight() / 2);
        pantalla.addActor(textoGameOver);

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

        //Establezco que dentro de esa pantalla, voy a mover al actor, el
        //Primero que se insertó.
        pantalla.setKeyboardFocus(pantalla.getActors().get(0));
        pantalla.setDebugAll(true);
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
        if (popollo.checkCollision(llave)) {
            llave.reduce();
            popollo.addObjeto(llave);
            llave.getSound().play(1f);
            llave.addAction(Actions.removeActor());
            llave = new Llave(0, 0);
            textoLlave.setText("LLAVE: 1");
        }
        if (popollo.checkCollision(gemaRoja)) {
            gemaRoja.getSound().play(1f);
            gemaRoja.remove();
            gemaRoja = new GemaRoja(0, 0);
            popollo.setPuntuacion(popollo.getPuntuacion() + gemaRoja.getPuntuacion());
            textoPuntuacion.setText("SCORE: " + popollo.getPuntuacion());
        }

        if (popollo.checkCollision(gemaAzul)) {

            gemaAzul.getSound().play(1f);
            gemaAzul.addAction(Actions.removeActor());
            gemaAzul = new GemaAzul(0, 0);
            popollo.setPuntuacion(popollo.getPuntuacion() + gemaAzul.getPuntuacion());
            textoPuntuacion.setText("SCORE: " + popollo.getPuntuacion());


        }
        if (popollo.checkCollision(enemigoTerrestre)) {
            recibirGolpe();
            enemigoTerrestre.addAction(Actions.removeActor());
            enemigoTerrestre = new Cuervo(Gdx.graphics.getWidth() + popollo.getX()*1, Gdx.graphics.getHeight()/23*4);
            pantalla.addActor(enemigoTerrestre);
        }
        if (popollo.checkCollision(sierra)) {
            recibirGolpe();
            pantalla.addAction(Actions.removeActor(sierra));
            sierra = new Sierra(Gdx.graphics.getWidth()/31 * 7, Gdx.graphics.getWidth()/31 * 1 - popollo.getY()*5);
            pantalla.addActor(sierra);

        }

        if(magia.checkCollision(enemigoTerrestre)){
        enemigoTerrestre.addAction(Actions.removeActor());
        enemigoTerrestre = new Cuervo(Gdx.graphics.getWidth() + popollo.getX()*1, Gdx.graphics.getHeight()/23*4);
        pantalla.addActor(enemigoTerrestre);
        magia.remove();
        magia = new Magia(0,0);
        }

        if (popollo.getVida() <= 0) {
            enemigoTerrestre.remove();
            sierra.remove();
            llave.remove();
            puerta.remove();
            gemaAzul.remove();
            gemaRoja.remove();
            pantalla.addActor(textoSalir);
            pantalla.addActor(textoReintentar);
            textoGameOver.setVisible(true);
        }

        //Movimiento enemigo
        if(enemigoTerrestre.getX()+enemigoTerrestre.getWidth()  <= 0){
            enemigoTerrestre.remove();
            enemigoTerrestre = new Cuervo(Gdx.graphics.getWidth() + popollo.getX()*1, Gdx.graphics.getHeight()/23*4);
            pantalla.addActor(enemigoTerrestre);
        }
        enemigoTerrestre.movimientoEnemigo();
        sierra.movimientoSierra();
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
        pantalla.dispose();
    }

    public void recibirGolpe(){
        popollo.getSound().play(1f);
        popollo.recibirDaño();
        barraVida.setValue(popollo.getVida());
    }

    public void disparo(int direccion){
        magia = new Magia(popollo.getX(), popollo.getY());
        pantalla.addActor(magia);
        magia.ataqueDisparo(direccion);
        if(magia.getX() > Gdx.graphics.getWidth()) {
            magia.remove();
        }else if(magia.getX() < -Gdx.graphics.getWidth()){
            magia.remove();
        }
    }
}
