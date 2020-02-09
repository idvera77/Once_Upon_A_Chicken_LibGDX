package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

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

import java.util.Random;

import actores.PolloMalvado;
import actores.Popollo;
import objetos.GemaAzul;
import objetos.GemaRoja;
import objetos.Llave;


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
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private BitmapFont font;
    private Label text;
    private Label.LabelStyle textStyle;
    private int puntuacion;


    public BaseScreen(Juego juego) {
        //Creando la base del juego
        game = juego;
        pantalla = new Stage(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(pantalla);
        //Añadiendo los actores
        //Primero el heroe
        popollo = new Popollo();
        pantalla.addActor(popollo);

        //Añadimos los enemigos
        enemigoTerrestre = new PolloMalvado(Gdx.graphics.getWidth(), 140);
        pantalla.addActor(enemigoTerrestre);
        enemigoVolador = new PolloMalvado(140, 0);
        pantalla.addActor(enemigoVolador);

        //Añadimos los objetos
        //Añado una llave
        Random r = new Random();
        float posXLlave = (float) r.nextInt(Gdx.graphics.getWidth() / 10 * 9);
        float posYLlave = (float) r.nextInt(Gdx.graphics.getHeight() / 10 * 9);
        llave = new Llave(posXLlave, posYLlave);
        pantalla.addActor(llave);

        //Añado las gemas
        float posXAzul = (float) r.nextInt(Gdx.graphics.getWidth() / 10 * 9);
        float posYAzul = (float) r.nextInt(Gdx.graphics.getHeight() / 10 * 9);
        gemaAzul = new GemaAzul(posXAzul, posYAzul);
        pantalla.addActor(gemaAzul);

        float posXRoja = (float) r.nextInt(Gdx.graphics.getWidth() / 10 * 9);
        float posYRoja = (float) r.nextInt(Gdx.graphics.getHeight() / 10 * 9);
        gemaRoja = new GemaRoja(posXRoja, posYRoja);
        pantalla.addActor(gemaRoja);

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
        text = new Label("SCORE: " + popollo.getPuntuacion(), textStyle);
        text.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()-30);
        pantalla.addActor(text);

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
            llave.getSound().play(1f);
            llave.addAction(Actions.removeActor());
            llave = new Llave(0, 0);
        }
        if (popollo.checkCollision(gemaRoja)) {
            gemaRoja.getSound().play(1f);
            gemaRoja.addAction(Actions.removeActor());
            gemaRoja = new GemaRoja(0, 0);
            popollo.setPuntuacion(popollo.getPuntuacion() + gemaRoja.getPuntuacion());
            text.setText("SCORE: " + popollo.getPuntuacion());
        }
        if (popollo.checkCollision(gemaAzul)) {
            gemaAzul.getSound().play(1f);
            gemaAzul.addAction(Actions.removeActor());
            gemaAzul = new GemaAzul(0, 0);
            popollo.setPuntuacion(popollo.getPuntuacion() + gemaAzul.getPuntuacion());
            text.setText("SCORE: " + popollo.getPuntuacion());

        }
        if (popollo.checkCollision(enemigoTerrestre)) {
            popollo.setVida(popollo.getVida() - 10);
            healthBar.setValue(popollo.getVida());
            popollo.recibirDaño();
            popollo.getSound().play(1f);
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
