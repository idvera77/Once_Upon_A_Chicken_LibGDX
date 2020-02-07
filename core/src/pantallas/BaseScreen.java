package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.ivan.popollo_adventures.Juego;

import java.util.Random;

import actores.Personaje;
import actores.Popollo;
import escuchadores.EscuchadorStage;
import objetos.GemaAzul;
import objetos.Llave;
import objetos.Objeto;


public abstract class BaseScreen implements Screen {
    protected Juego game;
    protected Stage pantalla;
    private Group enemigos;
    protected Texture fondo;
    protected Sound sonido, sonidoLlave, sonidoGema;

    public BaseScreen(Juego juego) {
        game = juego;
        pantalla = new Stage(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        sonido = Gdx.audio.newSound(Gdx.files.internal("sonidos/afinidad.wav"));
        sonidoLlave = Gdx.audio.newSound(Gdx.files.internal("sonidos/login.wav"));
        sonido.play(1.0f);
        pantalla.addActor(new Popollo());
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
        pantalla.addActor(new Llave(posXLlave, posYLlave));

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

        for (int i = 0; i < pantalla.getActors().size; i++) {
            try {
                Personaje per = (Personaje) pantalla.getActors().get(i);
                for (int j = 0; j < pantalla.getActors().size; j++) {
                    try {
                        Objeto obj = (Objeto) pantalla.getActors().get(j);
                        if (per.checkCollision(obj)) {
                            per.addObjeto(obj);
                            obj.reduce();
                            sonidoLlave.play(1.0f);

                            pantalla.getActors().get(j).remove();
                            //per.setPuntuacion(per.getPuntuacion()+((GemaAzul) obj).getPuntuacion());
                        }
                    } catch (Exception ex) {
                        //No puede hacer el casting a objeto, porque estoy mirando un personaje, no hago nada.
                    }
                }
            } catch (Exception e) {
                //No puede hacer el casting a personaje, porque el actor que estoy mirando es un patito de goma
            }
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
        pantalla.dispose();
        sonido.dispose();
    }
}
