package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.ivan.popollo_adventures.Juego;

import actores.Cuervo;
import actores.Popollo;
import actores.Sierra;
import objetos.GemaAzul;
import objetos.GemaRoja;
import objetos.Llave;
import objetos.Pincho;
import objetos.Pocion;
import objetos.Puerta;


public class Tienda extends BaseScreen {
    private int seleccion;
    private Texture texturaObjeto;
    private TextureRegion texturaRegion;
    private TextureRegion[] framesTextura;
    private Animation animacionObjeto;
    private float duracion = 0f;
    private static final int ANCHO = Gdx.graphics.getWidth() / 5;
    private static final int ALTO = Gdx.graphics.getHeight() / 9;

    public Tienda(Juego game, Popollo popollo, int seleccion) {
        super(game, popollo);
        this.seleccion = seleccion;
        this.fondo = new Texture("fondospantalla/tienda.png");
        this.musica = Gdx.audio.newMusic(Gdx.files.internal("sonidos/tienda.mp3"));
        musica.setVolume(0.2f);
        musica.play();

        //Añadimos la pocion
        pocion = new Pocion(Gdx.graphics.getWidth() / 31 * 14, Gdx.graphics.getHeight() / 23 * 11);
        pantalla.addActor(pocion);
        //Añadimos los enemigos.
        enemigoTerrestre = new Cuervo(0,0);
        sierra = new Sierra(0, 0);
        pincho = new Pincho(0, 0);

        //Añadimos la llave
        llave = new Llave(Gdx.graphics.getWidth() / 31 * 7, Gdx.graphics.getHeight() / 23 * 5);
        pantalla.addActor(llave);

        //Añadimos las gemas
        gemaAzul = new GemaAzul(0,0);
        gemaRoja = new GemaRoja(0,0);

        //Añadimos la salida del nivel y la enviamos atras para que los personajes sean visibles al pasar por ella.
        puerta = new Puerta(Gdx.graphics.getWidth() / 31 * 27, Gdx.graphics.getHeight() / 23 * 5);
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

        if (popollo.checkCollision(pocion)) {
            pocion.getSound().play(0.8f);
            pocion.remove();
            pocion = new Pocion(0, 0);
            if(popollo.getVida() + 30 > 100){
                popollo.setVida(100);
            }else{
                popollo.setVida(popollo.getVida() + 30);
            }
            barraVida.setValue(popollo.getVida());
        }

        if (popollo.checkCollision(puerta)) {
            if (popollo.checkCollision(puerta)) {
                if (popollo.getObjetos().size() == 1) {
                    popollo.getObjetos().remove(0);
                    puerta.getSound().play(1f);
                    musica.stop();
                    if (seleccion == 3) {
                        game.setPantallaActual(new Pantalla1(this.game, popollo));
                    } else if (seleccion == 1) {
                        game.setPantallaActual(new Pantalla2(this.game, popollo));
                    } else if (seleccion == 2) {
                        game.setPantallaActual(new Pantalla3(this.game, popollo));
                    }
                }
            }
        }
        duracion += delta;
        TextureRegion frame = (TextureRegion) animacionObjeto.getKeyFrame(duracion, true);
        pantalla.getBatch().begin();
        pantalla.getBatch().draw(frame, Gdx.graphics.getWidth() / 50 * 43, Gdx.graphics.getHeight() / 23 * 7);//posicion de la animación
        pantalla.getBatch().end();
    }

    @Override
    public void dispose() {
        sound.dispose();
        musica.dispose();
    }
}
