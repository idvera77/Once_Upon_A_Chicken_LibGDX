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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.ivan.popollo_adventures.Juego;

import actores.Cuervo;
import actores.Magia;
import actores.Popollo;
import actores.Sierra;
import objetos.GemaAzul;
import objetos.GemaRoja;
import objetos.Llave;
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
    protected GemaRoja gemaRoja, gemaAtaque;
    protected Puerta puerta;
    protected ProgressBar barraVida;
    protected FreeTypeFontGenerator fontGenerator;
    protected FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    protected BitmapFont font;
    protected Label textoPuntuacion, textoGameOver, textoLlave;
    protected Label.LabelStyle textStyle;
    protected Sound sound;
    protected int puntuacion;


    public BaseScreen(Juego juego) {
        //Creando la base del juego
        game = juego;
        pantalla = new Stage(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(pantalla);

        //Añadiendo los actores
        //Primero el heroe
        popollo = new Popollo();
        magia = new Magia();
        pantalla.addActor(popollo);

        //Añadimos el texto a mostrar, en este juego la puntuacion obtenida.
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("04B_20__.TTF"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 18;
        fontParameter.borderWidth = 2;
        fontParameter.borderColor = Color.BLACK;
        fontParameter.color = Color.WHITE;
        font = fontGenerator.generateFont(fontParameter);
        textStyle = new Label.LabelStyle();
        textStyle.font = font;
        textoPuntuacion = new Label("SCORE: " + popollo.getPuntuacion(), textStyle);
        textoPuntuacion.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()-30);
        textoLlave = new Label("LLAVE: 0", textStyle);
        textoLlave.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()-60);
        textoGameOver = new Label("GAME OVER", textStyle);
        textoGameOver.setVisible(false);
        textoGameOver.setPosition(Gdx.graphics.getWidth()/10*3, Gdx.graphics.getHeight()/10*5);
        pantalla.addActor(textoPuntuacion);
        pantalla.addActor(textoLlave);
        pantalla.addActor(textoGameOver);

        //Creo la barra de vida de popollo
        Pixmap pixmap = new Pixmap(120, 40, Pixmap.Format.RGBA8888);
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

        Pixmap pixmap2 = new Pixmap(120, 40, Pixmap.Format.RGBA8888);
        pixmap2.setColor(Color.GREEN);
        pixmap2.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap2)));
        pixmap2.dispose();
        progressBarStyle.knobBefore = drawable;

        barraVida = new ProgressBar(0, popollo.getVidaMaxima(), 1, false, progressBarStyle);
        barraVida.setValue(popollo.getVida());
        barraVida.getPercent();
        barraVida.setAnimateDuration(0.25f);
        barraVida.setPosition(20, Gdx.graphics.getHeight()-60);
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
            gemaRoja.addAction(Actions.removeActor());
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
            enemigoTerrestre = new Cuervo(Gdx.graphics.getWidth() + popollo.getX()*1, Gdx.graphics.getHeight()/5);
            pantalla.addActor(enemigoTerrestre);
        }
        if (popollo.checkCollision(sierra)) {
            recibirGolpe();
            sierra.addAction(Actions.removeActor());
            sierra = new Sierra(Gdx.graphics.getWidth()/10 * 2, 0-sierra.getY()*3);
            pantalla.addActor(sierra);

            magia = new Magia(popollo.getX(), popollo.getY());
            pantalla.addActor(magia);
            magia.ataqueDisparo();
            if(magia.getX() > Gdx.graphics.getWidth()){
              magia.remove();
            }
        }
        if(magia.checkCollision(enemigoTerrestre)){
            magia.clearActions();
            magia.remove();
            magia = new Magia(0,0);
            enemigoTerrestre.addAction(Actions.removeActor());
            enemigoTerrestre = new Cuervo(Gdx.graphics.getWidth() + popollo.getX()*1, Gdx.graphics.getHeight()/5);
            pantalla.addActor(enemigoTerrestre);
        }
        if (popollo.getVida() <= 0) {
            textoGameOver.setVisible(true);
            enemigoTerrestre.remove();
            sierra.remove();
            popollo.remove();
        }

        //Movimiento enemigo
        if(enemigoTerrestre.getX()  <= 0){
            enemigoTerrestre.remove();
            enemigoTerrestre = new Cuervo(Gdx.graphics.getWidth() + popollo.getX()*1, Gdx.graphics.getHeight()/5);
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
        popollo.disminuirVida();
        barraVida.setValue(popollo.getVida());
    }
}
