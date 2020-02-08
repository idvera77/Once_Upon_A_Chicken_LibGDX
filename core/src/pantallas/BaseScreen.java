package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.ivan.popollo_adventures.Juego;

import java.util.Random;

import actores.PolloMalvado;
import actores.Popollo;
import escuchadores.EscuchadorStage;
import objetos.GemaAzul;
import objetos.GemaRoja;
import objetos.Llave;

import static com.badlogic.gdx.scenes.scene2d.InputEvent.Type.keyDown;


public abstract class BaseScreen implements Screen {
    protected Juego game;
    protected Stage pantalla;
    protected Popollo popollo;
    protected PolloMalvado enemigoTerrestre, enemigoVolador;
    protected Texture fondo;
    protected Llave llave;
    protected GemaAzul gemaAzul;
    protected GemaRoja gemaRoja;
    protected ProgressBar healthBar;
    Label text;
    Label.LabelStyle textStyle;
    BitmapFont font = new BitmapFont();

    public BaseScreen(Juego juego) {
        game = juego;
        pantalla = new Stage(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        popollo = new Popollo();
        pantalla.addActor(popollo);

        textStyle = new Label.LabelStyle();
        textStyle.font = font;

        text = new Label("Gamever",textStyle);
        text.setBounds(0,.2f,100, 100);
        text.setFontScale(1f,1f);
        pantalla.addActor(text);

        enemigoTerrestre = new PolloMalvado(Gdx.graphics.getWidth(), 140);
        pantalla.addActor(enemigoTerrestre);
        enemigoVolador = new PolloMalvado(140, 0);
        pantalla.addActor(enemigoVolador);
        /**
         * pantalla.addActor(new Miguel(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2));
         *         //Grupo de enemigos
         *         enemigos=new Group();
         *         enemigos.addActor(new Alfredo(0,Gdx.graphics.getHeight()/2));
         *         enemigos.addActor(new Antonio(Gdx.graphics.getWidth()/2,0));
         *         for (Actor enemigo: enemigos.getChildren()){
         *             enemigos.addListener(new EscuchadorJugador((Personaje) enemigo));
         *         }
         *         pantalla.addActor(enemigos);
         */

        //Pongo el foco del movimiento en la pantalla 1
        pantalla.addListener(new EscuchadorStage(pantalla));
        Gdx.input.setInputProcessor(pantalla);

        //Añado una llave
        Random r = new Random();
        float posXLlave = (float) r.nextInt(Gdx.graphics.getWidth() / 10 * 9);
        float posYLlave = (float) r.nextInt(Gdx.graphics.getHeight() / 10 * 9);
        llave = new Llave(posXLlave, posYLlave);
        pantalla.addActor(llave);

        //Añado las gemas
        float posXAzul = (float) r.nextInt(Gdx.graphics.getWidth() / 10 * 9);
        float posYAzul = (float) r.nextInt(Gdx.graphics.getHeight() / 10 * 9);
        float posXRoja = (float) r.nextInt(Gdx.graphics.getWidth() / 10 * 9);
        float posYRoja = (float) r.nextInt(Gdx.graphics.getHeight() / 10 * 9);
        gemaAzul = new GemaAzul(posXAzul, posYAzul);
        gemaRoja = new GemaRoja(posXRoja, posYRoja);
        pantalla.addActor(gemaAzul);
        pantalla.addActor(gemaRoja);

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
        healthBar = new ProgressBar(0, popollo.getVidaMaxima(), 1, false, progressBarStyle);
        healthBar.setValue(popollo.getVida());
        System.out.println(healthBar.getValue());
        healthBar.getPercent();
        healthBar.setAnimateDuration(0.25f);
        healthBar.setBounds(20, pantalla.getHeight() - 60, 120, 40);
        pantalla.addActor(healthBar);

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
            llave.getSound().play(15.0f);
            llave.addAction(Actions.removeActor());
            llave = new Llave(0, 0);
        }
        if (popollo.checkCollision(gemaRoja)) {
            gemaRoja.getSound().play(15.0f);
            gemaRoja.addAction(Actions.removeActor());
            gemaRoja = new GemaRoja(0, 0);
        }
        if (popollo.checkCollision(gemaAzul)) {
            gemaAzul.getSound().play(15.0f);
            gemaAzul.addAction(Actions.removeActor());
            gemaAzul = new GemaAzul(0, 0);
            text.remove();
            text = new Label("POLLOOOOOOOOOOOOOOOOOO",textStyle);
            pantalla.addActor(text);

        }
        if (popollo.checkCollision(enemigoTerrestre)) {
            popollo.setVida(popollo.getVida() - 10);
            healthBar.setValue(popollo.getVida());
            popollo.recibirDaño();
            popollo.getSound().play(15.0f);
            enemigoTerrestre.addAction(Actions.removeActor());
            enemigoTerrestre = new PolloMalvado(Gdx.graphics.getWidth(), 140);
            pantalla.addActor(enemigoTerrestre);
        }
        if (popollo.checkCollision(enemigoVolador)) {
            popollo.setVida(popollo.getVida() - 10);
            healthBar.setValue(popollo.getVida());
            popollo.recibirDaño();
            popollo.getSound().play(15.0f);
            enemigoVolador.addAction(Actions.removeActor());
            enemigoVolador = new PolloMalvado(140, 0);
            pantalla.addActor(enemigoVolador);
        }
        if (popollo.getVida() <= 0) {
            System.out.println("A tomar por culo");
        }
        //Movimiento enemigo
        enemigoTerrestre.moveLeft(20);
        enemigoVolador.movimientoFuego();

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
}
